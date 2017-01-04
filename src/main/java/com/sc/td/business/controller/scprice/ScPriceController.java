package com.sc.td.business.controller.scprice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.service.scprice.ScPriceService;

@Controller
@RequestMapping("/operate/scprice")
public class ScPriceController {

	@Autowired
	private ScPriceService scPriceService;

	/**
	 * 获取最新价格
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getNowPrice", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getNowPrice(String jsonText) {
		return scPriceService.getNowPrice(jsonText);
	}
}
