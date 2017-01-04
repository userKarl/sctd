package com.sc.td.business.dao.scplate;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scplate.ScPlate;

public interface ScPlateDao extends BaseDao<ScPlate> {

	@Query(value = "select a.id,a.parent_id,a.parent_ids,a.name,a.sort,a.create_by,a.create_date,a.update_by,a.update_date,"
			+ "a.is_show,a.del_flag,a.remarks,p.name as parent_name"
			+ " from sc_plate a left join sc_plate p on a.parent_id=p.id" + " order by a.sort ASC", nativeQuery = true)
	List<ScPlate> findAll();

}
