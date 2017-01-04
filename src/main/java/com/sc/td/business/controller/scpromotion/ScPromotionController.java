package com.sc.td.business.controller.scpromotion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.service.scpromotion.ScPromotionService;

@Controller
@RequestMapping("/operate/scpromotion")
public class ScPromotionController {

	@Autowired
	private ScPromotionService scPromotionService;
	
	/**
	 * 获取需要显示的数据
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getInfo", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getInfo() {
		return scPromotionService.getInfo();
	}
}
