package com.sc.td.business.base;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.td.business.dao.SysDict.SysDictDao;
import com.sc.td.business.dao.scmoney.ScMoneyDao;
import com.sc.td.business.dao.scpklevel.ScPklevelDao;
import com.sc.td.business.dao.scuppertick.ScUpperTickDao;
import com.sc.td.business.dao.scuser.ScUserDao;
import com.sc.td.business.entity.scgroup.Group;
import com.sc.td.business.entity.scmarket.MarketInfo;
import com.sc.td.business.entity.scmoney.ScMoney;
import com.sc.td.business.entity.scpklevel.ScPklevel;
import com.sc.td.business.entity.scpkresult.ScPkResult;
import com.sc.td.business.entity.scuppertick.ScUpperTick;
import com.sc.td.business.entity.scuser.ScUser;
import com.sc.td.business.entity.sysdict.SysDict;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.config.Global;
import com.sc.td.common.utils.GetHttpMsg;
import com.sc.td.common.utils.IdGen;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.calc.ArithDecimal;
import com.sc.td.common.utils.datetime.TimeUtil;
import com.sc.td.common.utils.ip.IpUtils;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.common.utils.page.PageInfo;
import com.sc.td.common.utils.redis.RedisService;
import com.sc.td.frame.DictConfig;

@Service
public class BaseService {

	public static int index = PageInfo.index;
	public static int size = PageInfo.size;

	@Autowired
	private SysDictDao sysDictDao;

	@Autowired
	private DictConfig dictConfig;

	@Autowired
	private RedisService redisService;

	@Autowired
	private ScUpperTickDao scUpperTickDao;

	@Autowired
	private ScPklevelDao scPklevelDao;

	@Autowired
	private ScMoneyDao scMoneyDao;

	@Autowired
	private ScUserDao scUserDao;

	/**
	 * 获取可配置的完整IP地址
	 * @return
	 */
	public String getServerAddress(){
		String ip = Global.server_ip;
		String contextPort = Global.contextPort;
		String contextPath = Global.contextPath;
		return Global.protocolSSL+"://" + ip + ":" + contextPort + contextPath;
	}
	
	/**
	 * 重载注册用户
	 * @param scUser
	 */
	public void reloadScUser(){
			String ip = IpUtils.getLocalIP();
			String contextPort = Global.reload_port;
			String contextPath = Global.contextPath;
			GetHttpMsg.getHttp(Global.protocol+"://" + ip + ":" + contextPort + contextPath+"/reload/"+Global.reload_scuser);
	}
	
	/**
	 * 获取最小用户等级的ID
	 * 
	 * @return
	 */
	public String getMinLevelId() {
		ScPklevel sc = scPklevelDao.findMinLevel();
		if (sc != null) {
			return sc.getLevelId();
		}
		return null;
	}

	/**
	 * 获取页面信息
	 * 
	 * @param jsonText
	 */
	public void getPage(String jsonText) {
		// 解析json数据，获得用户ID、页码index及数量size
		@SuppressWarnings("unchecked")
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null) {
			String json_pageno = String.valueOf(jsonMap.get("pageno"));
			String json_size = String.valueOf(jsonMap.get("size"));
			index = PageInfo.getIndex(PageInfo.getPageNo(json_pageno), PageInfo.getSize(json_size));
			size = PageInfo.getSize(json_size);
		}
	}

	/**
	 * 判断是股票还是期货
	 * 
	 * @param type
	 * @return
	 */
	public String getStockType(String type) {
		if (type.startsWith("1")) {
			type = "stock";
		} else if (type.startsWith("0")) {
			type = "futures";
		}
		return type;
	}

	/**
	 * 获取最新价
	 * 
	 * @param commodityType
	 * @param stockNo
	 * @return
	 */
	public double getNowPrice(String commodityType, String stockNo) {
		// 首先从redis中根据key获取数据
		List<String> dataList = redisService.hmget(Global.redis_price_key, commodityType + "," + stockNo);
		// 如果在redis中有数据
		if (dataList.get(0) != null) {
			String data = dataList.get(0).replace("<", "").replace(">", "").replace("k__BackingField", "");
			//将该json数据转化为MarketInfo类
			MarketInfo mi=JacksonUtil.jsonToObj(data, MarketInfo.class);
			if(mi!=null && StringUtils.isNotBlank(mi.getCurrPrice())){
				return Double.parseDouble(mi.getCurrPrice());
			}
		}
		return 0;
	}

