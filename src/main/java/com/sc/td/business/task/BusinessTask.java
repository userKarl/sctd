package com.sc.td.business.task;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.dao.scpklevel.ScPklevelDao;
import com.sc.td.business.entity.sccurrency.ScCurrency;
import com.sc.td.business.entity.scexchange.ScExchange;
import com.sc.td.business.entity.scfutures.ScFutures;
import com.sc.td.business.entity.scindexresult.ScIndexResult;
import com.sc.td.business.entity.scpklevel.ScPklevel;
import com.sc.td.business.entity.scplate.ScPlateView;
import com.sc.td.business.entity.screcommend.ScRecommend;
import com.sc.td.business.entity.scstock.ScStock;
import com.sc.td.business.entity.scuppertick.ScUpperTick;
import com.sc.td.business.entity.scuser.ScUser;
import com.sc.td.business.entity.sysdict.SysDict;
import com.sc.td.business.service.BusinessTaskService;
import com.sc.td.business.service.scgroup.ScGroupService;
import com.sc.td.common.config.Global;
import com.sc.td.common.utils.StringUtils;

/**
 * 定時任務,加载基本信息
 * 
 * @author Administrator
 *
 */
@Controller
public class BusinessTask {

	@Autowired
	private BusinessTaskService businessTaskService;

	@Autowired
	private ScPklevelDao scPklevelDao;

	@Autowired
	private ScGroupService scGroupService;

	public static ConcurrentHashMap<String, ConcurrentHashMap<String, ScUser>> scUserMap = new ConcurrentHashMap<String, ConcurrentHashMap<String, ScUser>>();

	public static ConcurrentHashMap<String, List<ScPlateView>> scPlateViewMap = new ConcurrentHashMap<String, List<ScPlateView>>();

	public static ConcurrentHashMap<String, List<ScCurrency>> scCurrencyMap = new ConcurrentHashMap<String, List<ScCurrency>>();

	public static ConcurrentHashMap<String, List<ScUpperTick>> scUpperTickMap = new ConcurrentHashMap<String, List<ScUpperTick>>();

	public static ConcurrentHashMap<String, List<ScStock>> scStockMap = new ConcurrentHashMap<String, List<ScStock>>();

	public static ConcurrentHashMap<String, List<ScFutures>> scFuturesMap = new ConcurrentHashMap<String, List<ScFutures>>();

	public static ConcurrentHashMap<String, List<ScRecommend>> scRecommendMap = new ConcurrentHashMap<String, List<ScRecommend>>();

	public static ConcurrentHashMap<String, List<SysDict>> sysDictMap = new ConcurrentHashMap<String, List<SysDict>>();

	public static ConcurrentHashMap<String, List<ScExchange>> scExchangeMap = new ConcurrentHashMap<String, List<ScExchange>>();

	public static ConcurrentHashMap<String, List<ScIndexResult>> scIndexResultMap = new ConcurrentHashMap<String, List<ScIndexResult>>();
	
	public static ConcurrentHashMap<String, Boolean> scRecommendVip = new ConcurrentHashMap<String, Boolean>();

	/**
	 * 重新加载
	 * 
	 * @param reloadPassword
	 * @return
	 */
	@RequestMapping(value = { "/reload/{reloadType}" }, method = { RequestMethod.GET, RequestMethod.POST })
	@ResponseBody
	public String reload(@PathVariable(value = "reloadType") String reloadType) {
		try {
			if (Global.reload_all.equals(reloadType)) {
				oneDayStart();
			} else if (Global.reload_scuser.equals(reloadType)) {
				loadScUser();
			} else if (Global.reload_scplate.equals(reloadType)) {
				loadScPlateView();
			} else if (Global.reload_sccurrency.equals(reloadType)) {
				loadScCurrency();
			} else if (Global.reload_scuppertick.equals(reloadType)) {
				loadScUpperTick();
			} else if (Global.reload_scstock.equals(reloadType)) {
				loadScStock();
			} else if (Global.reload_scfutures.equals(reloadType)) {
				loadScFutures();
			} else if (Global.reload_sysDict.equals(reloadType)) {
				loadSysDict();
			} else if (Global.reload_screcommend.equals(reloadType)) {
				loadScRecommend();
			} else if (Global.reload_scexchange.equals(reloadType)) {
				loadScExchange();
			} else if (Global.reload_scindexresult.equals(reloadType)) {
				loadScIndexResult();
			} else if (Global.reload_recommend_vip.equals(reloadType)) {
				loadScRecommendVip();
			}

			return "reload " + reloadType + " success";
		} catch (Exception e) {
			return "reload " + reloadType + " fail";
		}
	}

