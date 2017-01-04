package com.sc.td.business.controller.scpkresult;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.service.scpkresult.ScHisPkResultService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/operate/schispkresult")
public class ScHisPkResultController {

	@Autowired
	private ScHisPkResultService scHisPkResultService;

	/**
	 * 获取发起方或接收方的数据列表
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getlist_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getlist_ckt(String jsonText) {
		return scHisPkResultService.getlist_ckt(jsonText);
	}

	/**
	 * 获取历史战绩
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getHisPkResultInfo_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getHisPkResultInfo_ckt(String jsonText) {

		return scHisPkResultService.getHisPkResultInfo_ckt(jsonText);
	}
}
