package com.sc.td.business.entity.scfutures;

import java.io.Serializable;

public class FuturesPK implements Serializable {
	/**
	 * 唯一標識
	 */
	private static final long serialVersionUID = 8520440336876406466L;

	public FuturesPK() {

	}

	private String exchangeNo;
	private String code;

	public String getExchangeNo() {
		return exchangeNo;
	}

	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((exchangeNo == null) ? 0 : exchangeNo.hashCode());
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
		FuturesPK other = (FuturesPK) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (exchangeNo == null) {
			if (other.exchangeNo != null)
				return false;
		} else if (!exchangeNo.equals(other.exchangeNo))
			return false;
		return true;
	}
}
