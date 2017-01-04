package com.sc.td.business.service.scpkresult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.scgroup.GroupDao;
import com.sc.td.business.dao.schisgrpstock.ScHisGrpStockDao;
import com.sc.td.business.dao.scpkresult.ScHisPkResultDao;
import com.sc.td.business.entity.scgroup.Group;
import com.sc.td.business.entity.schisgrpstock.ScHisGrpStock;
import com.sc.td.business.entity.scpkresult.ScHisPkResult;
import com.sc.td.business.entity.scuser.ScUser;
import com.sc.td.business.service.scgroup.ScGroupService;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.calc.ArithDecimal;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.DictConfig;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScHisPkResultService extends BaseService {

	@Autowired
	private ScHisPkResultDao scHisPkResultDao;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	@Autowired
	private ScGroupService scGroupService;

	@Autowired
	private DictConfig dictConfig;

	@Autowired
	private ScHisGrpStockDao scHisGrpStockDao;

	/**
	 * 获取发起方或接收方的数据列表
	 * 
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getlist_ckt(String jsonText) {
		HashMap<String, String> map = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		String groupId = map.get("groupId");
		String type = map.get("type");
		getPage(jsonText);
		List<ScHisPkResult> list = Lists.newArrayList();
		if ("apply".equals(type)) {
			list = scHisPkResultDao.findByApplyGroupIdOrderByStartDateDesc(groupId, index, size);
		} else if ("accept".equals(type)) {
			list = scHisPkResultDao.findByAcceptGroupIdOrderByStartDateDesc(groupId, index, size);
		}

		if (list != null && list.size() > 0) {
			ConcurrentHashMap<String, ConcurrentHashMap<String, ScUser>> scUserMap = BusinessTask.scUserMap;
			ConcurrentHashMap<String, ScUser> userMap = new ConcurrentHashMap<String, ScUser>();
			if (scUserMap != null) {
				userMap = scUserMap.get("scUser");
			}
			// 设置Group的值
			for (ScHisPkResult sc : list) {
				Group group = sc.getGroup();
				if ("apply".equals(type)) {
					group = groupDao.findByGroupId(sc.getAcceptGroupId());
				} else if ("accept".equals(type)) {
					group = groupDao.findByGroupId(sc.getApplyGroupId());
				}
				group = getUserInfo(group, userMap);
				sc.setGroup(group);
			}

			// 返回信息
			ConcurrentHashMap<String, Object> dataMap = new ConcurrentHashMap<String, Object>();
			dataMap.put("data", list);
			dataMap.put("result", true);
			return JacksonUtil.objToJson(dataMap);
		}

		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}

	/**
	 * 获取历史战绩
	 * 
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String getHisPkResultInfo_ckt(String jsonText) {
		getPage(jsonText);
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		if (jsonMap != null && jsonMap.size() > 0) {
			String groupId = jsonMap.get("groupId");
			String fromDate = jsonMap.get("fromDate");
			String endDate = jsonMap.get("endDate");
			List<ScHisPkResult> list = scHisPkResultDao.findResultList(fromDate, endDate, dictConfig.getPk_status_end(),
					groupId, index, size);
			// 整理数据
			ConcurrentHashMap<String, ScUser> userMap = new ConcurrentHashMap<String, ScUser>();
			if (BusinessTask.scUserMap != null) {
				userMap = BusinessTask.scUserMap.get("scUser");
			}
			List<ScHisPkResult> dataList = Lists.newArrayList();
			if (list != null && list.size() > 0) {
				for (ScHisPkResult sc : list) {
					String his_group_id = null;
					String group_id = null;
					// 接受的PK
					if (groupId.equals(sc.getAcceptGroupId())) {
						his_group_id = sc.getAcceptGroupId();
						group_id = sc.getApplyGroupId();
					} else if (groupId.equals(sc.getApplyGroupId())) {
						// 发起的PK
						his_group_id = sc.getApplyGroupId();
						group_id = sc.getAcceptGroupId();
					}
					Group hisgroup = getNewGroup(his_group_id);// 自身战队
					Group group = getNewGroup(group_id);// 对手战队
					if (StringUtils.isNotBlank(hisgroup.getGroupId()) && StringUtils.isNotBlank(group.getGroupId())) {
						group.setStockGroup(null);
						hisgroup.setStockGroup(null);
						List<ScHisGrpStock> hisGrpStockList = scHisGrpStockDao.findByGroupIdAndStartDate(his_group_id,
								sc.getStartDate());
						hisgroup.setScHisGrpStock(hisGrpStockList);
						hisgroup = getUserInfo(hisgroup, userMap);
						sc.setHisgroup(hisgroup);
						List<ScHisGrpStock> grpStockList = scHisGrpStockDao.findByGroupIdAndStartDate(group_id,
								sc.getStartDate());
						group.setScHisGrpStock(grpStockList);
						group = getUserInfo(group, userMap);
						sc.setGroup(group);
						dataList.add(sc);
					}
				}
			}
			Map<String, Object> map = Maps.newHashMap();
			map.put("result", true);
			map.put("data", dataList);
			return JacksonUtil.objToJson(map);
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}

	/**
	 * 为防止引用相同的对象，新建战队
	 * 
	 * @param groupId
	 * @return
	 */
	public Group getNewGroup(String groupId) {
		Group new_group = new Group();
		Group group = groupDao.findByGroupId(groupId);
		if (group != null) {
			new_group.setAcceptPkTimes(group.getAcceptPkTimes());
			new_group.setBuysellRatio(group.getBuysellRatio());
			new_group.setCreateBy(group.getCreateBy());
			new_group.setCreateDate(group.getCreateDate());
			new_group.setGroupId(group.getGroupId());
			new_group.setGroupName(group.getGroupName());
			new_group.setGroupType(group.getGroupType());
			new_group.setImage(group.getImage());
			new_group.setIsAllowPk(group.getIsAllowPk());
			new_group.setPkLevelName(group.getPkLevelName());
			new_group.setPkRowWin(group.getPkRowWin());
			new_group.setPkStatus(group.getPkStatus());
			new_group.setPkTotal(group.getPkTotal());
			new_group.setPkWin(group.getPkWin());
			new_group.setScHisGrpStock(group.getScHisGrpStock());
			new_group.setStockGroup(group.getStockGroup());
			new_group.setUpdateBy(group.getUpdateBy());
			new_group.setUpdateDate(group.getUpdateDate());
			new_group.setUserId(group.getUserId());
			new_group.setUserName(group.getUserName());
			new_group.setWinRate(group.getWinRate());
		}
		return new_group;
	}

	/**
	 * 根据group信息查找出关联的用户信息
	 * 
	 * @param group
	 * @return
	 */
	public Group getUserInfo(Group group, ConcurrentHashMap<String, ScUser> userMap) {

		// 设置Group的值
		if (group != null) {
			if (userMap != null) {
				group.setUserName(userMap.get(group.getUserId()).getUserName());
				group.setImage(userMap.get(group.getUserId()).getImage());
				group.setPkLevelName(userMap.get(group.getUserId()).getLevelName());
			}
			if (group.getPkTotal() != null && group.getPkTotal() != 0) {
				group.setWinRate(ArithDecimal.div(group.getPkWin(), group.getPkTotal(), 2));
			}
			group.setBuysellRatio(scGroupService.getbuysellRatio(group));
		}
		return group;
	}

}
