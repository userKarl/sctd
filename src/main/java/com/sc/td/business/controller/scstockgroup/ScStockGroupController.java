package com.sc.td.business.controller.scstockgroup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.base.BaseController;
import com.sc.td.business.service.scstockgroup.ScStockGroupService;

@Controller
@RequestMapping("/operate/scstockgroup")
public class ScStockGroupController extends BaseController {

	@Autowired
	private ScStockGroupService scStockGroupService;

	/**
	 * 删除
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/delete_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String delete_ckt(String jsonText) {
		return scStockGroupService.delete_ckt(jsonText);
	}
}
