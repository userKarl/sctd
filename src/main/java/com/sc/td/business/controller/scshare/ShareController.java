package com.sc.td.business.controller.scshare;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.sc.td.business.dao.scfutures.ScFuturesDao;
import com.sc.td.business.dao.scstock.ScStockDao;
import com.sc.td.business.entity.scgroup.Group;
import com.sc.td.business.entity.scstockgroup.StockGroup;
import com.sc.td.business.entity.scuser.ScUser;
import com.sc.td.business.service.scgroup.ScGroupService;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.utils.StringUtils;

@Controller
@RequestMapping("/operate/share")
public class ShareController {

	@Autowired
	private ScGroupService scGroupService;

	@Autowired
	private ScStockDao scStockDao;

	@Autowired
	private ScFuturesDao scFuturesDao;

	@RequestMapping(value = "/getSharePage", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView getPage(HttpServletRequest request, String groupId) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("share");
		String downloadUrl = null;
		// 判断设备类型
		String userAgent = request.getHeader("user-adgent");
		if (StringUtils.isNotBlank(userAgent)) {
			if (userAgent.toLowerCase().contains("iphone")) {
				downloadUrl = "";
			} else if (userAgent.toLowerCase().contains("android")) {
				downloadUrl = "";
			}
		}
		mav.addObject("downloadUrl", downloadUrl);
		// 根据groupID获取战队信息
		Group group = scGroupService.getGroupInfo(groupId);
		if (group != null) {
			group.setWinRate(Math.floor(group.getWinRate()*100));
			group.setBuysellRatio(Math.floor(group.getBuysellRatio()*100));
			List<StockGroup> stockGroupList = group.getStockGroup();
			if (stockGroupList.size() > 0) {
				for (StockGroup sg : stockGroupList) {
					sg.setPercent(Math.floor(sg.getPercent()*100));
					if (sg.getCommodityType().startsWith("1")) {
						sg.setScStock(scStockDao.findByExchangeNoAndStockNoAndCommodityType(sg.getExchangeNo(),
								sg.getStockNo(), sg.getCommodityType()));
					}else if(sg.getCommodityType().startsWith("0")){
						sg.setScFutures(scFuturesDao.findByExchangeNoAndCodeAndCommodityType(sg.getExchangeNo(),
								sg.getStockNo(), sg.getCommodityType()));
					}
				}
			}
		}
		ConcurrentHashMap<String, ConcurrentHashMap<String, ScUser>> scUserMap = BusinessTask.scUserMap;
		ConcurrentHashMap<String, ScUser> userMap = new ConcurrentHashMap<String, ScUser>();
		if (scUserMap != null) {
			userMap = scUserMap.get("scUser");
		}
		// 设置Group的值
		if (group != null && userMap != null) {
				group.setPkLevelName(userMap.get(group.getUserId()).getLevelName());
		}
		mav.addObject("group", group);
		return mav;
	}

}
