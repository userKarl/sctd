package com.sc.td.business.dao.scnotify;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scnotify.ScNotify;

public interface ScNotifyDao extends BaseDao<ScNotify> {

	@Query(value = "select t1.id,t1.type,t1.title,t1.content,t1.remarks,t1.status,"
			+ "t1.create_by,t1.create_date,t1.update_by,t1.update_date from sc_notify t1 ,sc_notify_record t2 "
			+ "where t1.id=t2.sc_notify_id  and t2.user_id=?1 and t2.read_flag=?2 and t1.status=?3", nativeQuery = true)
	List<ScNotify> findByUserIdAndReadFlag(String userId, String readFlag,String notifyStatus);

	@Query(value = "select t1.id,t1.type,t1.title,t1.content,t1.remarks,t1.status,"
			+ "t1.create_by,t1.create_date,t1.update_by,t1.update_date from sc_notify t1 ,sc_notify_record t2 "
			+ "where t1.id=t2.sc_notify_id and t2.user_id=?1 and t1.type=?2 and t1.status=?3 "
			+ "order by update_date DESC limit ?4,?5", nativeQuery = true)
	List<ScNotify> findByUserIdAndType(String userId, String type, String notifyStatus,int index, int size);
	
	@Query(value = "select * from (select t1.id,t1.type,t1.title,t1.content,t1.remarks,t1.status,"
			+ "t1.create_by,t1.create_date,t1.update_by,t1.update_date from sc_notify t1 ,sc_notify_record t2 "
			+ "where t1.id=t2.sc_notify_id and t2.user_id=?1 and t1.status=?2 order by update_date DESC )a group by type order by type ", nativeQuery = true)
	List<ScNotify> findByUserIdAndStatus(String userId,String notifyStatus);
}
