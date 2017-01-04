package com.sc.td.business.dao.scgroup;

import java.util.List;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scgroup.Group;

public interface GroupDao extends BaseDao<Group> {

	Group findByGroupId(String groupId);
	
	List<Group> findByGroupName(String groupName);

	List<Group> findByUserId(String userId);

	List<Group> findByGroupIdIn(List<String> groupIdList);
}
