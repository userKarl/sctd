package com.sc.td.business.base;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sc.td.common.utils.UnicodeUtils;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.frame.RespInfoConfig;

@Controller
public class BaseController {

	protected Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private RespInfoConfig respInfoConfig;
	
	/**
	 * 404页面
	 * @return
	 */
	@RequestMapping(value = "/error/page404", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	public String page404(){
		return "error/page404";
	}

	/**
	 * 返回消息
	 * 
	 * @param resp
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/baseResp", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String baseResp(String resp) throws UnsupportedEncodingException {
		return UnicodeUtils.deUnicode(resp);
	}

	/**
	 * 異常
	 * 
	 * @return
	 */
	@RequestMapping(value = "/exception", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String exception() {
		return CreateJson.createTextJson(respInfoConfig.getSysExeption(), false);
	}
}
