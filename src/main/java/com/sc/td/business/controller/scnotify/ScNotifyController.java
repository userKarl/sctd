package com.sc.td.business.controller.scnotify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sc.td.business.service.scnotify.ScNotifyService;

@Controller
@RequestMapping("/operate/scnotify")
public class ScNotifyController {

	@Autowired
	private ScNotifyService scNotifyService;
	
	/**
	 * 根据userId获取消息记录
	 */
	@RequestMapping(value = "/getNotifyRecordInfo_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getNotifyRecordInfo(String jsonText) {
		return scNotifyService.getNotifyRecordInfo(jsonText);
	}
	
	/**
	 * 根据userID和type获取消息的具体信息
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getNotifyInfo_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getNotifyInfo(String jsonText) {
		return scNotifyService.getNotifyInfo(jsonText);
	}
	
}
