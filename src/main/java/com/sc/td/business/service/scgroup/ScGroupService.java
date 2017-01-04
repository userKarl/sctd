package com.sc.td.business.service.scgroup;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.SysDict.SysDictDao;
import com.sc.td.business.dao.scfutures.ScFuturesDao;
import com.sc.td.business.dao.scgroup.GroupDao;
import com.sc.td.business.dao.scgroup.ScGroupDao;
import com.sc.td.business.dao.scpkresult.ScPkResultDao;
import com.sc.td.business.dao.scstock.ScStockDao;
import com.sc.td.business.dao.scstockgroup.StockGroupFDao;
import com.sc.td.business.dao.scstockgroup.StockGroupSDao;
import com.sc.td.business.dao.scuser.ScUserDao;
import com.sc.td.business.entity.scfutures.ScFutures;
import com.sc.td.business.entity.scgroup.BaseGroup;
import com.sc.td.business.entity.scgroup.Group;
import com.sc.td.business.entity.scgroup.ScGroup;
import com.sc.td.business.entity.scstock.ScStock;
import com.sc.td.business.entity.scstockgroup.StockGroup;
import com.sc.td.business.entity.scstockgroup.StockGroupF;
import com.sc.td.business.entity.scstockgroup.StockGroupS;
import com.sc.td.business.entity.scuser.ScUser;
import com.sc.td.business.entity.sysdict.SysDict;
import com.sc.td.common.utils.IdGen;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.calc.ArithDecimal;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.common.utils.page.PageInfo;
import com.sc.td.frame.DictConfig;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScGroupService extends BaseService {

	@Autowired
	private ScUserDao scUserDao;

	@Autowired
	private ScStockDao scStockDao;

	@Autowired
	private ScFuturesDao scFuturesDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private SysDictDao sysDictDao;

	@Autowired
	private ScGroupDao scGroupDaoImpl;

	@Autowired
	private StockGroupSDao stockGroupSDao;

	@Autowired
	private StockGroupFDao stockGroupFDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	@Autowired
	private DictConfig dictConfig;

	@Autowired
	private ScPkResultDao scPkResultDao;

	
	/**
	 * 获取PK榜信息
	 * 
	 * @param jsonText
	 * @return
	 * @throws UnknownHostException
	 */

	public String getrank(String jsonText) throws UnknownHostException {
		// 得到参数数组
		List<Object> paramList = getParaArr(jsonText);
		// 获取rankList
		List<ScGroup> rankList = scGroupDaoImpl.findGroupRankDESC(paramList);
		// 定义返回信息
		HashMap<String, Object> respMap = new HashMap<String, Object>();
		if (rankList != null && rankList.size() > 0) {
			// 整合rankList的数据
			for (ScGroup sc : rankList) {
				sc.setImage(getServerAddress() + sc.getImage());
				// double buysellRatio = getbuysellRatio(sc);// 多空比例
				// sc.setBuysellRatio(buysellRatio);
			}

			// 将rankList信息添加至map
			respMap.put("data", rankList);
			respMap.put("result", true);
			return JacksonUtil.objToJson(respMap);
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}

	/**
	 * 计算多空比例
	 * 
	 * @param sc
	 * @return
	 */
	public double getbuysellRatio(BaseGroup sc) {
		String dict_direct_buy = dictConfig.getGroup_direct_buy();
		String dict_direct_sell = dictConfig.getGroup_direct_sell();
		double buysellRatio = 0;// 多空比例
		// 定义期货价值
		double worth_future_total = 0;// 不考虑多空的价值
		double worth_future_total_real = 0;// 考虑多空的价值
		// 关联期货的视图，确定期货价值
		List<StockGroupF> futureList = stockGroupFDao.findFutureByGroupId(sc.getGroupId());
		if (futureList != null && futureList.size() > 0) {

			for (StockGroupF scsf : futureList) {
				// 获取最新价格
				double now_price_future = getNowPrice(scsf.getCommodityType(), scsf.getStockNo());
				// 进行十进制转化
				now_price_future = getRealPrice(now_price_future, scsf.getLowerTick(), scsf.getDotNum());
				// 获取换算后的价格
				now_price_future = now_price_future * scsf.getExchange();
				// 计算价值
				double unit = ArithDecimal.div(scsf.getLowerTick(), Math.pow(10.0, (double) scsf.getDotNum()));
				double total = now_price_future * (scsf.getProductDot() * unit / scsf.getUpperTick())
						* scsf.getTradeVol();
				// 不考虑多空时
				worth_future_total += total;
				// 考虑多空时
				if (dict_direct_buy.equals(scsf.getDirect())) {
					// 多
					worth_future_total_real += total;
				} else if (dict_direct_sell.equals(scsf.getDirect())) {
					// 空
					worth_future_total_real -= total;
				}
			}
		}
		// 定义股票价值
		double worth_stock_total = 0;// 不考虑多空的价值
		double worth_stock_total_real = 0;// 考虑多空的价值
		// 关联股票的视图，确定股票价值
		List<StockGroupS> stockList = stockGroupSDao.findStockByGroupId(sc.getGroupId());
		if (stockList != null && stockList.size() > 0) {
			for (StockGroupS scs : stockList) {
				// 获取最新价格
				double now_price_stock = getNowPrice(scs.getCommodityType(), scs.getStockNo());
				// 获取换算后的价格
				now_price_stock = now_price_stock * scs.getExchange();
				// 计算价值
				double total = now_price_stock * scs.getTradeVol();
				// 不考虑多空时
				worth_stock_total += total;
				// 考虑多空时
				if (dict_direct_buy.equals(scs.getDirect())) {
					// 多
					worth_stock_total_real += total;
				} else if (dict_direct_sell.equals(scs.getDirect())) {
					// 空
					worth_stock_total_real -= total;
				}
			}
		}
		if (worth_future_total + worth_stock_total != 0) {
			buysellRatio = ArithDecimal.div(worth_future_total_real + worth_stock_total_real,
					worth_future_total + worth_stock_total, 2);
		}
		return buysellRatio;
	}

	/**
	 * 创建战队
	 * 
	 * @param jsonText
	 * @return
	 */
	@Transactional
	public String creategroup_ckt(String jsonText) {
		// 解析json数据
		Group group = JacksonUtil.jsonToObj(jsonText, Group.class);
		if (group != null) {
			// 判断该用户是否存在
			ScUser scuser = scUserDao.findOne(group.getUserId());
			if (scuser == null) {
				return CreateJson.createTextJson(respInfoConfig.getUserNotExist(), false);
			}
			List<Group> scGroupList=groupDao.findByUserId(group.getUserId());
			if(scGroupList!=null && scGroupList.size()>0){
				//限制每个用户只能创建一个战队
				return CreateJson.createTextJson(respInfoConfig.getGroupCreateLimit(), false);
			}
			// 判断组合名是否已经存在
			List<Group> groupList = groupDao.findByGroupName(group.getGroupName());
			if (groupList != null && groupList.size() > 0) {
				return CreateJson.createTextJson(respInfoConfig.getGroupNameExist(), false);
			}
			// 设置Group属性
			group.setGroupId(IdGen.uuid());
			setGroupValue(group);
			List<StockGroup> stockgroupList = group.getStockGroup();
			// 判断所选产品的个数是否已经超过预设值
			int count_pre = 0;
			List<SysDict> dictList = sysDictDao.findByType(dictConfig.getGroup_maxproducts_key());
			if (dictList != null && dictList.size() > 0) {
				count_pre = Integer.parseInt(dictList.get(0).getValue());
			} else {
				count_pre = Integer.parseInt(dictConfig.getGroup_max_products());
			}
			if (stockgroupList.size() > count_pre) {
				return CreateJson.createTextJson(respInfoConfig.getGroupMaxProducts(), false);
			}
			// 判断组合内产品的总投资比例是否大于1
			if (stockgroupList != null && stockgroupList.size() > 0) {
				Double percent = 0.0;
				for (StockGroup sc : stockgroupList) {
					percent += sc.getPercent();
				}
				if (percent > 1.0) {
					return CreateJson.createTextJson(respInfoConfig.getGroupStockPercent(), false);
				}
			}
			// 新增相关联的明细表ScStockGroup数据
			group = setCommodityValue(group);
			if (groupDao.save(group) != null) {
				reloadScUser();
				// 战队创建成功之后，将战队信息返回
				ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap<String, Object>();
				dataMap.put("group", group);
				dataMap.put("result", true);
				return JacksonUtil.objToJson(dataMap);
			} else {
				return CreateJson.createTextJson(respInfoConfig.getCreateGroupError(), false);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getCreateGroupError(), false);
	}

	/**
	 * 修改战队信息(调仓)
	 * 
	 * @param jsonText
	 * @return
	 */
	@Transactional
	public String updategroup_ckt(String jsonText) {
		// 解析json数据
		Group json_group = JacksonUtil.jsonToObj(jsonText, Group.class);
		if (json_group != null) {
			Group group = groupDao.findOne(json_group.getGroupId());
			if (group == null) {
				return CreateJson.createTextJson(respInfoConfig.getGroupNotexist(), false);
			}
			// 判断该战队是否在PK中
			// List<ScPkResult> pklist =
			// scPkResultDao.findByGroupIdAndPkStatus(dictConfig.getPk_status_ing(),
			// json_group.getGroupId());
			// if (pklist.size() > 0) {
			// return CreateJson.createTextJson(respInfoConfig.getPkIng(),
			// false);
			// }

			// 更改时间
			for (StockGroup sg : json_group.getStockGroup()) {
				sg.setInitValue(sg, group.getUserId());
			}
			group.setStockGroup(json_group.getStockGroup());
			groupDao.save(group);
			Map<String, Object> map = Maps.newHashMap();
			map.put("result", true);
			map.put("group", group);
			return JacksonUtil.objToJson(map);
		}
		return CreateJson.createTextJson(respInfoConfig.getSysExeption(), false);
	}

	/**
	 * 根据userId获取战队信息
	 * 
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getGroupInfoByUserId_ckt(String jsonText) {
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null) {
			String userId = jsonMap.get("userId");
			List<Group> groupList = groupDao.findByUserId(userId);
			if (groupList != null && groupList.size() > 0) {
				// 计算多空比例
				for (Group group : groupList) {
					double buysellRatio = getbuysellRatio(group);// 多空比例
					group.setBuysellRatio(buysellRatio);
					// 计算胜率
					double winRate = 0.0;
					if (group.getPkTotal() > 0) {
						winRate = ArithDecimal.div(group.getPkWin(), group.getPkTotal(), 2);
					}
					group.setWinRate(winRate);
					group = getUserInfo(group);
				}
			}
			HashMap<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("data", groupList);
			respMap.put("result", true);
			return JacksonUtil.objToJson(respMap);
		}
		return CreateJson.createTextJson(respInfoConfig.getParamsNull(), false);
	}

	/**
	 * 根据groupId获取战队信息
	 * 
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getGroupInfoByGroupId(String jsonText) {
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null) {
			String groupId = jsonMap.get("groupId");
			Group group = groupDao.findOne(groupId);
			if (group != null) {
				// 计算多空比例
				double buysellRatio = getbuysellRatio(group);// 多空比例
				group.setBuysellRatio(buysellRatio);
				// 计算胜率
				double winRate = 0.0;
				if (group.getPkTotal() > 0) {
					winRate = ArithDecimal.div(group.getPkWin(), group.getPkTotal(), 2);
				}
				group.setWinRate(winRate);
				group = setCommodityValue(group);
				group = getUserInfo(group);
			}
			HashMap<String, Object> respMap = new HashMap<String, Object>();
			respMap.put("data", group);
			respMap.put("result", true);
			return JacksonUtil.objToJson(respMap);
		}
		return CreateJson.createTextJson(respInfoConfig.getParamsNull(), false);
	}

	/**
	 * 供内部调用的方法 根据groupID获取战队信息
	 * 
	 * @param jsonText
	 * @return
	 */
	public Group getGroupInfo(String groupId) {
		Group group = groupDao.findOne(groupId);
		if (group != null) {
			// 计算多空比例
			double buysellRatio = getbuysellRatio(group);// 多空比例
			group.setBuysellRatio(buysellRatio);
			// 计算胜率
			double winRate = 0.0;
			if (group.getPkTotal() > 0) {
				winRate = ArithDecimal.div(group.getPkWin(), group.getPkTotal(), 2);
			}
			group.setWinRate(winRate);
			group = setCommodityValue(group);
			group = getUserInfo(group);
		}
		return group;
	}

	/**
	 * 设置StockGroup内的ScStock和ScFutures的值
	 * 
	 * @param group
	 * @return
	 */
	public Group setCommodityValue(Group group) {
		List<StockGroup> stockgroupList = group.getStockGroup();
		if (stockgroupList != null && stockgroupList.size() > 0) {
			for (int i = 0; i < stockgroupList.size(); i++) {
				StockGroup sg = stockgroupList.get(i);
				sg.setGroupId(group.getGroupId());
				sg.setInitValue(stockgroupList.get(i), group.getUserId());
				// 获取价格
				sg.setPrice(getNowPrice(sg.getCommodityType(), sg.getStockNo()));

				if ("stock".equals(getStockType(sg.getCommodityType()))) {
					ScStock sc = scStockDao.findByExchangeNoAndStockNoAndCommodityType(sg.getExchangeNo(),
							sg.getStockNo(), sg.getCommodityType());
					if (sc != null) {
						sg.setScStock(sc);
					}
				}
				if ("futures".equals(getStockType(sg.getCommodityType()))) {
					// 期货时，获取保证金
					ScFutures scf = scFuturesDao.findByExchangeNoAndCodeAndCommodityType(sg.getExchangeNo(),
							sg.getStockNo(), sg.getCommodityType());
					if (scf != null) {
						sg.setScFutures(scf);
					}
				}

			}
		}
		return group;
	}

	/**
	 * 开启或关闭PK
	 * 
	 * @param jsonText
	 * @return
	 */

	@SuppressWarnings("unchecked")
	@Transactional
	public String setAllowPk_ckt(String jsonText) {
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null) {
			String groupId = jsonMap.get("groupId");
			String isAllowPk = jsonMap.get("isAllowPk");
			Group group = groupDao.findOne(groupId);
			if (group != null) {
				group.setIsAllowPk(isAllowPk);
				groupDao.save(group);
				return CreateJson.createTextJson(respInfoConfig.getOperateSuccess(), true);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getOperateFail(), false);
	}

	/**
	 * 设置Group属性
	 * 
	 * @param group
	 * @return
	 */
	public Group setGroupValue(Group group) {
		group.setGroupType(dictConfig.getGroup_type_personal());
		group.setPkTotal(0);
		group.setPkRowWin(0);
		group.setPkWin(0);
		group.setAcceptPkTimes(0);
		group.setIsAllowPk(dictConfig.getGroup_pk_allow());
		group.setInitValue(group, group.getUserId());
		return group;
	}

	/**
	 * 设置查询方法的参数
	 * 
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getParaArr(String jsonText) {
		List<Object> list = new ArrayList<Object>();
		String minWinRate = dictConfig.getGroup_minWinRate();
		String maxWinRate = dictConfig.getGroup_maxWinRate();
		String minRowWin = dictConfig.getGroup_minRowWin();
		String maxRowWin = dictConfig.getGroup_maxRowWin();
		String minTotal = dictConfig.getGroup_minTotal();
		String maxTotal = dictConfig.getGroup_maxTotal();
		String accpetTimes = dictConfig.getGroup_accept_pktimes();
		String groupName = "";
		// 解析json数据
		int index = PageInfo.index;
		int size = PageInfo.size;
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null && jsonMap.size() > 0) {
			String json_pageno = String.valueOf(jsonMap.get("pageno"));
			String json_size = String.valueOf(jsonMap.get("size"));
			index = PageInfo.getIndex(PageInfo.getPageNo(json_pageno), PageInfo.getSize(json_size));
			size = PageInfo.getSize(json_size);
			String json_compare_winRate = jsonMap.get("compare_winRate");
			String json_winRate = jsonMap.get("winRate");
			String json_compare_rowWin = jsonMap.get("compare_rowWin");
			String json_rowWin = jsonMap.get("rowWin");
			String json_compare_total = jsonMap.get("compare_total");
			String json_total = jsonMap.get("total");
			String json_groupName = jsonMap.get("groupName");
			if (StringUtils.isNotBlank(json_winRate)) {
				if (dictConfig.getCompare_gt().equals(json_compare_winRate)) {
					// 胜率大于
					minWinRate = json_winRate;
				} else if (dictConfig.getCompare_lt().equals(json_compare_winRate)) {
					// 胜率小于
					maxWinRate = ""+(Float.parseFloat(json_winRate)-0.001);
				}
			}
			if (StringUtils.isNotBlank(json_rowWin)) {
				if (dictConfig.getCompare_gt().equals(json_compare_rowWin)) {
					// 连胜大于
					minRowWin = json_rowWin;
				} else if (dictConfig.getCompare_lt().equals(json_compare_rowWin)) {
					// 连胜小于
					maxRowWin = json_rowWin;
				}
			}
			if (StringUtils.isNotBlank(json_total)) {
				if (dictConfig.getCompare_gt().equals(json_compare_total)) {
					// 总场次大于
					minTotal = json_total;
				} else if (dictConfig.getCompare_lt().equals(json_compare_total)) {
					// 总场次小于
					maxTotal = json_total;
				}
			}
			if (StringUtils.isNotBlank(json_groupName)) {
				groupName = json_groupName;
			}
		}
		list.add(minWinRate);
		list.add(maxWinRate);
		list.add(minRowWin);
		list.add(maxRowWin);
		list.add(minTotal);
		list.add(maxTotal);
		list.add(accpetTimes);
		list.add(groupName);
		list.add(index);
		list.add(size);
		return list;
	}

	/**
	 * 批处理程序，每天0点将当天可接受的PK次数设置为0
	 */
	@Transactional
	public void setPkTimes() {
		List<Group> list = groupDao.findAll();
		if (list.size() > 0) {
			for (Group group : list) {
				group.setAcceptPkTimes(0);
				groupDao.save(group);
			}
		}
	}
}
