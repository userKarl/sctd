package com.sc.td.business.controller.scstock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sc.td.business.base.BaseController;
import com.sc.td.business.service.scstock.ScStockService;
import com.sc.td.common.utils.json.JacksonUtil;

@Controller
@RequestMapping("/operate/scstock")
public class ScStockController extends BaseController {

	@Autowired
	private ScStockService scStockService;

	/**
	 * 从数据库获取股票列表
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getScStockInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getStockInfo(String jsonText) {

		return JacksonUtil.objToJson(scStockService.getStockInfo(jsonText));
	}

	/**
	 * 从内存中获取股票数据
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getScStock", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getScStock(String jsonText) {

		return scStockService.getScStock(jsonText);
	}
}
