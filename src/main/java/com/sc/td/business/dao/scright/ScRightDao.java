package com.sc.td.business.dao.scright;

import java.util.List;
import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scright.ScRight;

public interface ScRightDao extends BaseDao<ScRight> {
	
	List<ScRight> findByUserId(String userId);
	
	List<ScRight> findByUserIdAndRightIdAndIsEnable(String userId,String rightId,String isEnable);
}
