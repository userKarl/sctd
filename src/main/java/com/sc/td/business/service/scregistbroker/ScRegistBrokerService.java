package com.sc.td.business.service.scregistbroker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.scregistbroker.ScRegistBrokerDao;
import com.sc.td.business.entity.scregistbroker.ScRegistBroker;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScRegistBrokerService extends BaseService {

	@Autowired
	private RespInfoConfig respInfoConfig;

	@Autowired
	private ScRegistBrokerDao scRegistBrokerDao;

	/**
	 * 获取经纪商信息
	 * 
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getScRegistBrokerInfo(String jsonText) {
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		String brokerType = jsonMap.get("brokerType");
		if (StringUtils.isNotBlank(brokerType)) {
			List<ScRegistBroker> list = scRegistBrokerDao.findByBrokerType(brokerType);
			if (list != null && list.size() > 0) {
				for (ScRegistBroker sc : list) {
					sc.setBrokerImg(getServerAddress() + sc.getBrokerImg());
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("result", true);
				map.put("data", list);
				return JacksonUtil.objToJson(map);
			} else {
				return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
			}
		} else {
			return CreateJson.createTextJson(respInfoConfig.getParamsNull(), false);
		}
	}
}
