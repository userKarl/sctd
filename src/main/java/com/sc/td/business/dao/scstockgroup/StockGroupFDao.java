package com.sc.td.business.dao.scstockgroup;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scstockgroup.StockGroupF;

public interface StockGroupFDao extends BaseDao<StockGroupF> {

	@Query(value = "select group_id,stock_no,exchange_no,commodity_type,dot_num,exchange,lower_tick,upper_tick,product_dot,"
			+ "percent,trade_vol,direct,create_by,create_date,update_by,update_date "
			+ "from v_stockgroup_future where group_id=?1", nativeQuery = true)
	List<StockGroupF> findFutureByGroupId(String groupId);

}
