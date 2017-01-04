package com.sc.td.business.service.scindexresult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.scindexresult.ScIndexResultDao;
import com.sc.td.business.entity.scindexresult.ScIndexResult;
import com.sc.td.business.entity.sysdict.SysDict;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScIndexResultService extends BaseService{

	@Autowired
	private ScIndexResultDao scIndexResultDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	/**
	 * 根据指标ID获取数据
	 * 
	 * @param jsonText
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getIndexResult(String jsonText) {
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap!=null&&jsonMap.size() > 0) {
			String funcIndexId = jsonMap.get("indexId");
			String type = jsonMap.get("type");
			List<ScIndexResult> stocklist = scIndexResultDao.findByFuncIndexIdStock(funcIndexId,type);
			List<ScIndexResult> futureslist = scIndexResultDao.findByFuncIndexIdFutures(funcIndexId,type);
			List<ScIndexResult> list=Lists.newArrayList();
			list.addAll(stocklist);
			list.addAll(futureslist);
			if (list!=null&&list.size() > 0) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("result", true);
				map.put("size", list.size());
				map.put("data", list);
				return JacksonUtil.objToJson(map);
			} else {
				return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getSmsException(), false);
	}
	
	/**
	 * 从内存中获取指标数据
	 * 
	 * @return
	 */
	public String getScIndexResult(String jsonText) {
		getPage(jsonText);
		String dateTime = null;
		String funcIndexId = null;
		@SuppressWarnings("unchecked")
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null&&jsonMap.size()>0) {
//			dateTime = jsonMap.get("dateTime");
			funcIndexId = jsonMap.get("indexId");
			String type = jsonMap.get("type");
			ConcurrentHashMap<String, List<ScIndexResult>> scIndexResultMap = BusinessTask.scIndexResultMap;
			List<ScIndexResult> scIndexResultList = scIndexResultMap.get("scIndexResult");
			if (scIndexResultList!=null&&scIndexResultList.size() > 0) {
				// 筛选指标数据
				List<ScIndexResult> requireList = Lists.newArrayList();
				if (StringUtils.isNotBlank(funcIndexId)) {
					for (ScIndexResult sc : scIndexResultList) {
						if (StringUtils.isNotBlank(sc.getFuncIndexId()) && sc.getFuncIndexId().equals(funcIndexId)) {
							if(StringUtils.isNotBlank(type)){
								if(type.equals("market_type") && getStockType(sc.getCommodityType()).equals("futures") ){
									requireList.add(sc);
								}else{
									ConcurrentHashMap<String, List<SysDict>> sysDictMap = BusinessTask.sysDictMap;
									List<SysDict> sysDictList=sysDictMap.get("sysDict");
									if(sysDictList.size()>0){
										for(SysDict sysDict:sysDictList){
											if(sysDict.getType().equals(type) && sysDict.getValue().equals(sc.getExchangeNo())
													&& !getStockType(sc.getCommodityType()).equals("futures")){
												requireList.add(sc);
												break;
											}
										}
									}
								}
							}else{
								requireList.add(sc);
							}
							
						}
					}
				}
				List<ScIndexResult> dataList = Lists.newArrayList();
//				if (requireList.size() > 0) {
//					// 当传的时间不为空时
//					if (StringUtils.isNotBlank(dateTime)) {
//						for (ScIndexResult sc : requireList) {
//							if (Double.parseDouble(dateTime.replace("-", "").replace(" ", "").replace(":", "")) < Double
//									.parseDouble(
//											sc.getUpdateDate().replace("-", "").replace(" ", "").replace(":", ""))) {
//								dataList.add(sc);
//							}
//						}
//					} else {
//						dataList = requireList;
//					}
//				}
				dataList = requireList;
				if (dataList!=null && dataList.size() > 0) {
					int listSize = dataList.size();
					int maxIndex = index  + size;
					if (maxIndex > listSize) {
						maxIndex = listSize;
					}
					List<ScIndexResult> list = Lists.newArrayList();
					for (int i = index ; i < maxIndex; i++) {
						list.add(dataList.get(i));
					}
					Map<String, Object> respMap = new HashMap<String,Object>();
					respMap.put("data", list);
					respMap.put("size", listSize);
					respMap.put("result", true);
					return JacksonUtil.objToJson(respMap);
				}
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}
}
