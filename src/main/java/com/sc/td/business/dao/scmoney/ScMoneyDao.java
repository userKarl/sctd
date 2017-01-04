package com.sc.td.business.dao.scmoney;

import java.util.List;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scmoney.ScMoney;

public interface ScMoneyDao extends BaseDao<ScMoney> {

	List<ScMoney> findByUserIdAndAcceptStatusAndMoneyGreaterThan(String userId, String acceptStatus, Double money);
}
