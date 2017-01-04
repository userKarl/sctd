package com.sc.td.business.service.scpromotion;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Maps;
import com.sc.td.business.base.BaseService;
import com.sc.td.business.dao.scpromotion.ScPromotionDao;
import com.sc.td.business.entity.scpromotion.ScPromotion;
import com.sc.td.common.utils.json.CreateJson;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.frame.DictConfig;
import com.sc.td.frame.RespInfoConfig;

@Service
public class ScPromotionService extends BaseService{

	@Autowired
	private ScPromotionDao scPromotionDao;
	
	@Autowired
	private DictConfig dictConfig;
	
	@Autowired
	private RespInfoConfig respInfoConfig;
	
	/**
	 * 获取需要显示的数据
	 * @return
	 */
	public String getInfo(){
		List<ScPromotion> list=scPromotionDao.findByIsShow(dictConfig.getIs_show());
		if(list.size()>0){
			for(ScPromotion sc:list){
				sc.setPromotionImg(getServerAddress()+sc.getPromotionImg());
			}
			Map<String,Object> map=Maps.newHashMap();
			map.put("result", true);
			map.put("data", list);
			return JacksonUtil.objToJson(map);
		}
		return CreateJson.createTextJson(respInfoConfig.getNonData(), false);
	}
}
