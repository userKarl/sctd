package com.sc.td.business.entity.scstockgroup;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "sc_stock_group")
@Entity
public class StockGroupS extends ScStockGroup implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = 2580132740900830601L;

	private String upperTickCode; // 股票时跳点编码，以此确定小数位
	private Double exchange;// 汇率

	public Double getExchange() {
		return exchange;
	}

	public void setExchange(Double exchange) {
		this.exchange = exchange;
	}

	public String getUpperTickCode() {
		return upperTickCode;
	}

	public void setUpperTickCode(String upperTickCode) {
		this.upperTickCode = upperTickCode;
	}

}
