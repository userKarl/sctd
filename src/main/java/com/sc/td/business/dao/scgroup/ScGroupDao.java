package com.sc.td.business.dao.scgroup;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scgroup.ScGroup;

public interface ScGroupDao extends BaseDao<ScGroup> {

	@Query(value = "select group_id,group_name,user_id,group_type,pk_total,pk_win,pk_row_win,accept_pk_times,is_allow_pk"
			+ "create_by,create_date,update_by,update_date "
			+ "from sc_group order by (pk_win/pk_total) DESC limit 0,20", nativeQuery = true)
	List<ScGroup> findGroupRankDESC(List<Object> paramList);
	
}
