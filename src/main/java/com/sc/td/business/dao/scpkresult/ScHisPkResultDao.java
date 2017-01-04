package com.sc.td.business.dao.scpkresult;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scpkresult.ScHisPkResult;

public interface ScHisPkResultDao extends BaseDao<ScHisPkResult> {

	ScHisPkResult findByApplyGroupIdAndAcceptGroupIdAndStartDate(String applyGroupId, String acceptGroupId,
			String startDate);

	@Query(value = "select apply_group_id,accept_group_id,pk_result,start_date,end_date,apply_group_id_profit,accept_group_id_profit,"
			+ "money,pk_status,create_by,create_date,update_by,update_date from sc_his_pk_result"
			+ "	where apply_group_id=?1 order by start_date DESC limit ?2,?3", nativeQuery = true)
	List<ScHisPkResult> findByApplyGroupIdOrderByStartDateDesc(String applyGroupId, int index, int size);

	@Query(value = "select apply_group_id,accept_group_id,pk_result,start_date,end_date,apply_group_id_profit,accept_group_id_profit,"
			+ "money,pk_status,create_by,create_date,update_by,update_date from sc_his_pk_result"
			+ "	where accept_group_id=?1 order by start_date DESC limit ?2,?3", nativeQuery = true)
	List<ScHisPkResult> findByAcceptGroupIdOrderByStartDateDesc(String acceptGroupId, int index, int size);

	@Query(value = "select apply_group_id,accept_group_id,pk_result,start_date,end_date,apply_group_id_profit,accept_group_id_profit,"
			+ "money,pk_status,create_by,create_date,update_by,update_date from sc_his_pk_result"
			+ "	where start_date between ?1 and ?2 and pk_status=?3"
			+ "	and (apply_group_id=?4 or accept_group_id=?4) "
			+ "	order by start_date DESC limit ?5,?6", nativeQuery = true)
	List<ScHisPkResult> findResultList(String fromDate, String endDate, String pkStatus, String groupId, int index,
			int size);// 查询历史战绩

	List<ScHisPkResult> findByPkStatus(String pkStatus);
}
