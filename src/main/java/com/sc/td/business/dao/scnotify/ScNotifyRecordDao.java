package com.sc.td.business.dao.scnotify;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scnotify.ScNotifyRecord;

public interface ScNotifyRecordDao extends BaseDao<ScNotifyRecord> {

	
	@Query(value="select t2.*,t1.status,t1.type from sc_notify t1,sc_notify_record t2 where t1.id=t2.sc_notify_id "
			+ "and t2.user_id=?1 and t1.type=?2 and t1.status=?3",nativeQuery=true)
	List<ScNotifyRecord> findByUserIdAndType(String userId,String type,String notifyStatus);
}
