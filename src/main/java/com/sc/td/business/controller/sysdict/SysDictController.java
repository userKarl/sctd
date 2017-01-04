package com.sc.td.business.controller.sysdict;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.service.sysdict.SysDictService;

@Controller
@RequestMapping("/operate/sysdict")
public class SysDictController {

	@Autowired
	private SysDictService sysDictService;

	/**
	 * 从内存中获取数据字典
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getSysDict", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getSysDict(String jsonText) {

		return sysDictService.getSysDict(jsonText);
	}
}
