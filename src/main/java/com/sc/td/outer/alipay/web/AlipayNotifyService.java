package com.sc.td.outer.alipay.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.text.DecimalFormat;
import java.util.Map;
import javax.transaction.Transactional;

import org.dom4j.DocumentException;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.scmoney.ScMoneyDao;
import com.sc.td.business.dao.scorder.ScOrderDao;
import com.sc.td.business.dao.scuser.ScUserDao;
import com.sc.td.business.entity.scmoney.ScMoney;
import com.sc.td.business.entity.scorder.ScOrder;
import com.sc.td.business.entity.scuser.ScUser;
import com.sc.td.common.utils.IdGen;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.datetime.TimeUtil;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.DictConfig;
import com.sc.td.frame.RespInfoConfig;
import com.sc.td.outer.alipay.config.AlipayConfig;
import com.sc.td.outer.alipay.core.AlipaySubmit;
import com.sc.td.outer.alipay.core.Trade;
import com.sc.td.outer.alipay.core.TradeStatus;

@Service
public class AlipayNotifyService extends BaseService{
	
	private static final Logger log=LoggerFactory.getLogger(AlipayNotifyService.class);
	
	@Autowired
	private RespInfoConfig respInfoConfig;

	@Autowired
	private ScOrderDao scOrderDao;
	
	@Autowired
	private ScUserDao scUserDao;
	
	@Autowired
	private ScMoneyDao scMoneyDao;
	
	@Autowired
	private DictConfig dictConfig;
	
	/**
	 * 客户端请求
	 * @param jsonText
	 * @return
	 * @throws MalformedURLException
	 * @throws DocumentException
	 * @throws IOException
	 * @throws AlipayApiException 
	 */
	@Transactional
	public String request(String jsonText) throws MalformedURLException, DocumentException, IOException, AlipayApiException {
		Trade trade = JacksonUtil.jsonToObj(jsonText, Trade.class);
		DecimalFormat df = new DecimalFormat("#.##");
		if (trade != null && StringUtils.isNotBlank(trade.getTotal_amount())) {
			ScUser scUser=scUserDao.findByUserId(trade.getUserId());
			if(scUser==null){
				return CreateJson.createTextJson(respInfoConfig.getUserNotExist(), false);
			}
			String total_amount = trade.getTotal_amount();
			String outTradeNo=IdGen.uuid();
			trade.setTotal_amount(df.format(Double.parseDouble(total_amount)));
			trade.setOut_trade_no(outTradeNo);
			Map<String, String> map = AlipaySubmit.buildParamsMap(trade);
			//创建一个订单
			ScOrder sc=new ScOrder();
			sc.setOut_trade_no(outTradeNo);
			sc.setSeller_id(AlipayConfig.seller_id);
			sc.setApp_id(AlipayConfig.app_id);
			sc.setUser_id(trade.getUserId());
			sc.setTrade_status(TradeStatus.WAIT_BUYER_PAY.name());
			sc.setSubject(trade.getSubject());
			sc.setTotal_amount(df.format(Double.parseDouble(total_amount)));
			sc.setInitValue(sc, trade.getUserId());
			scOrderDao.save(sc);
			return JacksonUtil.objToJson(map);
		}
		return CreateJson.createTextJson(respInfoConfig.getSysExeption(), false);
	}
	
