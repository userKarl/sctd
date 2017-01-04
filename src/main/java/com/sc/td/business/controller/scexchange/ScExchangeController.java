package com.sc.td.business.controller.scexchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.service.scexchange.ScExchangeService;

@Controller
@RequestMapping("/operate/scexchange")
public class ScExchangeController {

	@Autowired
	private ScExchangeService scExchangeService;

	/**
	 * 从内存中获取交易所数据
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getExchange", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getExchange(String jsonText) {

		return scExchangeService.getExchange(jsonText);
	}
}
