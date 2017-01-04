package com.sc.td.business.dao.screcommend;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.screcommend.ScRecommend;

public interface ScRecommendDao extends BaseDao<ScRecommend> {

	@Query(value = "select t1.stock_no,t2.stock_name,t3.contract_name,t1.exchange_no,t1.commodity_type,t1.start_date,"
			+ "t1.create_by,t1.create_date,t1.update_by,t1.update_date,t1.reason,t1.is_vip,IFNULL(t3.dot_num,0) as dot_num,t2.upper_tick_code,"
			+ "IFNULL(t3.lower_tick,0) as lower_tick "
			+ "from sc_recommend t1 left join sc_stock t2 on t1.stock_no=t2.stock_no "
			+ "and t1.exchange_no=t2.exchange_no  and t1.commodity_type=t2.commodity_type "
			+ "left join sc_futures t3 on t1.stock_no=t3.`code` and t1.commodity_type=t3.commodity_type "
			+ "and t1.exchange_no=t3.exchange_no where t1.is_vip<>?1 order by t1.update_date DESC limit ?2,?3 ", nativeQuery = true)
	List<ScRecommend> findByStockNoAndExchangeNo(String isVip,int index, int size);

	@Query(value = "select t1.stock_no,t2.stock_name,t3.contract_name,t1.exchange_no,t1.commodity_type,t1.start_date,"
			+ "t1.create_by,t1.create_date,t1.update_by,t1.update_date,t1.reason,t1.is_vip,IFNULL(t3.dot_num,0) as dot_num,t2.upper_tick_code,"
			+ "IFNULL(t3.lower_tick,0) as lower_tick "
			+ "from sc_recommend t1 left join sc_stock t2 on t1.stock_no=t2.stock_no "
			+ "and t1.exchange_no=t2.exchange_no  and t1.commodity_type=t2.commodity_type "
			+ "left join sc_futures t3 on t1.stock_no=t3.`code` and t1.commodity_type=t3.commodity_type "
			+ "and t1.exchange_no=t3.exchange_no where t1.is_vip=?1 order by t1.update_date DESC", nativeQuery = true)
	List<ScRecommend> nonVip(String isVip);
	
	@Query(value = "select t1.stock_no,t2.stock_name,t3.contract_name,t1.exchange_no,t1.commodity_type,t1.start_date,"
			+ "t1.create_by,t1.create_date,t1.update_by,t1.update_date,t1.reason,t1.is_vip,IFNULL(t3.dot_num,0) as dot_num,t2.upper_tick_code,"
			+ "IFNULL(t3.lower_tick,0) as lower_tick "
			+ "from sc_recommend t1 left join sc_stock t2 on t1.stock_no=t2.stock_no "
			+ "and t1.exchange_no=t2.exchange_no  and t1.commodity_type=t2.commodity_type "
			+ "left join sc_futures t3 on t1.stock_no=t3.`code` and t1.commodity_type=t3.commodity_type "
			+ "and t1.exchange_no=t3.exchange_no where t1.is_vip=?1 order by t1.update_date DESC", nativeQuery = true)
	List<ScRecommend> findByIsVip(String isVip);
	
	@Query(value = "select t1.stock_no,t2.stock_name,t3.contract_name,t1.exchange_no,t1.commodity_type,t1.start_date,"
			+ "t1.create_by,t1.create_date,t1.update_by,t1.update_date,t1.reason,t1.is_vip,IFNULL(t3.dot_num,0) as dot_num,t2.upper_tick_code,"
			+ "IFNULL(t3.lower_tick,0) as lower_tick "
			+ "from sc_recommend t1 left join sc_stock t2 on t1.stock_no=t2.stock_no "
			+ "and t1.exchange_no=t2.exchange_no  and t1.commodity_type=t2.commodity_type "
			+ "left join sc_futures t3 on t1.stock_no=t3.`code` and t1.commodity_type=t3.commodity_type "
			+ "and t1.exchange_no=t3.exchange_no where t1.is_vip=?1 order by t1.is_vip ASC limit ?2,?3", nativeQuery = true)
	List<ScRecommend> findNonVipDataOrderByIsVipASC(String isVip,int index, int size);
	
	@Query(value = "select t1.stock_no,t2.stock_name,t3.contract_name,t1.exchange_no,t1.commodity_type,t1.start_date,"
			+ "t1.create_by,t1.create_date,t1.update_by,t1.update_date,t1.reason,t1.is_vip,IFNULL(t3.dot_num,0) as dot_num,t2.upper_tick_code,"
			+ "IFNULL(t3.lower_tick,0) as lower_tick "
			+ "from sc_recommend t1 left join sc_stock t2 on t1.stock_no=t2.stock_no "
			+ "and t1.exchange_no=t2.exchange_no  and t1.commodity_type=t2.commodity_type "
			+ "left join sc_futures t3 on t1.stock_no=t3.`code` and t1.commodity_type=t3.commodity_type "
			+ "and t1.exchange_no=t3.exchange_no order by t1.is_vip ASC limit ?1,?2", nativeQuery = true)
	List<ScRecommend> findDataOrderByIsVipASC(int index, int size);
}
