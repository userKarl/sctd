package com.sc.td.business.dao.scpkresult;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scpkresult.ScPkResult;

public interface ScPkResultDao extends BaseDao<ScPkResult> {

	ScPkResult findByApplyGroupIdAndAcceptGroupId(String applyGroupId, String acceptGroupId);

	List<ScPkResult> findByApplyGroupIdAndAcceptGroupIdAndPkStatusIn(String applyGroupId, String acceptGroupId,
			List<String> pkStatusList);

	List<ScPkResult> findByPkStatusIn(List<String> pkStatusList);

	@Query(value = "select apply_group_id,accept_group_id,pk_result,start_date,end_date,money,pk_status,"
			+ "create_by,create_date,update_by,update_date from sc_pk_result"
			+ "	where apply_group_id=?1 order by start_date DESC limit ?2,?3", nativeQuery = true)
	List<ScPkResult> findByApplyGroupIdOrderByStartDateDesc(String applyGroupId, int index, int size);

	@Query(value = "select apply_group_id,accept_group_id,pk_result,start_date,end_date,money,pk_status,"
			+ "create_by,create_date,update_by,update_date from sc_pk_result"
			+ "	where accept_group_id=?1 order by start_date DESC limit ?2,?3", nativeQuery = true)
	List<ScPkResult> findByAcceptGroupIdOrderByStartDateDesc(String acceptGroupId, int index, int size);

	@Query(value = "select apply_group_id,accept_group_id,pk_result,start_date,end_date,money,pk_status,"
			+ "create_by,create_date,update_by,update_date from sc_pk_result" + "	where start_date between ?1 and ?2"
			+ "	and pk_status=?3 and (apply_group_id=?4 or accept_group_id=?4) "
			+ "	order by start_date DESC limit ?5,?6", nativeQuery = true)
	List<ScPkResult> findResultList(String fromDate, String endDate, String pkStatus, String groupId, int index,
			int size);

	List<ScPkResult> findByPkStatus(String pkStatus);

	@Query(value = "select apply_group_id,accept_group_id,pk_result,start_date,end_date,money,pk_status,"
			+ "create_by,create_date,update_by,update_date from sc_pk_result"
			+ "	where  pk_status=?1 and (apply_group_id=?2 or accept_group_id=?2) ", nativeQuery = true)
	List<ScPkResult> findByGroupIdAndPkStatus(String pkStatus, String groupId);
}
