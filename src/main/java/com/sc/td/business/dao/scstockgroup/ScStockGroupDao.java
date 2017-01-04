package com.sc.td.business.dao.scstockgroup;

import java.util.List;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scstockgroup.StockGroup;

public interface ScStockGroupDao extends BaseDao<StockGroup> {

	List<StockGroup> findByGroupId(String groupId);
}
