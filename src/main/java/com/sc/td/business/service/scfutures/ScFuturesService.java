package com.sc.td.business.service.scfutures;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.scfutures.ScFuturesDao;
import com.sc.td.business.entity.scfutures.ScFutures;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.common.utils.page.PageInfo;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScFuturesService extends BaseService {

	@Autowired
	private ScFuturesDao scFuturesDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	/**
	 * 获取期货信息
	 * 
	 * @param jsonText
	 * @return
	 */
	public String getScFuturesInfo(String jsonText) {
		String dateTime = "";
		// 解析json数据
		@SuppressWarnings("unchecked")
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null) {
			dateTime = jsonMap.get("dateTime");
			String json_pageno = jsonMap.get("pageno");
			String json_size = jsonMap.get("size");
			if (StringUtils.isNotBlank(json_pageno) && StringUtils.isNotBlank(json_size)) {
				index = PageInfo.getIndex(PageInfo.getPageNo(json_pageno), PageInfo.getSize(json_size));
				size = PageInfo.getSize(json_size);
				List<ScFutures> list = scFuturesDao.findByRegDateGreaterThan(dateTime, index * size, size);
				Map<String, List<ScFutures>> map = Maps.newHashMap();
				map.put("scFutures", list);
				return JacksonUtil.objToJson(map);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}

	/**
	 * 从内存中获取期货信息
	 * 
	 * @return
	 */
	public String getScFutures(String jsonText) {
		getPage(jsonText);
		String dateTime = null;
		String dataType = null;
		@SuppressWarnings("unchecked")
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null) {
			dateTime = jsonMap.get("dateTime");
			dataType = jsonMap.get("dataType");
			ConcurrentHashMap<String, List<ScFutures>> scFuturesMap = BusinessTask.scFuturesMap;
			List<ScFutures> scFuturesList = scFuturesMap.get("scFutures");

			if (scFuturesList!=null && scFuturesList.size() > 0) {
				// 筛选期货类型
				List<ScFutures> requireList = Lists.newArrayList();
				if (StringUtils.isNotBlank(dataType)) {
					Iterable<String> split = Splitter.on(",").trimResults().split(dataType);
					for (ScFutures sc : scFuturesList) {
						for (String str : split) {
							if (StringUtils.isNotBlank(sc.getFuturesType()) && sc.getFuturesType().equals(str)) {
								requireList.add(sc);
							}
						}
					}
				}
				List<ScFutures> dataList = Lists.newArrayList();
				if (requireList.size() > 0) {
					// 当传的时间不为空时
					if (StringUtils.isNotBlank(dateTime)) {
						for (ScFutures sc : requireList) {
							if (Double.parseDouble(dateTime.replace("-", "").replace(" ", "").replace(":", "")) < Double
									.parseDouble(
											sc.getUpdateDate().replace("-", "").replace(" ", "").replace(":", ""))) {
								dataList.add(sc);
							}
						}
					} else {
						dataList = requireList;
					}
				}
				if (dataList.size() > 0) {
					int listSize = dataList.size();
					int maxIndex = index  + size;
					if (maxIndex > listSize) {
						maxIndex = listSize;
					}
					List<ScFutures> list = Lists.newArrayList();
					for (int i = index ; i < maxIndex; i++) {
						list.add(dataList.get(i));
					}
					Map<String, List<ScFutures>> respMap = new HashMap<String, List<ScFutures>>();
					respMap.put("scFutures", list);
					return JacksonUtil.objToJson(respMap);
				}
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}
}
