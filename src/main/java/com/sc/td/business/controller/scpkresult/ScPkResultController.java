package com.sc.td.business.controller.scpkresult;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.sc.td.business.base.BaseController;
import com.sc.td.business.dao.scpkresult.ScPkResultDao;
import com.sc.td.business.entity.scpkresult.ScPkResult;
import com.sc.td.business.service.scpkresult.ScPkResultService;

@Controller
@RequestMapping("/operate/scpkresult")
public class ScPkResultController extends BaseController {

	@Autowired
	private ScPkResultService scPkResultService;

	@Autowired
	private ScPkResultDao scPkResultDao;

	@RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String test(String jsonText) {
		List<String> dataList = Lists.newArrayList();
		dataList.add("0");
		dataList.add("1");
		List<ScPkResult> list = scPkResultDao.findByApplyGroupIdAndAcceptGroupIdAndPkStatusIn("1", "2", dataList);
		System.out.println(list.get(0).getApplyGroupId());
		return "test";
	}

	/**
	 * 获取发起方或接收方的数据列表
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/getlist_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String getlist_ckt(String jsonText) {
		return scPkResultService.getlist_ckt(jsonText);
	}

	/**
	 * 发起PK
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/createpk_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String createpk_ckt(String jsonText) {
		return scPkResultService.createpk_ckt(jsonText);
	}

	/**
	 * 更新PK状态
	 * 
	 * @param jsonText
	 * @return
	 */
	@RequestMapping(value = "/changestatus_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String changestatus_ckt(String jsonText) {
		return scPkResultService.changestatus_ckt(jsonText);
	}

//	/**
//	 * 获取历史战绩
//	 * 
//	 * @param jsonText
//	 * @return
//	 */
//	@RequestMapping(value = "/getPkResultInfo_ckt", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//	@ResponseBody
//	public String getPkResultInfo_ckt(String jsonText) {
//
//		return scPkResultService.getPkResultInfo_ckt(jsonText);
//	}

}
