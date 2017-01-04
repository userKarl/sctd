package com.sc.td.business.service.scstockgroup;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sc.td.business.dao.scpkresult.ScPkResultDao;
import com.sc.td.business.dao.scstockgroup.ScStockGroupDao;
import com.sc.td.business.entity.scpkresult.ScPkResult;
import com.sc.td.business.entity.scstockgroup.StockGroup;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.DictConfig;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScStockGroupService {

	@Autowired
	private ScStockGroupDao scStockGroupDao;
	
	@Autowired
	private RespInfoConfig respInfoConfig;
	
	@Autowired
	private ScPkResultDao scPkResultDao;
	
	@Autowired
	private DictConfig dictConfig;
	/**
	 * 删除
	 * @param jsonText
	 * @return
	 */
	@Transactional
	public String delete_ckt(String jsonText){
		StockGroup stockGroup=JacksonUtil.jsonToObj(jsonText, StockGroup.class);
		// 判断该战队是否在PK中
//		List<ScPkResult> pklist = scPkResultDao.findByGroupIdAndPkStatus(dictConfig.getPk_status_ing(),
//				stockGroup.getGroupId());
//		if (pklist.size() > 0) {
//			return CreateJson.createTextJson(respInfoConfig.getPkIng(), false);
//		}
		scStockGroupDao.delete(stockGroup);
		return CreateJson.createTextJson(respInfoConfig.getOperateSuccess(), true);
	}
}
