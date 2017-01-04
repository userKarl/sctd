package com.sc.td.business.service.sccurrency;

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

import com.google.common.collect.Lists;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.sccurrency.ScCurrencyDao;
import com.sc.td.business.entity.sccurrency.ScCurrency;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScCurrencyService extends BaseService {

	@Autowired
	private ScCurrencyDao scCurrencyDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	/**
	 * 获取汇率信息
	 * 
	 * @param jsonText
	 * @return
	 */
	public Page<ScCurrency> getScCurrencyInfo(String jsonText) {
		getPage(jsonText);
		Sort sort = new Sort(Direction.ASC, "currency");
		Pageable pageable = new PageRequest(index, size, sort);
		return scCurrencyDao.findAll(pageable);
	}

	/**
	 * 从内存中获取汇率信息
	 * 
	 * @param jsonText
	 * @return
	 */
	public String getCurrencyInfo(String jsonText) {
		getPage(jsonText);
		String dateTime = null;
		@SuppressWarnings("unchecked")
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null) {
			dateTime = jsonMap.get("dateTime");
		}
		ConcurrentHashMap<String, List<ScCurrency>> scCurrencyMap = BusinessTask.scCurrencyMap;
		List<ScCurrency> scCurrencyList = scCurrencyMap.get("scCurrency");
		if (scCurrencyList != null && scCurrencyList.size() > 0) {
			List<ScCurrency> dataList = Lists.newArrayList();
			// 当传入的时间不为空时
			if (StringUtils.isNotBlank(dateTime)) {
				for (ScCurrency sd : scCurrencyList) {
					if (Double.parseDouble(dateTime.replace("-", "").replace(" ", "").replace(":", "")) < Double
							.parseDouble(sd.getUpdateDate().replace("-", "").replace(" ", "").replace(":", ""))) {
						dataList.add(sd);
					}
				}
			} else {
				dataList = scCurrencyList;
			}
			if (dataList != null && dataList.size() > 0) {
				int listSize = dataList.size();
				int maxIndex = index * size + size;
				if (maxIndex > listSize) {
					maxIndex = listSize;
				}
				List<ScCurrency> list = Lists.newArrayList();
				for (int i = index * size; i < maxIndex; i++) {
					list.add(dataList.get(i));
				}
				ConcurrentHashMap<String, List<ScCurrency>> respMap = new ConcurrentHashMap<String, List<ScCurrency>>();
				respMap.put("scCurrency", list);
				return JacksonUtil.objToJson(respMap);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}
}
