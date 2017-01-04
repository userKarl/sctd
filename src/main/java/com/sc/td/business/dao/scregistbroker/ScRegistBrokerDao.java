package com.sc.td.business.dao.scregistbroker;

import java.util.List;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scregistbroker.ScRegistBroker;

public interface ScRegistBrokerDao extends BaseDao<ScRegistBroker> {

	List<ScRegistBroker> findByBrokerType(String brokerType);
}
