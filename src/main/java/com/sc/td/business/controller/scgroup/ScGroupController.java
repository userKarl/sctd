package com.sc.td.business.controller.scgroup;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.sc.td.business.base.BaseController;
import com.sc.td.business.service.scgroup.ScGroupService;

@Controller
@RequestMapping("/operate/scgroup")
public class ScGroupController extends BaseController {
	
	@Autowired
	private ScGroupService scGroupService;

	/**
	 * 获取PK风云榜信息
	 * 
	 * @param jsonText
	 * @return
	 * @throws UnknownHostException
	 */
	@RequestMapping(value = "/getrank", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getrank(String jsonText) throws UnknownHostException {
		return scGroupService.getrank(jsonText);
	}

	/**
	 * 创建战队
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/creategroup_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String creategroup_ckt(String jsonText) {
		return scGroupService.creategroup_ckt(jsonText);
	}

	/**
	 * 修改战队信息
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/updategroup_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String updategroup_ckt(String jsonText) {
		return scGroupService.updategroup_ckt(jsonText);
	}

	/**
	 * 开启或关闭PK
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/setAllowPk_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String setAllowPk_ckt(String jsonText) {
		return scGroupService.setAllowPk_ckt(jsonText);
	}

	/**
	 * 根据userID获取战队信息
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getGroupInfoByUserId_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getGroupInfoByUserId_ckt(String jsonText) {
		return scGroupService.getGroupInfoByUserId_ckt(jsonText);
	}

	/**
	 * 根据groupId获取战队信息
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getGroupInfoByGroupId", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getGroupInfoByGroupId(String jsonText) {
		return scGroupService.getGroupInfoByGroupId(jsonText);
	}
}
