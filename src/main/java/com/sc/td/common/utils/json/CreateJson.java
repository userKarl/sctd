package com.sc.td.common.utils.json;

import java.util.HashMap;
import java.util.Map;

/**
 * 将普通的文本数据返回为json
 * 
 * @author Administrator
 *
 */
public class CreateJson {

	public static String createTextJson(String resp, boolean result) {
		if (resp == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", result);
		map.put("resp", resp);
		return JacksonUtil.objToJson(map);
	}

	public String createObjJson(String token, Object obj) {
		if (token == null || obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", true);
		map.put("token", token);
		map.put(obj.getClass().getSimpleName(), obj);
		return JacksonUtil.objToJson(map);
	}
}
