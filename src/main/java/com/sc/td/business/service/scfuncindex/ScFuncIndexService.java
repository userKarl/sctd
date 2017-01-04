package com.sc.td.business.service.scfuncindex;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.scfuncindex.ScFuncIndexDao;
import com.sc.td.business.entity.scfuncindex.ScFuncIndex;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.DictConfig;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScFuncIndexService extends BaseService {

	@Autowired
	private ScFuncIndexDao scFuncIndexDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	@Autowired
	private DictConfig dictConfig;

	/**
	 * 获取指标信息(只获取前台要显示的数据is_show)
	 * 
	 * @return
	 */
	public String getIdxInfo() {
		List<ScFuncIndex> list = scFuncIndexDao
				.findByIsShow(StringUtils.isBlank(dictConfig.getIs_show()) ? "0" : dictConfig.getIs_show());
		if (list != null && list.size() > 0) {
			// 处理返回信息中指标的图标信息
			for (ScFuncIndex sc : list) {
				sc.setIconPath(getServerAddress() + sc.getIconPath());
			}
			Map<String, Object> respMap = Maps.newHashMap();
			respMap.put("result", true);
			respMap.put("data", list);
			return JacksonUtil.objToJson(respMap);
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}
}
