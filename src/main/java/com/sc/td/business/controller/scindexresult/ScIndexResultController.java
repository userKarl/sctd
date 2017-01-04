package com.sc.td.business.controller.scindexresult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.sc.td.business.service.scindexresult.ScIndexResultService;

@Controller
@RequestMapping("/operate/scindexresult")
public class ScIndexResultController {

	@Autowired
	private ScIndexResultService scIndexResultService;


	/**
	 * 根据指标ID获取数据
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getIndexResult", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getIndexResult(String jsonText) {
		return scIndexResultService.getIndexResult(jsonText);
	}
	
	
	/**
	 * 从内存中获取指标数据
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getScIndexResult", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getScIndexResult(String jsonText) {
		return scIndexResultService.getScIndexResult(jsonText);
	}
}
