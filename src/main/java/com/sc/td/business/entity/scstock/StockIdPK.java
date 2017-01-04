package com.sc.td.business.entity.scstock;

import java.io.Serializable;

@SuppressWarnings("serial")
public class StockIdPK implements Serializable {

	private String exchangeNo;
	private String stockNo;

	public StockIdPK() {
		super();
	}

	public String getExchangeNo() {
		return exchangeNo;
	}

	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		StockIdPK other = (StockIdPK) obj;
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
