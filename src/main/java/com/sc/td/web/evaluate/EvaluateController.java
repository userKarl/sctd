package com.sc.td.web.evaluate;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.collect.Maps;
import com.sc.td.common.utils.GetHttpMsg;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.json.JacksonUtil;

@Controller
@RequestMapping("/evaluate")
public class EvaluateController {

	@RequestMapping(value = "", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView intro(String mobile) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/evaluate/intro");
		mav.addObject("mobile",mobile);
		return mav;
	}
	
	@RequestMapping(value = "/index", method =RequestMethod.GET)
	public ModelAndView index(String mobile) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/evaluate/index");
		mav.addObject("mobile",mobile);
		return mav;
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "do", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String evaluate(EvaluateUser user) {
		// 调用接口获取活动列表
		String act_body = "key=" + EvaluateConfig.key;
		String activity = GetHttpMsg.postHttp(EvaluateConfig.activity_url, act_body);
		String activityId=null;
		if (StringUtils.isNotBlank(activity)) {
			RespInfo respInfo=JacksonUtil.jsonToObj(activity, RespInfo.class);
			if (respInfo != null) {
				Map<String,String> map=(Map<String, String>) respInfo.getData().get(EvaluateConfig.activity_listKey);
				for(Map.Entry<String, String> entry:map.entrySet()){
					if(entry.getValue().equals(EvaluateConfig.activity_name)){
						activityId=entry.getKey();
						break;
					}
				}
			}
		}
		//调用评测接口
		/**
		 * http://gl.normstar.net/ns-napm-web/joinNapm/getAccountTestUrl.do?key=n11660987780433921&uuid=1111&tname=test
		 * &sex=man&birthday=2000-01-01&testSite=aa
		 */
		StringBuffer eva_body=new StringBuffer();
		eva_body.append("key="+EvaluateConfig.key);
		eva_body.append("&uuid="+user.getMobile());
		if(StringUtils.isNotBlank(activityId)){
			eva_body.append("&activityId="+activityId);
		}
		eva_body.append("&tname="+user.getUsername());
		eva_body.append("&sex="+user.getSex());
		eva_body.append("&birthday="+user.getBirth());
		eva_body.append("&testSite="+EvaluateConfig.test_site);
		eva_body.append("&phone="+user.getMobile());
		String evaRespInfo=GetHttpMsg.postHttp(EvaluateConfig.evaluate_url, eva_body.toString());
		String eva_address=null;
		if (StringUtils.isNotBlank(evaRespInfo)) {
			RespInfo respInfo=JacksonUtil.jsonToObj(evaRespInfo, RespInfo.class);
			if (respInfo != null) {
				eva_address=(String) respInfo.getData().get(EvaluateConfig.evaluate_urlKey);
				if(StringUtils.isNotBlank(eva_address)){
					Map<String,String> respMap=Maps.newHashMap();
					respMap.put("result", "success");
					respMap.put("eva_address", eva_address);
					return JacksonUtil.objToJson(respMap);
				}
			}
		}
		return null;
	}
}
