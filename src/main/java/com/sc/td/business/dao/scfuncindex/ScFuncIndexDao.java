package com.sc.td.business.dao.scfuncindex;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scfuncindex.ScFuncIndex;

public interface ScFuncIndexDao extends BaseDao<ScFuncIndex>{

	@Query(value="select t1.func_index_id,t1.func_index_name,t1.remark,t1.func_param,t1.func_param_name,t1.calc_range,t1.module_name,"
			+ "t1.func_category,t2.label as category_name,t1.is_show,t1.icon_path,t1.del_flag,t1.create_by,t1.create_date,t1.update_by,t1.update_date"
			+ "	from sc_func_index t1 left join sys_dict t2 on t1.func_category=t2.value and t2.type='func_category'"
			+ "	where t1.is_show=?1",nativeQuery=true)
	List<ScFuncIndex> findByIsShow(String isShow);
}
