package com.sc.td.business.service.scexchange;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.entity.scexchange.ScExchange;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScExchangeService extends BaseService {

	@Autowired
	private RespInfoConfig respInfoConfig;

	/**
	 * 从内存中获取交易所信息
	 * 
	 * @param jsonText
	 * @return
	 */
	public String getExchange(String jsonText) {
		getPage(jsonText);
		String dateTime = null;
		@SuppressWarnings("unchecked")
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap.size() > 0) {
			dateTime = jsonMap.get("dateTime");
		}
		ConcurrentHashMap<String, List<ScExchange>> scExchangeMap = BusinessTask.scExchangeMap;
		List<ScExchange> scExchangeList = scExchangeMap.get("scExchange");
		if (scExchangeList != null && scExchangeList.size() > 0) {
			List<ScExchange> dataList = Lists.newArrayList();
			// 当传的时间不为空时
			if (StringUtils.isNotBlank(dateTime)) {
				for (ScExchange sc : scExchangeList) {
					if (Double.parseDouble(dateTime.replace("-", "").replace(" ", "").replace(":", "")) < Double
							.parseDouble(sc.getUpdateDate().replace("-", "").replace(" ", "").replace(":", ""))) {
						dataList.add(sc);
					}
				}
			} else {
				dataList = scExchangeList;
			}

			if (dataList != null && dataList.size() > 0) {
				int listSize = dataList.size();
				int maxIndex = index + size;
				if (maxIndex > listSize) {
					maxIndex = listSize;
				}
				List<ScExchange> list = Lists.newArrayList();
				for (int i = index; i < maxIndex; i++) {
					list.add(dataList.get(i));
				}
				ConcurrentHashMap<String, List<ScExchange>> respMap = new ConcurrentHashMap<String, List<ScExchange>>();
				respMap.put("scExchange", list);
				return JacksonUtil.objToJson(respMap);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}

}
