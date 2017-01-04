package com.sc.td.business.entity.screcommend;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ReCommendIdPK implements Serializable {

	private String stockNo; // 股票名称
	private String exchangeNo; // 交易所代码
	private String commodityType;// 类型

	public ReCommendIdPK() {
		super();
	}

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getExchangeNo() {
		return exchangeNo;
	}

	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	public String getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commodityType == null) ? 0 : commodityType.hashCode());
		result = prime * result + ((exchangeNo == null) ? 0 : exchangeNo.hashCode());
		result = prime * result + ((stockNo == null) ? 0 : stockNo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReCommendIdPK other = (ReCommendIdPK) obj;
		if (commodityType == null) {
			if (other.commodityType != null)
				return false;
		} else if (!commodityType.equals(other.commodityType))
			return false;
		if (exchangeNo == null) {
			if (other.exchangeNo != null)
				return false;
		} else if (!exchangeNo.equals(other.exchangeNo))
			return false;
		if (stockNo == null) {
			if (other.stockNo != null)
				return false;
		} else if (!stockNo.equals(other.stockNo))
			return false;
		return true;
	}

}
