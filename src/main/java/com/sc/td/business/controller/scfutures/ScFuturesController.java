package com.sc.td.business.controller.scfutures;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sc.td.business.service.scfutures.ScFuturesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/operate/scfutures")
public class ScFuturesController {

	@Autowired
	private ScFuturesService scFuturesService;

	/**
	 * 获取期货列表
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getScFuturesInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getScFuturesInfo(String jsonText) {
		return scFuturesService.getScFuturesInfo(jsonText);
	}

	/**
	 * 从内存中获取期货数据
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getScFutures", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getScFutures(String jsonText) {

		return scFuturesService.getScFutures(jsonText);
	}
}
