package com.sc.td.business.service.scpkresult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.SysDict.SysDictDao;
import com.sc.td.business.dao.scgroup.GroupDao;
import com.sc.td.business.dao.scmoney.ScMoneyDao;
import com.sc.td.business.dao.scnotify.ScNotifyRecordDao;
import com.sc.td.business.dao.scpkresult.ScPkResultDao;
import com.sc.td.business.dao.scuser.ScUserDao;
import com.sc.td.business.entity.scgroup.Group;
import com.sc.td.business.entity.scmoney.ScMoney;
import com.sc.td.business.entity.scnotify.ScNotify;
import com.sc.td.business.entity.scnotify.ScNotifyRecord;
import com.sc.td.business.entity.scpkresult.ScPkResult;
import com.sc.td.business.entity.scuser.ScUser;
import com.sc.td.business.entity.sysdict.SysDict;
import com.sc.td.business.service.scgroup.ScGroupService;
import com.sc.td.business.task.BusinessTask;
import com.sc.td.common.config.Global;
import com.sc.td.common.utils.IdGen;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.calc.ArithDecimal;
import com.sc.td.common.utils.datetime.TimeUtil;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.DictConfig;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScPkResultService extends BaseService {

	@Autowired
	private ScPkResultDao scPkResultDao;

	@Autowired
	private ScUserDao scUserDao;

	@Autowired
	private SysDictDao sysDictDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	@Autowired
	private DictConfig dictConfig;

	@Autowired
	private GroupDao groupDao;

	@Autowired
	private ScGroupService scGroupService;

	@Autowired
	private ScMoneyDao scMoneyDao;

	@Autowired
	private ScNotifyRecordDao scNotifyRecordDao;

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
		List<ScPkResult> list = Lists.newArrayList();
		if ("apply".equals(type)) {
			list = scPkResultDao.findByApplyGroupIdOrderByStartDateDesc(groupId, index, size);
		} else if ("accept".equals(type)) {
			list = scPkResultDao.findByAcceptGroupIdOrderByStartDateDesc(groupId, index, size);
		}

		if (list != null && list.size() > 0) {
			ConcurrentHashMap<String, ConcurrentHashMap<String, ScUser>> scUserMap = BusinessTask.scUserMap;
			ConcurrentHashMap<String, ScUser> userMap = new ConcurrentHashMap<String, ScUser>();
			if (scUserMap != null) {
				userMap = scUserMap.get("scUser");
			}
			// 设置Group的值
			for (ScPkResult sc : list) {
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
	 * 发起PK
	 * 
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String createpk_ckt(String jsonText) {
		// 解析Json数据，获取PK发起方和PK接收方的组合ID
		HashMap<String, String> map = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		String applyUserId = map.get("applyUserId");
		String acceptUserId = map.get("acceptUserId");
		// 不能和自己PK
		if (applyUserId.equals(acceptUserId)) {
			return CreateJson.createTextJson(respInfoConfig.getPkSelfUser(), false);
		}
		// 判断用户是否存在（理论上用户是存在的）
		ScUser applyUser = scUserDao.findByUserId(applyUserId);
		ScUser acceptser = scUserDao.findByUserId(acceptUserId);
		if (applyUser == null || acceptser == null) {
			return CreateJson.createTextJson(respInfoConfig.getUserNotExist(), false);
		}
		String applyGroupId = map.get("applyGroupId");
		String acceptGroupId = map.get("acceptGroupId");
		// 判断是否已经和该对象进行PK
		List<String> pkStatusList = Lists.newArrayList();
		pkStatusList.add(dictConfig.getPk_status_call());// 发起
		pkStatusList.add(dictConfig.getPk_status_ing());// PK中
		List<ScPkResult> applyList = scPkResultDao.findByApplyGroupIdAndAcceptGroupIdAndPkStatusIn(applyGroupId,
				acceptGroupId, pkStatusList);
		List<ScPkResult> acceptList = scPkResultDao.findByApplyGroupIdAndAcceptGroupIdAndPkStatusIn(acceptGroupId,
				applyGroupId, pkStatusList);
		if (applyList.size() > 0 || acceptList.size() > 0) {
			// 已经存在PK中的状态
			return CreateJson.createTextJson(respInfoConfig.getPkExist(), false);
		}

		// 判断用户的虚拟币是否足够进行一场PK
		Double pk_money = 0.0;
		// 根据数据字典确定PK所需金额
		List<SysDict> dictList = sysDictDao.findByType(dictConfig.getPk_money_key());
		if (dictList.size() > 0) {
			pk_money = Double.parseDouble(dictList.get(0).getValue());
		}
		if (applyUser.getMoney() < pk_money) {
			// PK发起方余额不足
			return CreateJson.createTextJson(respInfoConfig.getApplyPkmoenyNotenough(), false);
		}
		if (acceptser.getMoney() < pk_money) {
			// PK接收方余额不足
			return CreateJson.createTextJson(respInfoConfig.getAcceptPkmoenyNotenough(), false);
		}

		if (StringUtils.isNotBlank(applyGroupId) && StringUtils.isNotBlank(acceptGroupId)) {
			Group applyScGroup = groupDao.findByGroupId(applyGroupId);
			Group acceptScGroup = groupDao.findByGroupId(acceptGroupId);

			// 判断发起方或接受方是否开启允许PK
			if (applyScGroup.getIsAllowPk().equals(dictConfig.getGroup_pk_notallow())) {
				return CreateJson.createTextJson(respInfoConfig.getPkApplyClose(), false);
			}
			if (acceptScGroup.getIsAllowPk().equals(dictConfig.getGroup_pk_notallow())) {
				return CreateJson.createTextJson(respInfoConfig.getPkAcceptClose(), false);
			}

			// 判断接受方是否已经达到每天可接受场次的上限
			if (acceptScGroup.getAcceptPkTimes() >= Integer.parseInt(dictConfig.getGroup_accept_pktimes())) {
				return CreateJson.createTextJson(respInfoConfig.getGroupPkAcceptlimit(), false);
			}

			ScPkResult sc = new ScPkResult();
			sc.setApplyGroupId(applyGroupId);
			sc.setAcceptGroupId(acceptGroupId);
			sc.setPkResult(dictConfig.getPk_result_pre());
			setDate(sc);
			sc.setMoney(pk_money * 2);
			sc.setPkStatus(dictConfig.getPk_status_call());
			sc.setInitValue(sc, applyUser.getUserId());
			// 接受方的可接受次数加1
			acceptScGroup.setAcceptPkTimes(acceptScGroup.getAcceptPkTimes() + 1);
			// 更新 user表的money字段值
			applyUser.setMoney((applyUser.getMoney() - pk_money));
			applyUser.setUpdateValue(applyUser, applyUser.getUserId());
			acceptser.setMoney((acceptser.getMoney() - pk_money));
			acceptser.setUpdateValue(acceptser, acceptser.getUserId());
			// 更新出入金表sc_money表
			ScMoney scMoneyApply = setInitScMoney(applyUser);
			scMoneyApply.setMoney(-pk_money);
			scMoneyApply.setRemark("和" + acceptser.getUserName() + "PK的赌注");
			ScMoney scMoneyAccept = setInitScMoney(acceptser);
			scMoneyAccept.setMoney(-pk_money);
			scMoneyAccept.setRemark("和" + applyUser.getUserName() + "PK的赌注");
			// 发起PK成功之后，重载内存中ScUser的数据
			if (scPkResultDao.save(sc) != null && groupDao.save(acceptScGroup) != null
					&& scUserDao.save(applyUser) != null && scUserDao.save(acceptser) != null
					&& scMoneyDao.save(scMoneyApply) != null && scMoneyDao.save(scMoneyAccept) != null) {
				reloadScUser();
				// 发起PK之后，通知被挑战者
				if (applyScGroup != null && acceptScGroup != null) {
					String title = "战队PK公告";
					String acceptContent = "战队" + applyScGroup.getGroupName() + "向您发起PK，该PK将在"
							+ TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSpdayFormat) + Global.pk_start_time
							+ "开始，结束时间为：" + formatDate(sc.getEndDate()) + Global.pk_start_time + "。";
					createNotify(acceptUserId, dictConfig.getMsg_type_pk(), title, acceptContent);
				}
				return CreateJson.createTextJson(respInfoConfig.getOperateSuccess(), true);
			} else {
				return CreateJson.createTextJson(respInfoConfig.getOperateFail(), false);
			}
		} else {
			return CreateJson.createTextJson(respInfoConfig.getParamsNull(), false);
		}
	}

	/**
	 * 将yyyymmdd格式的日期转化为yyyy-mm-dd
	 */
	public String formatDate(String date) {
		StringBuffer sb = new StringBuffer("");
		if (StringUtils.isNotBlank(date) && date.length() >= 8) {
			sb.append(date.substring(0, 4));
			sb.append("-");
			sb.append(date.substring(4, 6));
			sb.append("-");
			sb.append(date.substring(6, 8));
		}
		return sb.toString();
	}

	/**
	 * 更改PK状态
	 * 
	 * @param jsonText
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Transactional
	public String changestatus_ckt(String jsonText) {
		HashMap<String, String> map = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		String applyUserId = map.get("applyUserId");
		String acceptUserId = map.get("acceptUserId");
		String applyGroupId = map.get("applyGroupId");
		String acceptGroupId = map.get("acceptGroupId");
		String status = map.get("status");
		ScPkResult scPkResult = scPkResultDao.findByApplyGroupIdAndAcceptGroupId(applyGroupId, acceptGroupId);
		if (scPkResult == null) {
			// PK不存在
			return CreateJson.createTextJson(respInfoConfig.getGroupNotexist(), false);
		}
		// PK中和PK结束的不能修改状态
		if (scPkResult.getPkStatus().equals(dictConfig.getPk_status_ing())) {
			return CreateJson.createTextJson(respInfoConfig.getPkIng(), false);
		}
		if (scPkResult.getPkStatus().equals(dictConfig.getPk_status_end())) {
			return CreateJson.createTextJson(respInfoConfig.getPkEnd(), false);
		}
		Double money = scPkResult.getMoney() / 2;
		Double pkRejectMoney = 0.0;
		List<SysDict> dictList = sysDictDao.findByType(dictConfig.getPk_rejectMoney_key());
		if (dictList.size() > 0) {
			pkRejectMoney = Double.parseDouble(dictList.get(0).getValue());
		}
		// 拒绝
		if (status.equals(dictConfig.getPk_status_refuse())) {
			// 更新用户的money
			ScUser applyScUser = scUserDao.findOne(applyUserId);
			ScUser acceptScUser = scUserDao.findOne(acceptUserId);
			applyScUser.setMoney(applyScUser.getMoney() + money + pkRejectMoney);
			applyScUser.setUpdateValue(applyScUser, applyScUser.getUserId());
			acceptScUser.setMoney(acceptScUser.getMoney() + money - pkRejectMoney);
			acceptScUser.setUpdateValue(acceptScUser, acceptScUser.getUserId());
			// 重载内存中ScUser的数据
			if (scUserDao.save(applyScUser) != null && scUserDao.save(acceptScUser) != null) {
				reloadScUser();
			}

			// 更新出入金表（1、返还双方赌注 ；2、申请方获得赔偿；3、拒绝方支付赔偿）
			ScMoney scMoneyApply = setInitScMoney(applyScUser);
			scMoneyApply.setMoney(money);
			scMoneyApply.setRemark("和" + acceptScUser.getUserName() + "的PK被拒绝，赌注返还");
			ScMoney scMoneyAccept = setInitScMoney(acceptScUser);
			scMoneyAccept.setMoney(money);
			scMoneyAccept.setRemark("拒绝" + applyScUser.getUserName() + "的PK申请，赌注返还");
			ScMoney scMoneyApply1 = setInitScMoney(applyScUser);
			scMoneyApply1.setMoney(pkRejectMoney);
			scMoneyApply1.setRemark("和" + acceptScUser.getUserName() + "的PK被拒绝，获得赔偿");
			ScMoney scMoneyAccept1 = setInitScMoney(acceptScUser);
			scMoneyAccept1.setMoney(-pkRejectMoney);
			scMoneyAccept1.setRemark("拒绝" + applyScUser.getUserName() + "的PK申请，支付罚金");
			// 更新PK等级
			updatePkLevel(applyScUser);
			updatePkLevel(acceptScUser);
			// 设置更新时间
			scPkResult.setUpdateValue(scPkResult, acceptUserId);
			scPkResult.setPkStatus(status);
			// 将接受方的接受PK次数减1
			Group acceptScGroup = groupDao.findByGroupId(acceptGroupId);
			acceptScGroup.setAcceptPkTimes(acceptScGroup.getAcceptPkTimes() - 1);
			if (scMoneyDao.save(scMoneyApply) != null && scMoneyDao.save(scMoneyAccept) != null
					&& scMoneyDao.save(scMoneyApply1) != null && scMoneyDao.save(scMoneyAccept1) != null
					&& scPkResultDao.save(scPkResult) != null && groupDao.save(acceptScGroup) != null) {
				// 拒绝成功之后，通知PK发起者
				String title = "战队PK公告";
				String acceptContent = "战队" + acceptScGroup.getGroupName() + "拒绝了您发起的PK，您将获得" + pkRejectMoney
						+ "墨币的赔偿。";
				createNotify(applyUserId, dictConfig.getMsg_type_pk(), title, acceptContent);
				Map<String, Object> dataMap = Maps.newHashMap();
				dataMap.put("data", scPkResult);
				dataMap.put("result", true);
				return JacksonUtil.objToJson(dataMap);
			} else {
				return CreateJson.createTextJson(respInfoConfig.getOperateFail(), false);
			}

		} else if (status.equals(dictConfig.getPk_status_recall())) {
			// 撤回
			if (scPkResult.getPkStatus().equals(dictConfig.getPk_status_call())) {
				scPkResultDao.delete(scPkResult);
				// 更新用户的money
				ScUser applyScUser = scUserDao.findOne(applyUserId);
				ScUser acceptScUser = scUserDao.findOne(acceptUserId);
				applyScUser.setMoney(applyScUser.getMoney() + money);
				applyScUser.setUpdateValue(applyScUser, applyScUser.getUserId());
				acceptScUser.setMoney(acceptScUser.getMoney() + money);
				acceptScUser.setUpdateValue(acceptScUser, acceptScUser.getUserId());
				// 撤回成功之后，重载内存中ScUser的数据
				if (scUserDao.save(applyScUser) != null && scUserDao.save(acceptScUser) != null) {
					reloadScUser();
				}
				// 更新出入金表（返还赌注）
				ScMoney scMoneyApply = setInitScMoney(applyScUser);
				scMoneyApply.setMoney(money);
				scMoneyApply.setRemark("撤销和" + acceptScUser.getUserName() + "的PK，赌注返还");
				ScMoney scMoneyAccept = setInitScMoney(acceptScUser);
				scMoneyAccept.setMoney(money);
				scMoneyAccept.setRemark(applyScUser.getUserName() + "撤销PK申请，赌注返还");
				// 更新PK等级
				updatePkLevel(applyScUser);
				updatePkLevel(acceptScUser);
				// 将接受方的接受PK次数减 1
				Group acceptScGroup = groupDao.findByGroupId(acceptGroupId);
				acceptScGroup.setAcceptPkTimes(acceptScGroup.getAcceptPkTimes() - 1);
				Group applyScGroup = groupDao.findByGroupId(applyGroupId);
				if (scMoneyDao.save(scMoneyApply) != null && scMoneyDao.save(scMoneyAccept) != null
						&& groupDao.save(acceptScGroup) != null) {
					// 撤回成功之后，通知被挑战者
					if (applyScGroup != null) {
						String title = "战队PK公告";
						String acceptContent = "战队" + applyScGroup.getGroupName() + "撤回了向您发起的PK。";
						createNotify(acceptUserId, dictConfig.getMsg_type_pk(), title, acceptContent);
					}
					return CreateJson.createTextJson(respInfoConfig.getOperateSuccess(), true);
				} else {
					return CreateJson.createTextJson(respInfoConfig.getOperateFail(), false);
				}
			}
		}
		return CreateJson.createTextJson(respInfoConfig.getOperateFail(), false);

	}

	// /**
	// * 获取历史战绩
	// *
	// * @param jsonText
	// * @return
	// */
	// @SuppressWarnings("unchecked")
	// public String getPkResultInfo_ckt(String jsonText) {
	// getPage(jsonText);
	// HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText,
	// HashMap.class);
	// if (jsonMap != null) {
	// String groupId = jsonMap.get("groupId");
	// String fromDate = jsonMap.get("fromDate");
	// String endDate = jsonMap.get("endDate");
	// List<ScPkResult> list = scPkResultDao.findResultList(fromDate, endDate,
	// dictConfig.getPk_status_end(),
	// groupId, index, size);
	// // 整理数据
	// ConcurrentHashMap<String, ScUser> userMap = new ConcurrentHashMap<String,
	// ScUser>();
	// if (BusinessTask.scUserMap != null) {
	// userMap = BusinessTask.scUserMap.get("scUser");
	// }
	// if (list.size() > 0) {
	// for (ScPkResult sc : list) {
	// Group group = sc.getGroup(); // 对手战队
	// Group hisgroup = sc.getHisgroup(); // 自身战队历史信息
	// // 接受的PK
	// if (sc.getAcceptGroupId().equals(groupId)) {
	// hisgroup = groupDao.findByGroupId(sc.getAcceptGroupId());
	// group = groupDao.findByGroupId(sc.getApplyGroupId());
	// } else if (sc.getApplyGroupId().equals(groupId)) {
	// // 发起的PK
	// hisgroup = groupDao.findByGroupId(sc.getApplyGroupId());
	// group = groupDao.findByGroupId(sc.getAcceptGroupId());
	// }
	// group = getUserInfo(group, userMap);
	// hisgroup = getUserInfo(hisgroup, userMap);
	// sc.setGroup(group);
	// sc.setHisgroup(hisgroup);
	// }
	// }
	// Map<String, Object> map = Maps.newHashMap();
	// map.put("result", true);
	// map.put("data", list);
	// return JacksonUtil.objToJson(map);
	// }
	// return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	// }

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

	/**
	 * 创建消息
	 * 
	 * @param userId
	 * @param title
	 * @param content
	 */
	public void createNotify(String userId, String type, String title, String content) {
		ScNotify sc = new ScNotify();
		sc.setId(IdGen.uuid());
		sc.setType(type);
		sc.setTitle(title);
		sc.setContent(content);
		sc.setInitValue(sc, userId);
		sc.setStatus(dictConfig.getMsg_publish());
		ScNotifyRecord scNotifyRecord = new ScNotifyRecord();
		scNotifyRecord.setId(IdGen.uuid());
		scNotifyRecord.setScNotify(sc);
		scNotifyRecord.setUserId(userId);
		scNotifyRecord.setReadFlag(dictConfig.getNot_read_flag());
		scNotifyRecordDao.save(scNotifyRecord);
	}
}
