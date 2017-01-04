package com.sc.td.business.service.scprice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sc.td.business.entity.scprice.ScPrice;
import com.sc.td.business.service.screcommend.ScRecommendService;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScPriceService {

	@Autowired
	private ScRecommendService scRecommendService;

	@Autowired
	private RespInfoConfig respInfoConfig;

	/**
	 * 获取最新价格
	 * 
	 * @param jsonText
	 * @return
	 */
	public String getNowPrice(String jsonText) {
		ScPrice scPrice = JacksonUtil.jsonToObj(jsonText, ScPrice.class);
		if (scPrice != null) {
			List<ScPrice> scPriceList = scPrice.getScPriceList();
			for (ScPrice sc : scPriceList) {
				sc.setPrice(scRecommendService.getNowPrice(sc.getCommodityType(), sc.getStockNo()));
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("data", scPriceList);
			map.put("result", true);
			return JacksonUtil.objToJson(map);
		}
		return CreateJson.createTextJson(respInfoConfig.getParamsNull(), false);
	}
}