	/**
	 * 支付宝异步通知
	 * @param request
	 * @param response
	 * @return
	 * @throws AlipayApiException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@Transactional
	public String notify(Map<String, String> params) throws AlipayApiException,
			IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String result = "";
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, AlipayConfig.charset); // 调用SDK验证签名
		if (signVerified) {
			// 验签成功后
			log.info("验签成功!");
			// 1、验证该通知数据中的out_trade_no是否为商户系统中创建的订单号
			// 2、判断total_amount是否确实为该订单的实际金额（即商户订单创建时的金额）
			// 3、校验通知中的seller_id（或者seller_email) 是否为out_trade_no这笔单据的对应的操作方
			// 4、验证app_id是否为该商户本身
			ScOrder scOrder = new ScOrder();
			Class<ScOrder> clazz = ScOrder.class;
			Method[] methods = clazz.getMethods();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				String key = "set" + toUpperKey(entry.getKey());
				for (Method method : methods) {
					if (key.equals(method.getName())) {
						method.invoke(scOrder, entry.getValue());
						break;
					}
				}
			}
			if (scOrder != null) {
				ScOrder sc = scOrderDao.findByOutTradeNo(scOrder.getOut_trade_no());
				String moenyId=IdGen.uuid();
				if (sc != null && sc.getTotal_amount().equals(scOrder.getTotal_amount())
						&& sc.getSeller_id().equals(scOrder.getSeller_id())
						&& sc.getApp_id().equals(scOrder.getApp_id())) {
					//验证成功后,保存支付宝返回的订单信息
					sc.setTrade_status(scOrder.getTrade_status());
					sc.setTrade_no(scOrder.getTrade_no());
					sc.setReceipt_amount(scOrder.getReceipt_amount());
					sc.setBuyer_pay_amount(scOrder.getBuyer_pay_amount());
					sc.setSubject(scOrder.getSubject());
					sc.setBody(moenyId);
					sc.setGmt_create(scOrder.getGmt_create());
					sc.setGmt_payment(scOrder.getGmt_payment());
					sc.setUpdateValue(sc, sc.getUser_id());
					//保存成功后，更新用户的余额及等级，出入金表添加记录
					if(scOrderDao.save(sc)!=null){
						ScUser scUser=scUserDao.findByUserId(sc.getUser_id());
						Double addMoney=calcMoney(Double.parseDouble(sc.getTotal_amount()));
						scUser.setMoney(scUser.getMoney()+addMoney);
						scUser.setUpdateValue(scUser, scUser.getUserId());
						if(scUserDao.save(scUser)!=null && scMoneyDao.save(setScMoney(scUser,addMoney,moenyId))!=null){
							updatePkLevel(scUser);//更新PK等级
						}
					}
					result = "success";
				} else {
					result = "failure";
				}
			}
		} else {
			// 验签失败则记录异常日志，并在response中返回failure.
			log.error("验签失败!");
			result = "failure";
		}
		return result;
	}
	
	/**
	 * 根据虚拟币与实际金额的比例计算充值的虚拟币
	 * @param amount
	 * @return
	 */
	public Double calcMoney(Double amount){
		Double money=0.0;
		String rate=getSysDictValue(AlipayConfig.rate_key);
		if(StringUtils.isNotBlank(rate)){
			money=amount*Double.parseDouble(rate);
		}
		return money;
	}
	
	/**
	 * 将首字母大写
	 * 
	 * @param key
	 * @return
	 */
	public String toUpperKey(String key) {
		if (StringUtils.isNotBlank(key)) {
			key = key.substring(0, 1).toUpperCase() + key.substring(1, key.length());
			return key;
		}
		return null;
	}
	
	
	/**
	 * 设置出入金scMoney属性
	 * 
	 * @param scUser
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ScMoney setScMoney(ScUser scUser,Double addMoney,String moenyId) throws UnsupportedEncodingException {
		ScMoney scMoney = new ScMoney();
		scMoney.setSeqNo(moenyId);
		scMoney.setUserId(scUser.getUserId());
		scMoney.setMoney(addMoney);
		scMoney.setApplyTime(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
		scMoney.setAcceptTime(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
		scMoney.setApplyUserName(scUser.getUserName());
		scMoney.setAcceptUserName(scUser.getUserName());
		scMoney.setAcceptStatus(dictConfig.getAudit_accept_status());
		scMoney.setRemark(AlipayConfig.money_remark);
		scMoney.setInitValue(scMoney, scUser.getUserId());
		return scMoney;
	}
}
