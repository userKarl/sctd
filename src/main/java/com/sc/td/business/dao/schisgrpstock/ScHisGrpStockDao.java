package com.sc.td.business.dao.schisgrpstock;

import java.util.List;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.schisgrpstock.ScHisGrpStock;

public interface ScHisGrpStockDao extends BaseDao<ScHisGrpStock> {

	List<ScHisGrpStock> findByGroupIdAndStartDate(String groupId,String startDate);
}