	@Scheduled(initialDelay = 1000 * 5, fixedRate = 1000 * 60 * 60 * 24 * 15)
	public void reStartFix() {
		oneDayStart();
		setPkTimes();
	}

	/**
	 * 每月15日
	 */
	@Scheduled(cron = "0 0 0 15 * ?")
	public void oneDayStart() {
		loadScUser();
		loadScPlateView();
		loadScCurrency();
		loadScUpperTick();
		loadScStock();
		loadScFutures();
		loadSysDict();
		loadScRecommend();
		loadScRecommendVip();
		loadScExchange();
		loadScIndexResult();
	}

	/**
	 * 每天
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void everyDayStart() {
		loadScUser();
		loadScStock();
		loadScFutures();
		loadScRecommend();
		loadScRecommendVip();
		loadScIndexResult();
		setPkTimes();
	}

	/**
	 * 加载用户信息
	 */
	public void loadScUser() {
		List<ScUser> userList = businessTaskService.getScUser();
		if (userList.size() > 0) {
			ConcurrentHashMap<String, ScUser> map = new ConcurrentHashMap<String, ScUser>();
			String ip = Global.server_ip;
			String contextPort = Global.contextPort;
			String contextPath = Global.contextPath;
			for (ScUser sc : userList) {
				sc.setImage(Global.protocolSSL+"://" + ip + ":" + contextPort + contextPath
						+ (sc.getImage() == null ? "" : sc.getImage()));
				if(StringUtils.isNotBlank(sc.getLevelId())){
					ScPklevel scPklevel=scPklevelDao.findByLevelId(sc.getLevelId());
					if(scPklevel!=null && StringUtils.isNoneBlank(scPklevel.getLevelName())){
						sc.setLevelName(scPklevel.getLevelName());
					}
				}
				map.put(sc.getUserId(), sc);
			}
			scUserMap.put("scUser", map);
		}
	}

	/**
	 * 加载板块
	 */
	public void loadScPlateView() {
		scPlateViewMap.put("scPlate", businessTaskService.getScPlateView());
	}

	/**
	 * 加载汇率
	 */
	public void loadScCurrency() {
		scCurrencyMap.put("scCurrency", businessTaskService.getScCurrency());
	}

	/**
	 * 加载跳点
	 */
	public void loadScUpperTick() {
		scUpperTickMap.put("scUpperTick", businessTaskService.getScUpperTick());
	}

	/**
	 * 加载股票
	 */
	public void loadScStock() {
		scStockMap.put("scStock", businessTaskService.getScStock());
	}

	/**
	 * 加载期货
	 */
	public void loadScFutures() {
		scFuturesMap.put("scFutures", businessTaskService.getScFutures());
	}

	/**
	 * 加载数据字典
	 */
	public void loadSysDict() {
		sysDictMap.put("sysDict", businessTaskService.getSysDict());
	}

	/**
	 * 加载推荐信息
	 */
	public void loadScRecommend() {
		scRecommendMap.put("scRecommend", businessTaskService.getScRecommend());
	}

	/**
	 * 查询推荐表内是否有VIP用户查看的信息
	 * true为有
	 * false为没有
	 */
	public void loadScRecommendVip() {
		scRecommendVip.put("scRecommendVip", businessTaskService.getScRecommendVip());
	}
	
	/**
	 * 加载交易所信息
	 */
	public void loadScExchange() {
		scExchangeMap.put("scExchange", businessTaskService.getScExchange());
	}

	/**
	 * 加载指标计算结果
	 */
	public void loadScIndexResult() {
		scIndexResultMap.put("scIndexResult", businessTaskService.getScIndexResult());
	}

	
	/**
	 * 每天0点将当天可接受的PK次数设置为0
	 */
	public void setPkTimes() {
		scGroupService.setPkTimes();
	}
}
