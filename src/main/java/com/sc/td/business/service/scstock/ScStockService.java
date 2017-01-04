package com.sc.td.business.service.scstock;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.scstock.ScStockDao;
import com.sc.td.business.entity.scstock.ScStock;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScStockService extends BaseService {

	@Autowired
	private ScStockDao scStockDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	/**
	 * 从数据库获取股票信息
	 * 
	 * @param jsonText
	 * @return
	 */
	public Page<ScStock> getStockInfo(String jsonText) {
		getPage(jsonText);
		Sort sort = new Sort(Direction.ASC, "updateDate");
		Pageable pageable = new PageRequest(index, size, sort);
		String dateTime = "";
		// 解析json数据
		@SuppressWarnings("unchecked")
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null) {
			dateTime = jsonMap.get("dateTime");
		}
		return scStockDao.findByUpdateDateGreaterThan(dateTime, pageable);
	}

	/**
	 * 从内存中获取股票信息
	 * 
	 * @return
	 */
	public String getScStock(String jsonText) {
		getPage(jsonText);
		String dateTime = null;
		String dataType = null;
		@SuppressWarnings("unchecked")
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null && jsonMap.size() > 0) {
			dateTime = jsonMap.get("dateTime");
			dataType = jsonMap.get("dataType");
		}
		ConcurrentHashMap<String, List<ScStock>> scStockMap = BusinessTask.scStockMap;
		List<ScStock> scStockList = scStockMap.get("scStock");

		if (scStockList != null && scStockList.size() > 0) {
			// 筛选股票类型
			List<ScStock> requireList = Lists.newArrayList();
			if (StringUtils.isNotBlank(dataType)) {
				Iterable<String> split = Splitter.on(",").trimResults().split(dataType);
				for (ScStock sc : scStockList) {
					for (String str : split) {
						if (StringUtils.isNotBlank(sc.getStockType()) && sc.getStockType().equals(str)) {
							requireList.add(sc);
						}
					}
				}
			}
			List<ScStock> dataList = Lists.newArrayList();
			if (requireList != null && requireList.size() > 0) {
				// 当传的时间不为空时
				if (StringUtils.isNotBlank(dateTime)) {
					for (ScStock sc : requireList) {
						if (Double.parseDouble(dateTime.replace("-", "").replace(" ", "").replace(":", "")) < Double
								.parseDouble(sc.getUpdateDate().replace("-", "").replace(" ", "").replace(":", ""))) {
							dataList.add(sc);
						}
					}
				} else {
					dataList = requireList;
				}
			}

			if (dataList != null && dataList.size() > 0) {
				int listSize = dataList.size();
				int maxIndex = index + size;
				if (maxIndex > listSize) {
					maxIndex = listSize;
				}
				List<ScStock> list = Lists.newArrayList();
				for (int i = index; i < maxIndex; i++) {
					list.add(dataList.get(i));
				}
				ConcurrentHashMap<String, List<ScStock>> respMap = new ConcurrentHashMap<String, List<ScStock>>();
				respMap.put("scStock", list);
				return JacksonUtil.objToJson(respMap);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}
}
