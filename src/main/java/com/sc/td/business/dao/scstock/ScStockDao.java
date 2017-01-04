package com.sc.td.business.dao.scstock;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scstock.ScStock;

public interface ScStockDao extends BaseDao<ScStock> {

	Page<ScStock> findByUpdateDateGreaterThan(String updateDate, Pageable pageable);

	ScStock findByExchangeNoAndStockNoAndCommodityType(String exchangeNo, String stockNo, String commodityType);
}
