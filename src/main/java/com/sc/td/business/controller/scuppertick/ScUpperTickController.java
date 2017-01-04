package com.sc.td.business.controller.scuppertick;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.service.scuppertick.ScUpperTickService;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.utils.json.JacksonUtil;

@Controller
@RequestMapping("/operate/scuppertick")
public class ScUpperTickController {

	@Autowired
	private ScUpperTickService scUpperTickService;

	/**
	 * 获取跳点信息
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getScUpperTickInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getScUpperTickInfo() {

		return JacksonUtil.objToJson(BusinessTask.scUpperTickMap);
	}

	/**
	 * 加入时间检索
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getUpperTickInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getUpperTickInfo(String jsonText) {

		return scUpperTickService.getUpperTickInfo(jsonText);
	}
}
