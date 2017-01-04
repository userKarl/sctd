package com.sc.td.business.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.sc.td.business.dao.SysDict.SysDictDao;
import com.sc.td.business.dao.sccurrency.ScCurrencyDao;
import com.sc.td.business.dao.scexchange.ScExchangeDao;
import com.sc.td.business.dao.scfutures.ScFuturesDao;
import com.sc.td.business.dao.scindexresult.ScIndexResultDao;
import com.sc.td.business.dao.scplate.ScPlateDao;
import com.sc.td.business.dao.scplate.ScPlateProductDao;
import com.sc.td.business.dao.scplate.ScPlateViewDao;
import com.sc.td.business.dao.screcommend.ScRecommendDao;
import com.sc.td.business.dao.scstock.ScStockDao;
import com.sc.td.business.dao.scuppertick.ScUpperTickDao;
import com.sc.td.business.dao.scuser.ScUserDao;
import com.sc.td.business.entity.sccurrency.ScCurrency;
import com.sc.td.business.entity.scexchange.ScExchange;
import com.sc.td.business.entity.scfutures.ScFutures;
import com.sc.td.business.entity.scindexresult.ScIndexResult;
import com.sc.td.business.entity.scplate.ScPlate;
import com.sc.td.business.entity.scplate.ScPlateProduct;
import com.sc.td.business.entity.scplate.ScPlateView;
import com.sc.td.business.entity.screcommend.ScRecommend;
import com.sc.td.business.entity.scstock.ScStock;
import com.sc.td.business.entity.scuppertick.ScUpperTick;
import com.sc.td.business.entity.scuser.ScUser;
import com.sc.td.business.entity.sysdict.SysDict;
import com.sc.td.common.config.Global;
import com.sc.td.common.utils.datetime.TimeUtil;

@Service
public class BusinessTaskService {

	@Autowired
	private ScPlateViewDao scPlateViewDao;

	@Autowired
	private ScPlateDao scPlateDao;

	@Autowired
	private ScPlateProductDao scPlateProductDao;

	@Autowired
	private ScCurrencyDao scCurrencyDao;

	@Autowired
	private ScUpperTickDao scUpperTickDao;

	@Autowired
	private ScStockDao scStockDao;

	@Autowired
	private ScFuturesDao scFuturesDao;

	@Autowired
	private SysDictDao sysDictDao;

	@Autowired
	private ScUserDao scUserDao;

	@Autowired
	private ScRecommendDao scRecommendDao;

	@Autowired
	private ScExchangeDao scExchangeDao;

	@Autowired
	private ScIndexResultDao scIndexResultDao;
	
	/**
	 * 获取板块信息
	 * 
	 * @return
	 */
	public List<ScPlateView> getScPlateView() {
		return scPlateViewDao.findAll();
	}

	/**
	 * 获取汇率信息
	 * 
	 * @return
	 */
	public List<ScCurrency> getScCurrency() {
		return scCurrencyDao.findAll();
	}

	/**
	 * 获取跳点信息
	 * 
	 * @return
	 */
	public List<ScUpperTick> getScUpperTick() {
		return scUpperTickDao.findAll();
	}

	/**
	 * 获取股票信息
	 * 
	 * @return
	 */
	public List<ScStock> getScStock() {
		return scStockDao.findAll();
	}

	/**
	 * 获取期货信息
	 * 
	 * @return
	 */
	public List<ScFutures> getScFutures() {
		//获取过期月份
		String expiryDate=TimeUtil.dateTime2Str(TimeUtil.minusTime(DateTime.now(),1, TimeUtil.MONTHS), TimeUtil.DBMONTHFormat);
		return scFuturesDao.findByExpiryDateGreaterThan(expiryDate);

	}

	/**
	 * 获取数据字典
	 * 
	 * @return
	 */
	public List<SysDict> getSysDict() {
		return sysDictDao.findByRemarks("sc");
	}

	/**
	 * 获取推荐的信息(非VIP数据)
	 * 
	 * @return
	 */
	public List<ScRecommend> getScRecommend() {
		return scRecommendDao.nonVip(Global.recommend_not_vip);
	}

	/**
	 * 查询推荐表内是否有VIP用户查看的信息
	 * 
	 * @return
	 */
	public Boolean getScRecommendVip() {
		int size=scRecommendDao.findByIsVip(Global.recommend_vip).size();
		if(size>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取用户信息
	 * 
	 * @return
	 */
	public List<ScUser> getScUser() {
		return scUserDao.findAll();
	}

	/**
	 * 获取交易所信息
	 * 
	 * @return
	 */
	public List<ScExchange> getScExchange() {
		return scExchangeDao.findAll();
	}
	
	/**
	 * 获取指标计算结果
	 * @return
	 */
	public List<ScIndexResult> getScIndexResult(){
		List<ScIndexResult> stocklist = scIndexResultDao.findByStock();
		List<ScIndexResult> futureslist = scIndexResultDao.findByFutures();
		List<ScIndexResult> list=Lists.newArrayList();
		list.addAll(stocklist);
		list.addAll(futureslist);
		return list;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 加载板块的信息
	 * 
	 * @return
	 */
	public void loadScPlate() {
		List<ScPlate> sourcelist = scPlateDao.findAll();
		for (int i = 0; i < sourcelist.size(); i++) {
			List<ScPlateProduct> list = scPlateProductDao.findById(sourcelist.get(i).getId());
			if (list.size() > 0) {
				sourcelist.get(i).setScPlateProductList(list);
			}
		}
		handlerList(sourcelist);
		List<ScPlate> dataList = Lists.newArrayList();
		for (int i = 0; i < sourcelist.size(); i++) {
			ScPlate s = sourcelist.get(i);
			if (ScPlate.getRootId().equals(s.getParentId())) {
				dataList.add(s);
			}
		}
		// scPlateMap.put("data", dataList);
	}

	/**
	 * 递归获取子菜单
	 * 
	 * @param sourcelist
	 */
	public void handlerList(List<ScPlate> sourcelist) {
		for (int i = 0; i < sourcelist.size(); i++) {
			List<ScPlate> sclist = Lists.newArrayList();
			for (int j = 0; j < sourcelist.size(); j++) {
				if (sourcelist.get(i).getId().equals(sourcelist.get(j).getParentId())) {
					sclist.add(sourcelist.get(j));
				}
			}
			sourcelist.get(i).setChildren(sclist);
			if (sclist.size() > 0) {
				handlerList(sclist);
			}
		}
	}
}
