package com.sc.td.business.entity.scindexresult;

import java.io.Serializable;

public class HisIndexResultPk implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = -5196291772915917679L;

	public HisIndexResultPk() {

	}

	private String exchangeNo;// 交易所
	private String commodityNo;// 品种
	private String commodityType;// 类型
	private String funcIndexId;// 指标ID

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

	public String getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}

	public String getFuncIndexId() {
		return funcIndexId;
	}

	public void setFuncIndexId(String funcIndexId) {
		this.funcIndexId = funcIndexId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commodityNo == null) ? 0 : commodityNo.hashCode());
		result = prime * result + ((commodityType == null) ? 0 : commodityType.hashCode());
		result = prime * result + ((exchangeNo == null) ? 0 : exchangeNo.hashCode());
		result = prime * result + ((funcIndexId == null) ? 0 : funcIndexId.hashCode());
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
		HisIndexResultPk other = (HisIndexResultPk) obj;
		if (commodityNo == null) {
			if (other.commodityNo != null)
				return false;
		} else if (!commodityNo.equals(other.commodityNo))
			return false;
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
		if (funcIndexId == null) {
			if (other.funcIndexId != null)
				return false;
		} else if (!funcIndexId.equals(other.funcIndexId))
			return false;
		return true;
	}
}
