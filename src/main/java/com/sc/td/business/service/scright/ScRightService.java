package com.sc.td.business.service.scright;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.SysDict.SysDictDao;
import com.sc.td.business.dao.scmoney.ScMoneyDao;
import com.sc.td.business.dao.scright.ScRightDao;
import com.sc.td.business.dao.scuser.ScUserDao;
import com.sc.td.business.entity.scmoney.ScMoney;
import com.sc.td.business.entity.scright.ScRight;
import com.sc.td.business.entity.scuser.ScUser;
import com.sc.td.business.entity.sysdict.SysDict;
import com.sc.td.common.utils.IdGen;
import com.sc.td.common.utils.datetime.TimeUtil;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.DictConfig;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScRightService extends BaseService {

	@Autowired
	private ScRightDao scRightDao;

	@Autowired
	private ScMoneyDao scMoneyDao;

	@Autowired
	private SysDictDao sysDictDao;

	@Autowired
	private ScUserDao scUserDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	@Autowired
	private DictConfig dictConfig;

	/**
	 * 开通VIP
	 * 
	 * @param jsonText
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public synchronized String becomevip_ckt(String jsonText) throws UnsupportedEncodingException {
		// 解析json数据，得到用户ID
		ConcurrentHashMap<String, String> map = JacksonUtil.jsonToObj(jsonText, ConcurrentHashMap.class);
		String userId = map.get("userId");
		String duration = map.get("duration");// 开通时长，以月为单位
		if ("".equals(userId) || "".equals(duration)) {
			return CreateJson.createTextJson(respInfoConfig.getParamsNull(), false);
		}
		// 查询数据字典，确定开通VIP每月需要的虚拟币
		List<SysDict> dictlist = sysDictDao.findByType(dictConfig.getVip_todayCodeMoney_key());
		double todayCodeMoney = Double.parseDouble(dictConfig.getVip_default_todayCodeMoney());
		if (dictlist != null && dictlist.size() > 0) {
			todayCodeMoney = Double.parseDouble(dictlist.get(0).getValue());
		}

		double totalcost = todayCodeMoney * Double.parseDouble(duration);// 开通VIP总花费

		// 获取该用户的虚拟币
		ScUser scUser = scUserDao.findByUserId(userId);
		double money = 0;
		if (scUser != null) {
			money = scUser.getMoney();
		} else {
			// 用户不存在。正常情况下不会执行
			return CreateJson.createTextJson(respInfoConfig.getUserNotExist(), false);
		}

		// 判断用户账户中的虚拟币是否足以支付开通时长
		if (totalcost > money) {
			// 虚拟币不足
			return CreateJson.createTextJson(respInfoConfig.getMoenyNotenough(), false);
		}

		// 判断是否开通过VIP
		List<ScRight> list = scRightDao.findByUserId(userId);
		// 之前开通过VIP
		if (list != null && list.size() > 0) {
			ScRight scRight = list.get(0);
			// 判断VIP是否过期
			if (Long.parseLong(scRight.getEndDate()) < Long
					.parseLong(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DBdayFormat))) {
				// 已过期，重新开通
				scRight.setIsEnable(dictConfig.getVip_able());
				scRight.setEndDate(TimeUtil.dateTime2Str(
						TimeUtil.plusTime(new DateTime(), Integer.parseInt(duration), TimeUtil.MONTHS),
						TimeUtil.DBdayFormat));
				scRight = setScRightValue(scRight);
				if (scRightDao.save(scRight) != null) {
					// 表sc_money插入一条数据，sc_user更改money值
					setDbValue(scRight, scUser, totalcost);
					return CreateJson.createTextJson(respInfoConfig.getBecomevipSuccess(), true);
				}

			} else {
				scRight.setEndDate(TimeUtil.dateTime2Str(
						TimeUtil.plusTime(TimeUtil.str2DateTime(scRight.getEndDate(), TimeUtil.DBdayFormat),
								Integer.parseInt(duration), TimeUtil.MONTHS),
						TimeUtil.DBdayFormat));
				scRight = setScRightValue(scRight);
				if (scRightDao.save(scRight) != null) {
					// 表sc_money插入一条数据，sc_user更改money值
					setDbValue(scRight, scUser, totalcost);
					return CreateJson.createTextJson(respInfoConfig.getBecomevipSuccess(), true);
				}
			}
		} else {
			// 第一次开通VIP
			ScRight scRight = new ScRight();
			scRight.setUserId(userId);
			scRight.setRightId(dictConfig.getRight_vip());
			scRight.setIsEnable(dictConfig.getVip_able());
			scRight.setEndDate(TimeUtil.dateTime2Str(
					TimeUtil.plusTime(new DateTime(), Integer.parseInt(duration), TimeUtil.MONTHS),
					TimeUtil.DBdayFormat));
			scRight = setScRightValue(scRight);
			if (scRightDao.save(scRight) != null) {
				// 表sc_money插入一条数据，sc_user更改money值
				setDbValue(scRight, scUser, totalcost);
				return CreateJson.createTextJson(respInfoConfig.getBecomevipSuccess(), true);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getBecomevipSuccess(), false);
	}

	/**
	 * 设置ScRight属性
	 * 
	 * @param scRight
	 * @return
	 */
	public ScRight setScRightValue(ScRight scRight) {
		scRight.setCreateBy(scRight.getUserId());
		scRight.setCreateDate(TimeUtil.dateTime2Str(new DateTime(), TimeUtil.DSPdaytimeFormat));
		scRight.setUpdateBy(scRight.getUserId());
		scRight.setUpdateDate(TimeUtil.dateTime2Str(new DateTime(), TimeUtil.DSPdaytimeFormat));
		return scRight;
	}

	/**
	 * 设置scMoney属性
	 * 
	 * @param scMoney
	 * @param scRight
	 * @param money
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ScMoney setScmoneyInitValue(ScMoney scMoney, ScRight scRight, double money)
			throws UnsupportedEncodingException {
		scMoney.setSeqNo(IdGen.uuid());
		scMoney.setUserId(scRight.getUserId());
		scMoney.setMoney(money);
		scMoney.setApplyTime(TimeUtil.dateTime2Str(new DateTime(), TimeUtil.DSPdaytimeFormat));
		scMoney.setAcceptTime(TimeUtil.dateTime2Str(new DateTime(), TimeUtil.DSPdaytimeFormat));
		ScUser scUser = scUserDao.findByUserId(scRight.getUserId());
		if (scUser != null) {
			scMoney.setAcceptUserName(scUser.getUserName());
			scMoney.setApplyUserName(scUser.getUserName());
		}
		scMoney.setAcceptStatus(dictConfig.getAudit_accept_status());
		scMoney.setRemark(dictConfig.getMoney_vip_remark());
		scMoney.setCreateBy(scRight.getUserId());
		scMoney.setCreateDate(TimeUtil.dateTime2Str(new DateTime(), TimeUtil.DSPdaytimeFormat));
		scMoney.setUpdateBy(scRight.getUserId());
		scMoney.setUpdateDate(TimeUtil.dateTime2Str(new DateTime(), TimeUtil.DSPdaytimeFormat));
		return scMoney;
	}

	/**
	 * 操作数据库 表sc_money插入一条数据，sc_user更改money值
	 * 
	 * @param scRight
	 * @param scUser
	 * @param totalcost
	 * @throws UnsupportedEncodingException
	 */
	@Transactional
	public void setDbValue(ScRight scRight, ScUser scUser, double totalcost) throws UnsupportedEncodingException {
		// 保存ScRight成功后，将出入金表Sc_money加入一条数据
		ScMoney scMoney = new ScMoney();
		scMoneyDao.save(setScmoneyInitValue(scMoney, scRight, totalcost * -1));
		// sc_user表中更改用户的money
		scUser.setMoney(scUser.getMoney() - totalcost);
		// 开通成功之后，重载ScUser
		if (scUserDao.save(scUser) != null) {
			reloadScUser();
		}
	}
}
