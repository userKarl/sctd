package com.sc.td.business.entity.scstockgroup;

import java.io.Serializable;

public class StockGroupPK implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = -2128684749069561057L;

	public StockGroupPK() {

	}

	private String groupId; // 投资组合ID
	private String stockNo; // 股票代码
	private String exchangeNo; // 交易所代码
	private String commodityType; // 类型

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
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
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
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
		StockGroupPK other = (StockGroupPK) obj;
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
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (stockNo == null) {
			if (other.stockNo != null)
				return false;
		} else if (!stockNo.equals(other.stockNo))
			return false;
		return true;
	}

}
