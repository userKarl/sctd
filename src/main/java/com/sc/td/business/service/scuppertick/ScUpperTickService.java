package com.sc.td.business.service.scuppertick;

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
import com.sc.td.business.dao.scuppertick.ScUpperTickDao;
import com.sc.td.business.entity.scuppertick.ScUpperTick;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScUpperTickService extends BaseService {

	@Autowired
	private ScUpperTickDao scUpperTickDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	/**
	 * 获取跳点信息
	 * 
	 * @param jsonText
	 * @return
	 */
	public Page<ScUpperTick> getScUpperTickInfo(String jsonText) {
		getPage(jsonText);
		Sort sort = new Sort(Direction.ASC, "upperTickCode");
		Pageable pageable = new PageRequest(index, size, sort);
		return scUpperTickDao.findAll(pageable);
	}

	/**
	 * 从内存中获取跳点信息
	 * 
	 * @param jsonText
	 * @return
	 */
	public String getUpperTickInfo(String jsonText) {
		getPage(jsonText);
		String dateTime = null;
		@SuppressWarnings("unchecked")
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null && jsonMap.size() > 0) {
			dateTime = jsonMap.get("dateTime");
		}
		ConcurrentHashMap<String, List<ScUpperTick>> scUpperTickMap = BusinessTask.scUpperTickMap;
		List<ScUpperTick> scUpperTickList = scUpperTickMap.get("scUpperTick");
		if (scUpperTickList != null && scUpperTickList.size() > 0) {
			List<ScUpperTick> dataList = Lists.newArrayList();
			// 当传入的时间不为空时
			if (StringUtils.isNotBlank(dateTime)) {
				for (ScUpperTick sd : scUpperTickList) {
					if (Double.parseDouble(dateTime.replace("-", "").replace(" ", "").replace(":", "")) < Double
							.parseDouble(sd.getUpdateDate().replace("-", "").replace(" ", "").replace(":", ""))) {
						dataList.add(sd);
					}
				}
			} else {
				dataList = scUpperTickList;
			}
			if (dataList != null && dataList.size() > 0) {
				int listSize = dataList.size();
				int maxIndex = index + size;
				if (maxIndex > listSize) {
					maxIndex = listSize;
				}
				List<ScUpperTick> list = Lists.newArrayList();
				for (int i = index; i < maxIndex; i++) {
					list.add(dataList.get(i));
				}
				ConcurrentHashMap<String, List<ScUpperTick>> respMap = new ConcurrentHashMap<String, List<ScUpperTick>>();
				respMap.put("scUpperTick", list);
				return JacksonUtil.objToJson(respMap);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}
}
