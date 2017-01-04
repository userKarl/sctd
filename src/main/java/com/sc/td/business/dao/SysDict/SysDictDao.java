package com.sc.td.business.dao.SysDict;

import java.util.List;
import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.sysdict.SysDict;

public interface SysDictDao extends BaseDao<SysDict> {

	List<SysDict> findByType(String type);

	List<SysDict> findByRemarks(String remarks);

	List<SysDict> findByTypeAndValue(String type, String value);
}
