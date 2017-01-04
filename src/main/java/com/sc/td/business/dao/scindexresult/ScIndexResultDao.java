package com.sc.td.business.dao.scindexresult;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scindexresult.ScIndexResult;

public interface ScIndexResultDao extends BaseDao<ScIndexResult> {

	@Query(value="select t1.exchange_no,t1.commodity_no,t1.commodity_type,t1.func_index_id,t1.func_result,t1.remark"
			+ ",t1.create_by,t1.create_date,t1.update_by,t1.update_date from sc_index_result t1 "
			+ " left join sys_dict t3 on t1.exchange_no=t3.`value`,sc_stock t2 where func_index_id=?1"
			+ "	and t1.exchange_no=t2.exchange_no and t1.commodity_no=t2.stock_no and t3.type=?2 ",nativeQuery=true)
	List<ScIndexResult> findByFuncIndexIdStock(String funcIndexId,String type); 
	
	@Query(value="select t1.exchange_no,t1.commodity_no,t1.commodity_type,t1.func_index_id,t1.func_result,t1.remark"
			+ ",t1.create_by,t1.create_date,t1.update_by,t1.update_date from sc_index_result t1 "
			+ " left join sys_dict t3 on t1.exchange_no=t3.`value`,sc_futures t2 where func_index_id=?1"
			+ "	and t1.exchange_no=t2.exchange_no and t1.commodity_no=t2.code and t3.type=?2 ",nativeQuery=true)
	List<ScIndexResult> findByFuncIndexIdFutures(String funcIndexId,String type); 
	
	@Query(value="select t1.exchange_no,t1.commodity_no,t1.commodity_type,t1.func_index_id,t1.func_result,t1.remark"
			+ ",t1.create_by,t1.create_date,t1.update_by,t1.update_date from sc_index_result t1,sc_stock t2"
			+ "	where t1.exchange_no=t2.exchange_no and t1.commodity_no=t2.stock_no ",nativeQuery=true)
	List<ScIndexResult> findByStock(); 
	
	@Query(value="select t1.exchange_no,t1.commodity_no,t1.commodity_type,t1.func_index_id,t1.func_result,t1.remark"
			+ ",t1.create_by,t1.create_date,t1.update_by,t1.update_date from sc_index_result t1,sc_futures t2"
			+ "	where t1.exchange_no=t2.exchange_no and t1.commodity_no=t2.code",nativeQuery=true)
	List<ScIndexResult> findByFutures(); 
}
