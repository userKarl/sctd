package com.sc.td.business.entity.scuppertick;

import java.io.Serializable;

@SuppressWarnings("serial")
public class UpperTickPK implements Serializable {

	private String upperTickCode;
	private String priceFrom;

	public UpperTickPK() {

	}

	public String getUpperTickCode() {
		return upperTickCode;
	}

	public void setUpperTickCode(String upperTickCode) {
		this.upperTickCode = upperTickCode;
	}

	public String getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(String priceFrom) {
		this.priceFrom = priceFrom;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((priceFrom == null) ? 0 : priceFrom.hashCode());
		result = prime * result + ((upperTickCode == null) ? 0 : upperTickCode.hashCode());
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
		UpperTickPK other = (UpperTickPK) obj;
		if (priceFrom == null) {
			if (other.priceFrom != null)
				return false;
		} else if (!priceFrom.equals(other.priceFrom))
			return false;
		if (upperTickCode == null) {
			if (other.upperTickCode != null)
				return false;
		} else if (!upperTickCode.equals(other.upperTickCode))
			return false;
		return true;
	}

}