//	/**
//	 * 获取最新价
//	 * 
//	 * @param commodityType
//	 * @param stockNo
//	 * @return
//	 */
//	public double getNowPrice(String commodityType, String stockNo) {
//		// 首先从redis中根据key获取数据
//		List<String> dataList = redisService.hmget(systemConfig.getRedisPriceKey(), commodityType + "," + stockNo);
//		// 如果在redis中有数据
//		if (dataList.get(0) != null) {
//			String data = dataList.get(0);
//			String[] info = data.split(","); // 得到数组 :[0]最新价 [1]最近一笔的成交量
//												// [2]昨日结算价 [3]最新行情时间
//			// 符合上述长度时
//			if (info.length == 4) {
//				double now_price = 0;// 最新价
//				if (StringUtils.isNotBlank(info[0]) && StringUtils.isNotBlank(info[2])) {
//					now_price = Double.parseDouble(info[0]);
//
//				}
//				return now_price;
//			}
//		}
//		return 0;
//	}
	
	/**
	 * 获取涨跌
	 * 
	 * @param commodityType
	 * @param stockNo
	 * @param dotNum
	 * @return
	 */
	public String getUadPct(String commodityType, String stockNo, int dotNum) {
		// 首先从redis中根据key获取数据
		List<String> dataList = redisService.hmget(Global.redis_price_key, commodityType + "," + stockNo);
		// 如果在redis中有数据
		if (dataList.get(0) != null) {
			String data = dataList.get(0).replace("<", "").replace(">", "").replace("k__BackingField", "");
			//将该json数据转化为MarketInfo类
			MarketInfo mi=JacksonUtil.jsonToObj(data, MarketInfo.class);
			double now_price = 0;// 最新价
			double last_price = 0;// 昨日结算价
			if(mi!=null && StringUtils.isNotBlank(mi.getCurrPrice())){
			now_price = Double.parseDouble(mi.getCurrPrice());
			last_price = Double.parseDouble(mi.getOldClose());
			String uad = "0.0";
			if (last_price != 0) {
				uad = ArithDecimal.formatDouNum(ArithDecimal.div(now_price - last_price, last_price, dotNum),
						dotNum);
			}
			return uad;
			}
		}
		return "0.0";
	}

	/**
	 * 股票时，根据upper_tick_code获取小数位
	 * 
	 * @param upperTickCode
	 * @return
	 */
	public int getDotNum(String upperTickCode, double now_price) {
		// 根据upperTickCode获取跳点信息
		List<ScUpperTick> upperTickList = scUpperTickDao.findByUpperTickCodeOrderByPriceFromAsc(upperTickCode);
		if (upperTickList.size() > 0) {
			// 如果找到对应的跳点信息
			// 遍历upperTickList
			int dot_num = 3;
			for (ScUpperTick s : upperTickList) {
				if (now_price <= Double.parseDouble(s.getPriceFrom())) {
					break;
				}
				dot_num = s.getDotNum();
			}
			return dot_num;
		}
		return 3; // 默认返回
	}

	/**
	 * 根据type获取数据字典的数据 此方法只适用于单一值的数据
	 * 
	 * @param type
	 * @return
	 */
	public String getSysDictValue(String type) {
		List<SysDict> list = sysDictDao.findByType(type);
		if (list.size() > 0) {
			return list.get(0).getValue();
		}
		return null;
	}

	/**
	 * 根据type获取数据字典的数据
	 * 
	 * @param type
	 * @return
	 */
	public List<SysDict> getSysDictList(String type) {
		List<SysDict> list = sysDictDao.findByType(type);
		if (list.size() > 0) {
			return list;
		}
		return null;
	}

	/**
	 * 获取初始PK资金值
	 * 
	 * @return
	 */
	public Double getIniBalance() {
		Double ini_balance = 0.0;
		String dictValue = getSysDictValue(dictConfig.getPk_inibalance_key());
		if (StringUtils.isNotBlank(dictValue)) {
			ini_balance = Double.parseDouble(dictValue);
		}
		return ini_balance;
	}

	/**
	 * 根据行情信息取得十进制价格的显示进制的价格
	 * 
	 * @param realPrice
	 * @return
	 */
	public double getDisplayPrice(double realPrice, int lowerTick, int dotNum) {
		try {
			double unit = ArithDecimal.div(lowerTick, Math.pow(10.0, (double) dotNum));
			if (unit != 1) {
				return ArithDecimal.add(ArithDecimal.mul(ArithDecimal.sub(realPrice, (int) realPrice), unit),
						(int) realPrice);
			} else {
				return realPrice;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 根据行情信息取得真实的十进制价格
	 * 
	 * @param price
	 * @return
	 */
	public double getRealPrice(double price, int lowerTick, int dotNum) {
		try {
			double unit = ArithDecimal.div(lowerTick, Math.pow(10.0, (double) dotNum));
			if (unit != 1 && lowerTick != 0) {
				return ArithDecimal.add(ArithDecimal.div(ArithDecimal.sub(price, (int) price), unit), (int) price);
			} else {
				return price;
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return 0;
	}

	/**
	 * 设置开始时间和结束时间
	 * 
	 * @param sc
	 * @return
	 */
	public ScPkResult setDate(ScPkResult sc) {
		DateTime dateTime = DateTime.now();
		int day = dateTime.getDayOfWeek();
		int plusTime = 0;
		if (day == DateTimeConstants.FRIDAY) {
			plusTime = 3;
		} else if (day == DateTimeConstants.SATURDAY) {
			plusTime = 2;
		} else {
			plusTime = 1;
		}
		DateTime startTime = TimeUtil.plusTime(dateTime, plusTime, TimeUtil.DAYS);
		sc.setStartDate(TimeUtil.dateTime2Str(startTime, TimeUtil.DBdayFormat));
		// 获取PK的天数
		int pkDays = 0;
		List<SysDict> dictList = sysDictDao.findByType(dictConfig.getPk_days_key());
		if (dictList.size() > 0) {
			pkDays = Integer.parseInt(dictList.get(0).getValue());
		}
		int endPlusTime = 0;
		// 计算开始日期到结束日期有多少个星期六
		int count = TimeUtil.countSatd(startTime, pkDays - 1);
		// 结束日期
		DateTime dt = startTime.plusDays(pkDays - 1);
		int endDay = dt.getDayOfWeek();
		if (endDay == DateTimeConstants.SATURDAY) {
			endPlusTime = (count - 1) * 2 + 2;
		} else if (endDay == DateTimeConstants.SUNDAY) {
			endPlusTime = (count - 1) * 2 + 1;
		} else {
			endPlusTime = count * 2;
		}
		DateTime endTime = TimeUtil.plusTime(dt, endPlusTime, TimeUtil.DAYS);
		sc.setEndDate(TimeUtil.dateTime2Str(endTime, TimeUtil.DBdayFormat));
		return sc;
	}

	/**
	 * 根据PK开始时间、PK天数获得PK结束时间
	 * 
	 * @param startTime
	 * @return
	 */
	public DateTime getEndTime(DateTime startTime) {
		int pkDays = 0;
		if (StringUtils.isNotBlank(getSysDictValue(dictConfig.getPk_days_key()))) {
			pkDays = Integer.parseInt(getSysDictValue(dictConfig.getPk_days_key()));
		}
		int endPlusTime = 0;
		// 计算开始日期到结束日期有多少个星期六
		int count = TimeUtil.countSatd(startTime, pkDays - 1);
		// 结束日期
		DateTime dt = startTime.plusDays(pkDays - 1);
		int endDay = dt.getDayOfWeek();
		if (endDay == DateTimeConstants.SATURDAY) {
			endPlusTime = (count - 1) * 2 + 2;
		} else if (endDay == DateTimeConstants.SUNDAY) {
			endPlusTime = (count - 1) * 2 + 1;
		} else {
			endPlusTime = count * 2;
		}
		DateTime endTime = TimeUtil.plusTime(dt, endPlusTime, TimeUtil.DAYS);
		return endTime;
	}

	/**
	 * 新建出入金记录，并设置公共属性（不包含金额money、备注） 出入金状态默认为接受
	 * 
	 * @param scUser
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public ScMoney setInitScMoney(ScUser scUser) {
		ScMoney scMoney = new ScMoney();
		scMoney.setSeqNo(IdGen.uuid());
		scMoney.setUserId(scUser.getUserId());
		scMoney.setApplyTime(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
		scMoney.setAcceptTime(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
		scMoney.setApplyUserName(scUser.getUserName());
		scMoney.setAcceptUserName(scUser.getUserName());
		scMoney.setAcceptStatus(dictConfig.getAudit_accept_status());
		scMoney.setInitValue(scMoney, scUser.getUserId());
		return scMoney;
	}

	/**
	 * 更新用户PK等级
	 * 根据用户表的虚拟币来比较
	 * @param scUser
	 */
	public void updatePkLevel(ScUser scUser) {
		String levelId = scUser.getLevelId();
		Double money=0.0;
		ScUser sc=scUserDao.findByUserId(scUser.getUserId());
		if(sc!=null){
			money=sc.getMoney();
			// 获取PK等级的虚拟币
			List<ScPklevel> scPklevelList = scPklevelDao.findByMoneyOrderByMoneyAsc();
			if (scPklevelList.size() > 0) {
				if (money > scPklevelList.get(0).getMoney()) {
					for (int i = 1; i < scPklevelList.size(); i++) {
						if (money < scPklevelList.get(i).getMoney()) {
							levelId = scPklevelList.get(i - 1).getLevelId();
							break;
						}
					}
				}
			}
			if(Integer.parseInt(sc.getLevelId())<Integer.parseInt(levelId)){
				sc.setLevelId(levelId);
				sc.setUpdateDate(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
				scUserDao.save(sc);
			}
		}
	}
	
//	/**
//	 * 更新用户PK等级
//	 * 根据入金金额判断
//	 * @param scUser
//	 */
//	public void updatePkLevel(ScUser scUser) {
//		String levelId = scUser.getLevelId();
//		// 获取入金数据(money>0)
//		List<ScMoney> scMoneyList = scMoneyDao.findByUserIdAndAcceptStatusAndMoneyGreaterThan(scUser.getUserId(),dictConfig.getAudit_accept_status(), 0.0);
//		Double totalMoney = 0.0;
//		if (scMoneyList.size() > 0) {
//			for (ScMoney sc : scMoneyList) {
//				totalMoney += sc.getMoney();
//			}
//		}
//		// 获取PK等级的虚拟币
//		List<ScPklevel> scPklevelList = scPklevelDao.findByMoneyOrderByMoneyAsc();
//		if (scPklevelList.size() > 0) {
//			if (totalMoney > scPklevelList.get(0).getMoney()) {
//				for (int i = 1; i < scPklevelList.size(); i++) {
//					if (totalMoney < scPklevelList.get(i).getMoney()) {
//						levelId = scPklevelList.get(i - 1).getLevelId();
//						break;
//					}
//				}
//			}
//		}
//		ScUser sc=scUserDao.findByUserId(scUser.getUserId());
//		if(sc!=null && Integer.parseInt(sc.getLevelId())<Integer.parseInt(levelId)){
//			sc.setLevelId(levelId);
//			sc.setUpdateDate(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
//			scUserDao.save(sc);
//		}
//	}
	
	/**
	 * 根据group信息查找出关联的用户信息
	 * 
	 * @param group
	 * @return
	 */
	public Group getUserInfo(Group group) {
		ConcurrentHashMap<String, ConcurrentHashMap<String, ScUser>> scUserMap = BusinessTask.scUserMap;
		ConcurrentHashMap<String, ScUser> userMap = new ConcurrentHashMap<String, ScUser>();
		if (scUserMap != null) {
			userMap = scUserMap.get("scUser");
		}
		// 设置Group的值
		if (group != null && userMap != null) {
				group.setUserName(userMap.get(group.getUserId()).getUserName());
				group.setImage(userMap.get(group.getUserId()).getImage());
				group.setPkLevelName(userMap.get(group.getUserId()).getLevelName());
		}
		return group;
	}
	
	/**
	 * 将yyyymmdd的格式改为yyyy-mm-dd
	 * @param date
	 * @return
	 */
	public String formatDate(String date){
		if(date.length()>=8){
			date=date.substring(0, 4)+"-"+date.substring(4,6)+"-"+date.substring(6,date.length());
		}
		return date;
	}
}
