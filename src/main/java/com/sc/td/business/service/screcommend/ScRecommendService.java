package com.sc.td.business.service.screcommend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.screcommend.ScRecommendDao;
import com.sc.td.business.dao.scright.ScRightDao;
import com.sc.td.business.entity.scmarket.MarketInfo;
import com.sc.td.business.entity.screcommend.ScRecommend;
import com.sc.td.business.entity.scright.ScRight;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.config.Global;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.calc.ArithDecimal;
import com.sc.td.common.utils.datetime.TimeUtil;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.common.utils.redis.RedisService;
import com.sc.td.frame.DictConfig;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScRecommendService extends BaseService {

	@Autowired
	private ScRecommendDao scRecommendDao;

	@Autowired
	private ScRightDao scRightDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	@Autowired
	private RedisService redisService;

	@Autowired
	private DictConfig dictConfig;

	/**
	 * 获取股票/期货信息 关于用户的VIP逻辑判断是在前台完成，此接口侧重于提供数据
	 * 
	 * @param jsonText
	 * @param type
	 *            为true表示查看VIP数据，false表示普通数据
	 * @return
	 */
	public String getInfo(String jsonText, boolean type) {

		ConcurrentHashMap<String, Boolean> scRecommendVip = BusinessTask.scRecommendVip;
		Boolean recommendVip = scRecommendVip.get("scRecommendVip");

		boolean remainData = false;// 请求非VIP数据接口，判断是否还有剩余数据

		boolean remainVipData = false;// 当有VIP数据时，判断请求过后是否还有数据

		// 解析json数据，获得用户ID
		@SuppressWarnings("unchecked")
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		getPage(jsonText);
		// List<ScRecommend> list=scRecommendDao.nonVip(index,size);
		ConcurrentHashMap<String, List<ScRecommend>> scRecommendMap = BusinessTask.scRecommendMap;
		List<ScRecommend> scRecommendList = scRecommendMap.get("scRecommend");
		if (scRecommendList == null || scRecommendList.size() <= 0) {
			return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
		}

		int listSize = scRecommendList.size();
		int maxIndex = index * size + size;
		if (maxIndex > listSize) {
			maxIndex = listSize;
			remainData = false;
		} else {
			remainData = true;
		}
		List<ScRecommend> list = Lists.newArrayList();
		for (int i = index * size; i < maxIndex; i++) {
			list.add(scRecommendList.get(i));
		}

		// 判断是否点击查看更多
		if (type) {
			String userId = jsonMap.get("userId");
			// 判断用户是否为VIP用户
			List<ScRight> scRightList = scRightDao.findByUserIdAndRightIdAndIsEnable(userId, dictConfig.getRight_vip(),
					dictConfig.getVip_able());
			if (scRightList.size() > 0) {
				ScRight scRight = scRightList.get(0);
				if (Long.parseLong(scRight.getEndDate()) >= Long
						.parseLong(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DBdayFormat))) {
					// 是有效VIP用户
					list = scRecommendDao.findByStockNoAndExchangeNo(Global.recommend_not_vip, index, size);
					if (list.size() <= size) {
						remainVipData = false;
					} else {
						remainVipData = true;
					}
				} else {
					// VIP已过期
					return CreateJson.createTextJson(respInfoConfig.getVipExpire(), false);
				}
			} else {
				return CreateJson.createTextJson(respInfoConfig.getIsnotVip(), false);
			}
		}
		// 整理List的数据
		if (list.size() > 0) {
			// 定义stockList和futureList
			List<ScRecommend> dataList = new ArrayList<ScRecommend>();
			// 遍历list
			for (ScRecommend s : list) {
				// 取stockName
				if (s.getStockName() != null && s.getExchangeNo() != null) {
					s.setDspName(s.getStockName());
					// 得到最新价及涨跌
					s = getPriceAndUad(s);
					dataList.add(s);
				} else if (s.getContractName() != null && s.getExchangeNo() != null) { // 取contractName
					s.setDspName(s.getContractName());
					// 得到最新价及涨跌
					s = getPriceAndUad(s);
					dataList.add(s);
				}
			}
			if (dataList.size() > 0) {
				// 定义map
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("data", dataList);
				map.put("result", true);
				map.put("remainData", remainData);
				map.put("remainVipData", remainVipData);
				map.put("recommendVip", recommendVip);
				return JacksonUtil.objToJson(map);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}

	/**
	 * 获取最新价及涨跌
	 * 
	 * @param scRecommend
	 * @return
	 */
	public ScRecommend getPriceAndUad(ScRecommend scRecommend) {
		// 首先从redis中根据key获取数据
		List<String> dataList = redisService.hmget(Global.redis_price_key,
				scRecommend.getCommodityType() + "," + scRecommend.getStockNo());
		// 如果在redis中有数据
		if (dataList.get(0) != null) {
			String data = dataList.get(0).replace("<", "").replace(">", "").replace("k__BackingField", "");
			// 将该json数据转化为MarketInfo类
			MarketInfo mi = JacksonUtil.jsonToObj(data, MarketInfo.class);
			if (mi != null) {
				double now_price = 0;// 最新价
				double last_price = 0;// 昨日结算价
				if (StringUtils.isNotBlank(mi.getCurrPrice()) && StringUtils.isNotBlank(mi.getOldClose())) {
					now_price = Double.parseDouble(mi.getCurrPrice());
					last_price = Double.parseDouble(mi.getOldClose());
					int dot_num = 0; // 定义小数位
					if ("stock".equals(getStockType(scRecommend.getCommodityType()))) {// 当产品是期货时
						dot_num = scRecommend.getDotNum();
						// 获得十进制的价格
						now_price = getRealPrice(now_price, scRecommend.getLowerTick(), dot_num);
						last_price = getRealPrice(last_price, scRecommend.getLowerTick(), dot_num);
					} else if ("futures".equals(getStockType(scRecommend.getCommodityType()))) {// 产品为股票时
						// sc_stock和sc_upper_tick关联，根据upper_tick_code确定小数位
						dot_num = getDotNum(scRecommend.getUpperTickCode(), now_price);
					}
					String uad = "0.0";
					if (last_price != 0) {
						uad = ArithDecimal.formatDouNum(ArithDecimal.div(now_price - last_price, last_price, dot_num),
								dot_num);
					}
					scRecommend.setPrice(now_price);
					scRecommend.setUad(uad);
				}
			} else {
				scRecommend.setPrice(0.0);
				scRecommend.setUad("0.0");
			}

		}
		return scRecommend;
	}

	/**
	 * 获取股票/期货信息 该接口将逻辑判断的任务放在后台来做，侧重于聚合性 前台只需提供用户ID和索引、数量即可
	 * 
	 * @param jsonText
	 * @return 数据来源取自数据库根据is_vip字段来排序 1、userID为空或者该User不是VIP用户，直接请求数据库的非VIP数据
	 *         2、VIP用户直接根据index，size进行全表查询
	 */
	public String getInfo_new(String jsonText) {
		ConcurrentHashMap<String, Boolean> scRecommendVip = BusinessTask.scRecommendVip;
		Boolean recommendVip = scRecommendVip.get("scRecommendVip");
		List<ScRecommend> scRecommendList = Lists.newArrayList();
		// 解析json数据，获得用户ID
		@SuppressWarnings("unchecked")
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		int index = 0;
		int size = 0;
		String userId = null;
		if (jsonMap.size() > 0) {
			index = Integer.parseInt(StringUtils.isBlank(jsonMap.get("pageno")) ? "0" : jsonMap.get("pageno"));
			size = Integer.parseInt(StringUtils.isBlank(jsonMap.get("size")) ? "0" : jsonMap.get("size"));
			userId = jsonMap.get("userId");
		}
		// 判断用户是否为VIP用户
		boolean b = false;
		List<ScRight> scRightList = scRightDao.findByUserIdAndRightIdAndIsEnable(userId, dictConfig.getRight_vip(),
				dictConfig.getVip_able());
		if (scRightList.size() > 0) {
			ScRight scRight = scRightList.get(0);
			if (Long.parseLong(scRight.getEndDate()) >= Long
					.parseLong(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DBdayFormat))) {
				// 是有效VIP用户
				b = true;
			} else {
				b = false;
			}
		} else {
			b = false;
		}
		if (b) {
			// VIP用户
			scRecommendList = scRecommendDao.findDataOrderByIsVipASC(index, size);
		} else {
			// 非VIP用户
			scRecommendList = scRecommendDao.findNonVipDataOrderByIsVipASC(Global.recommend_not_vip, index, size);
		}
		Map<String, Object> map = Maps.newHashMap();
		map.put("recommendVip", recommendVip);
		// 整理List的数据
		if (scRecommendList != null && scRecommendList.size() > 0) {
			// 定义stockList和futureList
			List<ScRecommend> dataList = new ArrayList<ScRecommend>();
			// 遍历list
			for (ScRecommend s : scRecommendList) {
				// 取stockName
				if (s.getStockName() != null && s.getExchangeNo() != null) {
					s.setDspName(s.getStockName());
					// 得到最新价及涨跌
					s = getPriceAndUad(s);
					dataList.add(s);
				} else if (s.getContractName() != null && s.getExchangeNo() != null) { // 取contractName
					s.setDspName(s.getContractName());
					// 得到最新价及涨跌
					s = getPriceAndUad(s);
					dataList.add(s);
				}
			}
			if (dataList.size() > 0) {
				// 定义map
				map.put("data", dataList);
				map.put("result", true);
				return JacksonUtil.objToJson(map);
			}else {
				map.put(respInfoConfig.getNonData(), false);
				return JacksonUtil.objToJson(map);
			}
		} else {
			map.put(respInfoConfig.getNonData(), false);
			return JacksonUtil.objToJson(map);
		}
	}
}
