package com.sc.td.business.controller.scright;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sc.td.business.base.BaseController;
import com.sc.td.business.service.scright.ScRightService;

@Controller
@RequestMapping("/operate/scright")
public class ScRightController extends BaseController {

	@Autowired
	private ScRightService scRightService;

	@RequestMapping(value = "/becomevip_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String becomevip_ckt(String jsonText) throws UnsupportedEncodingException {
		return scRightService.becomevip_ckt(jsonText);
	}
}
