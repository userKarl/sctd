package com.sc.td.business.controller.scmarket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.service.scmarket.MarketService;

@Controller
@RequestMapping("/operate/scmarket")
public class MarketController {
	
	
	@Autowired
	private MarketService marketService;

	/**
	 * 获取行情信息
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getMarketInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getIndexResult(String jsonText) {
		return marketService.getMarketInfo(jsonText);
	}
}
