package com.sc.td.business.dao.scpklevel;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scpklevel.ScPklevel;

public interface ScPklevelDao extends BaseDao<ScPklevel>{

	@Query(value="select level_id,level_name,sort,money,remark,create_by,create_date,update_by,update_date"
			+ " from sc_pklevel order by sort ASC limit 0,1",nativeQuery=true)
	ScPklevel findMinLevel();
	
	@Query(value="select level_id,level_name,sort,money,remark,create_by,create_date,update_by,update_date"
			+ " from sc_pklevel order by money ASC ",nativeQuery=true)
	List<ScPklevel> findByMoneyOrderByMoneyAsc();
	
	ScPklevel findByLevelId(String levelId);
}
