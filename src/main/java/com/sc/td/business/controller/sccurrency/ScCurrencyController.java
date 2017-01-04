package com.sc.td.business.controller.sccurrency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.service.sccurrency.ScCurrencyService;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.utils.json.JacksonUtil;

@Controller
@RequestMapping("/operate/sccurrency")
public class ScCurrencyController {

	@Autowired
	private ScCurrencyService scCurrencyService;

	/**
	 * 获取汇率列表
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getScCurrencyInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getScCurrencyInfo() {

		return JacksonUtil.objToJson(BusinessTask.scCurrencyMap);
	}

	/**
	 * 从内存中获取汇率
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getCurrencyInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getCurrencyInfo(String jsonText) {

		return scCurrencyService.getCurrencyInfo(jsonText);
	}
}
