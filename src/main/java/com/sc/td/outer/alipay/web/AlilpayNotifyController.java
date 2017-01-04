package com.sc.td.outer.alipay.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.sc.td.common.config.Global;
import com.sc.td.common.utils.GetHttpMsg;
import com.sc.td.common.utils.ip.IpUtils;

@Controller
@RequestMapping("/alipay")
public class AlilpayNotifyController {

	@Autowired
	private AlipayNotifyService alipayNotifyService;
	
	/**
	 * 客户端请求
	 * @param jsonText
	 * @return
	 * @throws MalformedURLException
	 * @throws DocumentException
	 * @throws IOException
	 * @throws AlipayApiException 
	 */
	@RequestMapping(value = "/request", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String request(String jsonText) throws MalformedURLException, DocumentException, IOException, AlipayApiException {
		return alipayNotifyService.request(jsonText);
	}

	/**
	 * 支付宝异步通知
	 * @param request
	 * @param response
	 * @return
	 * @throws AlipayApiException
	 * @throws IOException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	@RequestMapping(value = "/notify", method = {RequestMethod.POST,RequestMethod.GET}, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String notify(HttpServletRequest request) throws AlipayApiException,
			IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/alipay/notify_url");
		Map<String, String> params = new HashMap<String, String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
			}
			// 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			// valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		String result = alipayNotifyService.notify(params);
		mav.addObject("result", result);
		String ip = IpUtils.getLocalIP();
		String contextPort = Global.reload_port;
		String contextPath = Global.contextPath;
		GetHttpMsg.getHttp(Global.protocol+"://" + ip + ":" + contextPort + contextPath+"/reload/"+Global.reload_scuser);
		return result;
	}

	
}
