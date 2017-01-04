package com.sc.td.business.dao.scfutures;


import java.util.List;
import org.springframework.data.jpa.repository.Query;
import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scfutures.ScFutures;

public interface ScFuturesDao extends BaseDao<ScFutures> {

	@Query(value="select exchange_no,exchange_name,commodity_no,commodity_name,code,contract_no,contract_name,futures_type"
			+ ",product_dot,upper_tick,reg_date,expiry_date,dot_num,currency_no,currency_name,lower_tick,exchange_no2"
			+ ",deposit,deposit_percent,first_notice_day,create_by,create_date,update_by,update_date,commodity_type,py_name"
			+ " from sc_futures where update_date>?1 order by exchange_no,`code` asc limit ?2,?3",nativeQuery=true)
	List<ScFutures> findByRegDateGreaterThan(String dateTime,int index,int size);
	
	ScFutures findByExchangeNoAndCodeAndCommodityType(String exchangeNo,String code,String commodityType);
	
	@Query(value="select exchange_no,exchange_name,commodity_no,commodity_name,code,contract_no,contract_name,futures_type"
			+ ",product_dot,upper_tick,reg_date,expiry_date,dot_num,currency_no,currency_name,lower_tick,exchange_no2"
			+ ",deposit,deposit_percent,first_notice_day,create_by,create_date,update_by,update_date,commodity_type,py_name"
			+ " from sc_futures where expiry_date>=?1 ",nativeQuery=true)
	List<ScFutures> findByExpiryDateGreaterThan(String expiryDate);
}
