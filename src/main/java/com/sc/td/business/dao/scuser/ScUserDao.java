package com.sc.td.business.dao.scuser;

import java.util.List;
import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scuser.ScUser;

public interface ScUserDao extends BaseDao<ScUser> {

	List<ScUser> findByUserIdAndPassword(String userId, String password);

	List<ScUser> findByMobileAndPassword(String mobile, String password);

	List<ScUser> findByUserName(String userName);

	List<ScUser> findByMobile(String mobile);

	ScUser findByUserId(String userId);
}
