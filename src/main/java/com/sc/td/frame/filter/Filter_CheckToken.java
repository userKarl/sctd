package com.sc.td.frame.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sc.td.common.config.Global;
import com.sc.td.common.utils.UnicodeUtils;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.jwt.Jwt;
import com.sc.td.common.utils.redis.RedisService;
import com.sc.td.frame.RespInfoConfig;
import com.sc.td.frame.cfg.AppConfig;

public class Filter_CheckToken implements Filter {

	private RedisService redisService;

	private RespInfoConfig respInfoConfig;

	@SuppressWarnings("unchecked")
	@Override
	public void doFilter(ServletRequest argo, ServletResponse arg1, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) argo;
		HttpServletResponse response = (HttpServletResponse) arg1;
		redisService = (RedisService) AppConfig.getBean("redisService");
		respInfoConfig = (RespInfoConfig) AppConfig.getBean("respInfoConfig");
		String token = request.getHeader("token");
		response.setContentType("text/html;charset=utf-8");
		try {
			String url = request.getServletPath();
			String resp = "";
			if (url.endsWith("_ckt")) {
				// token为空直接返回null
				if (token == null || "".equals(token.trim())) {
					resp = CreateJson.createTextJson(respInfoConfig.getTokenIllegal(), false);
					response.sendRedirect(
							Global.contextPath + "/baseResp?resp=" + UnicodeUtils.enUnicode(resp));
				} else {
					// 如果token不为空，则验证token
					// 1、将token解析出来
					Map<String, Object> resultMap = Jwt.validToken(token);
					String tokenState = Jwt.getResult((String) resultMap.get("state"));
					// 2、验证token有效性
					String mobile = null;
					if ("valid".equals(tokenState)) {
						// 如果token有效，获取mobile信息
						Map<String, String> dataMap = new HashMap<String, String>();
						dataMap = (HashMap<String, String>) resultMap.get("data");
						mobile = dataMap.get("mobile");

						// 判断token在redis中是否存在
						List<String> list = redisService.hmget("token", mobile);
						String token_old = list.get(0);
						if (token_old != null || !"".equals(token_old)) {
							// 将传入的token与redis中token进行对比
							if (!token.equals(token_old)) {
								// 如果不一致，则认为客户端传入非法token，需重新登录
								resp = CreateJson.createTextJson(respInfoConfig.getTokenIllegal(), false);
								response.sendRedirect(Global.contextPath + "/baseResp?resp="
										+ UnicodeUtils.enUnicode(resp));
							} else {
								chain.doFilter(request, response);
								return;

							}
						} else {
							// 如果不存在，则认为token失效，返回登录页面重新登录
							resp = CreateJson.createTextJson(respInfoConfig.getTokenInvalid(), false);
							response.sendRedirect(Global.contextPath + "/baseResp?resp="
									+ UnicodeUtils.enUnicode(resp));
						}
					} else {
						resp = CreateJson.createTextJson(respInfoConfig.getTokenIllegal(), false);
						response.sendRedirect(
								Global.contextPath + "/baseResp?resp=" + UnicodeUtils.enUnicode(resp));
					}
				}

			} else {
				chain.doFilter(request, response);
				return;
			}
		} catch (Exception e) {
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void destroy() {

	}

}
