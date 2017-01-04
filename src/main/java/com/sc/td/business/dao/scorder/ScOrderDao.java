package com.sc.td.business.dao.scorder;

import org.springframework.data.jpa.repository.Query;

import com.sc.td.business.base.BaseDao;
import com.sc.td.business.entity.scorder.ScOrder;

public interface ScOrderDao extends BaseDao<ScOrder>{

	@Query(value="select out_trade_no,seller_id,app_id,trade_no,user_id,trade_status,total_amount,receipt_amount,buyer_pay_amount,refund_fee,"
			+ "subject,body,gmt_create,gmt_payment,gmt_refund,gmt_close,create_by,create_date,update_by,update_date"
			+ " from sc_order where out_trade_no=?1",nativeQuery=true)
	ScOrder findByOutTradeNo(String out_trade_no);
}
