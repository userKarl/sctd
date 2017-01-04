package com.sc.td.business.controller.scplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sc.td.business.base.BaseController;
import com.sc.td.business.service.scplate.ScPlateService;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.utils.json.JacksonUtil;

@Controller
@RequestMapping("/operate/scplate")
public class ScPlateController extends BaseController {

	@Autowired
	private ScPlateService scPlateService;

	/**
	 * 获取板块列表
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getScPlateView", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getScPlateView() {
		return JacksonUtil.objToJson(BusinessTask.scPlateViewMap);
	}

	/**
	 * 根据条件检索
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getPlateInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getPlateInfo(String jsonText) {
		return scPlateService.getPlateInfo(jsonText);
	}
}
