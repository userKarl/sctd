package com.sc.td.business.service.scnotify;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Maps;
import com.google.common.collect.Multiset;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.scnotify.ScNotifyDao;
import com.sc.td.business.dao.scnotify.ScNotifyRecordDao;
import com.sc.td.business.entity.scnotify.ScNotify;
import com.sc.td.business.entity.scnotify.ScNotifyRecord;
import com.sc.td.common.utils.datetime.TimeUtil;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.DictConfig;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScNotifyService extends BaseService {

	@Autowired
	private ScNotifyRecordDao scNotifyRecordDao;

	@Autowired
	private ScNotifyDao scNotifyDao;

	@Autowired
	private RespInfoConfig respInfoConfig;

	@Autowired
	private DictConfig dictConfig;

	/**
	 * 根据userId获取消息记录
	 */
	@SuppressWarnings("unchecked")
	public String getNotifyRecordInfo(String jsonText) {
		getPage(jsonText);
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		String userId = null;
		if (jsonMap != null && jsonMap.size() > 0) {
			userId = jsonMap.get("userId");
		}
		List<ScNotify> list = scNotifyDao.findByUserIdAndStatus(userId, dictConfig.getMsg_publish());
		// 统计未读数据中每个类别的个数
		List<ScNotify> noReadList = scNotifyDao.findByUserIdAndReadFlag(userId, dictConfig.getNot_read_flag(),
				dictConfig.getMsg_publish());
		Map<String, Integer> countMap = Maps.newHashMap();
		if (noReadList != null && noReadList.size() > 0) {
			Multiset<String> multiset = HashMultiset.create();
			for (ScNotify sc : noReadList) {
				multiset.add(sc.getType());
			}
			for (String key : multiset.elementSet()) {
				countMap.put(key, multiset.count(key));
			}
		}
		if (list != null && list.size() > 0) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("result", true);
			map.put("count", countMap);
			map.put("data", list);
			return JacksonUtil.objToJson(map);
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}

	/**
	 * 根据userID和type获取消息的具体信息
	 * 
	 * @param jsonText
	 * @return
	 */
	@Transactional
	@SuppressWarnings("unchecked")
	public String getNotifyInfo(String jsonText) {
		getPage(jsonText);
		HashMap<String, String> jsonMap = JacksonUtil.jsonToObj(jsonText, HashMap.class);
		String userId = null;
		String type = null;
		if (jsonMap != null && jsonMap.size() > 0) {
			userId = jsonMap.get("userId");
			type = jsonMap.get("type");
		}
		// 将消息的状态设置为已读
		List<ScNotifyRecord> sclist = scNotifyRecordDao.findByUserIdAndType(userId, type, dictConfig.getMsg_publish());
		String readFlag = dictConfig.getRead_flag();
		if (sclist != null && sclist.size() > 0) {
			for (ScNotifyRecord sc : sclist) {
				sc.setReadDate(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSpdayFormat));
				sc.setReadFlag(readFlag);
				scNotifyRecordDao.save(sc);
			}
		}
		List<ScNotify> list = scNotifyDao.findByUserIdAndType(userId, type, dictConfig.getMsg_publish(), index, size);
		if (list != null && list.size() > 0) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("result", true);
			map.put("data", list);
			return JacksonUtil.objToJson(map);
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}
}
