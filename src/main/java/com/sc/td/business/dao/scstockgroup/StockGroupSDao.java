package com.sc.td.business.dao.scstockgroup;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scstockgroup.StockGroupS;

public interface StockGroupSDao extends BaseDao<StockGroupS> {

	@Query(value = "select group_id,stock_no,exchange_no,commodity_type,upper_tick_code,exchange,"
			+ "percent,trade_vol,direct,create_by,create_date,update_by,update_date "
			+ "from v_stockgroup_stock where group_id=?1", nativeQuery = true)
	List<StockGroupS> findStockByGroupId(String groupId);
}
