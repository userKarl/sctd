package com.sc.td.web.pkgroup;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.sc.td.business.dao.scgroup.GroupDao;
import com.sc.td.business.dao.schisgrpstock.ScHisGrpStockDao;
import com.sc.td.business.entity.scgroup.Group;
import com.sc.td.business.entity.schisgrpstock.ScHisGrpStock;
import com.sc.td.common.utils.GetHttpMsg;
import com.sc.td.common.utils.datetime.TimeUtil;

@Controller
@RequestMapping("/operate/web/group")
public class GroupWebController {

	@Autowired
	private ScHisGrpStockDao scHisGrpStockDao;

	@Autowired
	private GroupWebService groupWebService;

	@Autowired
	private GroupDao groupDao;

	@RequestMapping(value = "", method = { RequestMethod.GET })
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		String startDate = TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DBdayFormat);
		mav.setViewName("group");
		mav.addObject("startDate", startDate);
		return mav;
	}

	@RequestMapping(value = "/search", method = { RequestMethod.POST }, produces = "application/json; charset=utf-8")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response, String groupId,
			String startDate) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("group");
		Group group = groupDao.findByGroupId(groupId);
		if (group != null) {
			List<ScHisGrpStock> scHisGrpStockList = scHisGrpStockDao.findByGroupIdAndStartDate(groupId, startDate);
			if (scHisGrpStockList.size() > 0) {
				Double totalProfit = groupWebService.caclProfit(scHisGrpStockList);
				mav.addObject("totalProfit", totalProfit);
			}
			mav.addObject("groupName", group.getGroupName());
		}

		return mav;
	}

	/**
	 * 后台管理页面调用接口
	 * @param request
	 * @param response
	 * @param arr
	 * @param userId
	 * @return
	 */
	@RequestMapping(value = "/calc", method = { RequestMethod.POST,
			RequestMethod.GET }, produces = "application/json; charset=utf-8")
	@ResponseBody
	public String calc(String arr, String userId) {
			return groupWebService.exec(arr, userId);
	}

	
	@RequestMapping(value = "/test", method = { RequestMethod.GET })
	@ResponseBody
	public String test() {
		String arr="f3279321c7914e28b7f5061d0cb2c06b,5855b39022e748c6a6a6dd5d4cfe889f,20161116";
		String userId="1";
		GetHttpMsg.getHttp("http://192.168.1.119:8080/sctd/operate/web/group/calc?arr="+arr+"&userId="+userId);
		return "test";
	}
}
