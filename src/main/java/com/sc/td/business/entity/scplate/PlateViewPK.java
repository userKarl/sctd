package com.sc.td.business.entity.scplate;

import java.io.Serializable;

public class PlateViewPK implements Serializable {

	/**
	 * 唯一標識
	 */
	private static final long serialVersionUID = 1936938653530749168L;

	public PlateViewPK() {

	}

	private String plateGroupId;
	private String plateId;
	private String exchangeNo;
	private String commodityNo;

	public String getPlateGroupId() {
		return plateGroupId;
	}

	public void setPlateGroupId(String plateGroupId) {
		this.plateGroupId = plateGroupId;
	}

	public String getPlateId() {
		return plateId;
	}

	public void setPlateId(String plateId) {
		this.plateId = plateId;
	}

	public String getExchangeNo() {
		return exchangeNo;
	}

	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commodityNo == null) ? 0 : commodityNo.hashCode());
		result = prime * result + ((exchangeNo == null) ? 0 : exchangeNo.hashCode());
		result = prime * result + ((plateGroupId == null) ? 0 : plateGroupId.hashCode());
		result = prime * result + ((plateId == null) ? 0 : plateId.hashCode());
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
		PlateViewPK other = (PlateViewPK) obj;
		if (commodityNo == null) {
			if (other.commodityNo != null)
				return false;
		} else if (!commodityNo.equals(other.commodityNo))
			return false;
		if (exchangeNo == null) {
			if (other.exchangeNo != null)
				return false;
		} else if (!exchangeNo.equals(other.exchangeNo))
			return false;
		if (plateGroupId == null) {
			if (other.plateGroupId != null)
				return false;
		} else if (!plateGroupId.equals(other.plateGroupId))
			return false;
		if (plateId == null) {
			if (other.plateId != null)
				return false;
		} else if (!plateId.equals(other.plateId))
			return false;
		return true;
	}

}
