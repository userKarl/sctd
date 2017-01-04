package com.sc.td.business.dao.scpromotion;

import java.util.List;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scpromotion.ScPromotion;

public interface ScPromotionDao extends BaseDao<ScPromotion>{

	List<ScPromotion> findByIsShow(String isShow);
}
