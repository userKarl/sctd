package com.sc.td.business.controller.screcommend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.base.BaseController;
import com.sc.td.business.service.screcommend.ScRecommendService;

@Controller
@RequestMapping("/operate/screcommend")
public class ScRecommendController extends BaseController {

	@Autowired
	private ScRecommendService scRecommendService;

	/**
	 * 点击查看更多调用的接口
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getInfo_ckt", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getInfo_ckt(String jsonText) {
		return scRecommendService.getInfo(jsonText, true);
	}

//	/**
//	 * 进入页面时调用
//	 * 
//	 * @param jsonText
//	 * @return
//	 */
//	@RequestMapping(value = "/getInfo", method = { RequestMethod.POST,
//			RequestMethod.GET }, produces = "application/json; charset=utf-8")
//	@ResponseBody
//	public String getInfo(String jsonText) {
//		return scRecommendService.getInfo(jsonText, false);
//	}

	/**
	 * 进入页面时调用
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getInfo", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getInfo(String jsonText) {
		return scRecommendService.getInfo_new(jsonText);
	}
}
