package com.sc.td.business.service.sysdict;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.entity.sysdict.SysDict;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.RespInfoConfig;

@Service
public class SysDictService extends BaseService {

	@Autowired
	private RespInfoConfig respInfoConfig;

	/**
	 * 从内存中获取数据字典
	 * 
	 * @param jsonText
	 * @return
	 */
	public String getSysDict(String jsonText) {
		getPage(jsonText);
		String dateTime = null;
		@SuppressWarnings("unchecked")
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null) {
			dateTime = jsonMap.get("dateTime");
		}
		ConcurrentHashMap<String, List<SysDict>> sysDictMap = BusinessTask.sysDictMap;
		List<SysDict> sysDictList = sysDictMap.get("sysDict");
		if (sysDictList!=null && sysDictList.size() > 0) {
			List<SysDict> dataList = Lists.newArrayList();
			// 当传入的时间不为空时
			if (StringUtils.isNotBlank(dateTime)) {
				for (SysDict sd : sysDictList) {
					if (Double.parseDouble(dateTime.replace("-", "").replace(" ", "").replace(":", "")) < Double
							.parseDouble(sd.getUpdateDate().replace("-", "").replace(" ", "").replace(":", ""))) {
						dataList.add(sd);
					}
				}
			} else {
				dataList = sysDictList;
			}
			if (dataList.size() > 0) {
				int listSize = dataList.size();
				int maxIndex = index  + size;
				if (maxIndex > listSize) {
					maxIndex = listSize;
				}
				List<SysDict> list = Lists.newArrayList();
				for (int i = index ; i < maxIndex; i++) {
					list.add(dataList.get(i));
				}
				ConcurrentHashMap<String, List<SysDict>> respMap = new ConcurrentHashMap<String, List<SysDict>>();
				respMap.put("sysDict", list);
				return JacksonUtil.objToJson(respMap);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}
}
