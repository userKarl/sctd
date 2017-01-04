package com.sc.td.business.service.scmarket;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sc.td.business.entity.scmarket.MarketInfo;
import com.sc.td.common.config.Global;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.common.utils.redis.RedisService;
import com.sc.td.frame.RespInfoConfig;

@Service
public class MarketService {

	@Autowired
	private RedisService redisService;

	@Autowired
	private RespInfoConfig respInfoConfig;

	/**
	 * 获取行情信息
	 * 
	 * @param jsonText
	 * @return
	 */
	public String getMarketInfo(String jsonText) {
		StockInfo stockInfo = JacksonUtil.jsonToObj(jsonText, StockInfo.class);
		List<MarketInfo> marketInfoList = Lists.newArrayList();
		String redisPriceKey = Global.redis_price_key;
		if (stockInfo != null) {
			List<StockInfo> stockInfoList = stockInfo.getStockInfoList();
			if (stockInfoList != null && stockInfoList.size() > 0) {
				for (StockInfo si : stockInfoList) {
					// 首先从redis中根据key获取数据
					List<String> dataList = redisService.hmget(redisPriceKey,
							si.getCommodityType() + "," + si.getStockNo());
					// 如果在redis中有数据
					if (dataList.get(0) != null) {
						String data = dataList.get(0).replace("<", "").replace(">", "").replace("k__BackingField", "");
						// 将该json数据转化为MarketInfo类
						MarketInfo mi = JacksonUtil.jsonToObj(data, MarketInfo.class);
						if (mi != null) {
							marketInfoList.add(mi);
						}
					}
				}
				Map<String, Object> respMap = Maps.newHashMap();
				respMap.put("result", true);
				respMap.put("data", marketInfoList);
				return JacksonUtil.objToJson(respMap);
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}
}
