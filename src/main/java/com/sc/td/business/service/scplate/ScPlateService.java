package com.sc.td.business.service.scplate;

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
import com.sc.td.business.dao.scplate.ScPlateViewDao;
import com.sc.td.business.entity.scplate.ScPlateView;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScPlateService extends BaseService {

	@Autowired
	private ScPlateViewDao scPlateViewDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	/**
	 * 获取板块
	 * 
	 * @return
	 */
	public Page<ScPlateView> getPlateView(String jsonText) {
		getPage(jsonText);
		Sort sort = new Sort(Direction.ASC, "plateGroupId");
		Pageable pageable = new PageRequest(index, size, sort);
		return scPlateViewDao.findAll(pageable);
	}

	/**
	 * 从内存中获取板块信息
	 * 
	 * @param jsonText
	 * @return
	 */
	public String getPlateInfo(String jsonText) {
		getPage(jsonText);
		String dateTime = null;
		@SuppressWarnings("unchecked")
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null && jsonMap.size() > 0) {
			dateTime = jsonMap.get("dateTime");
		}
		ConcurrentHashMap<String, List<ScPlateView>> scPlateViewMap = BusinessTask.scPlateViewMap;
		List<ScPlateView> scPlateViewList = scPlateViewMap.get("scPlate");
		if (scPlateViewList != null && scPlateViewList.size() > 0) {
			List<ScPlateView> dataList = Lists.newArrayList();
			// 当传入的时间不为空时
			if (StringUtils.isNotBlank(dateTime)) {
				for (ScPlateView sd : scPlateViewList) {
					if (Double.parseDouble(dateTime.replace("-", "").replace(" ", "").replace(":", "")) < Double
							.parseDouble(sd.getUpdateDate().replace("-", "").replace(" ", "").replace(":", ""))) {
						dataList.add(sd);
					}
				}
			} else {
				dataList = scPlateViewList;
			}
			if (dataList != null && dataList.size() > 0) {
				int listSize = dataList.size();
				int maxIndex = index + size;
				if (maxIndex > listSize) {
					maxIndex = listSize;
				}
				List<ScPlateView> list = Lists.newArrayList();
				for (int i = index; i < maxIndex; i++) {
					list.add(dataList.get(i));
				}
				ConcurrentHashMap<String, List<ScPlateView>> respMap = new ConcurrentHashMap<String, List<ScPlateView>>();
				respMap.put("scPlate", list);
				return JacksonUtil.objToJson(respMap);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}
}
