package com.sc.td.business.entity.schisgrpstock;

import java.io.Serializable;

public class HisGrpStockPK implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = 3137443147525221353L;

	public HisGrpStockPK() {

	}

	private String groupId;
	private String stockNo;
	private String exchangeNo;
	private String commodityType;
	private String startDate;

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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commodityType == null) ? 0 : commodityType.hashCode());
		result = prime * result + ((exchangeNo == null) ? 0 : exchangeNo.hashCode());
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		HisGrpStockPK other = (HisGrpStockPK) obj;
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
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		if (stockNo == null) {
			if (other.stockNo != null)
				return false;
		} else if (!stockNo.equals(other.stockNo))
			return false;
		return true;
	}
}
