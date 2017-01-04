package com.sc.td.business.entity.scpkresult;

import java.io.Serializable;

public class HisPkResultPK implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = -1261955491853377218L;

	public HisPkResultPK() {

	}

	private String applyGroupId;// PK发起方
	private String acceptGroupId;// PK接受方
	private String startDate;// 开始日期

	public String getApplyGroupId() {
		return applyGroupId;
	}

	public void setApplyGroupId(String applyGroupId) {
		this.applyGroupId = applyGroupId;
	}

	public String getAcceptGroupId() {
		return acceptGroupId;
	}

	public void setAcceptGroupId(String acceptGroupId) {
		this.acceptGroupId = acceptGroupId;
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
		result = prime * result + ((acceptGroupId == null) ? 0 : acceptGroupId.hashCode());
		result = prime * result + ((applyGroupId == null) ? 0 : applyGroupId.hashCode());
		result = prime * result + ((startDate == null) ? 0 : startDate.hashCode());
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
		HisPkResultPK other = (HisPkResultPK) obj;
		if (acceptGroupId == null) {
			if (other.acceptGroupId != null)
				return false;
		} else if (!acceptGroupId.equals(other.acceptGroupId))
			return false;
		if (applyGroupId == null) {
			if (other.applyGroupId != null)
				return false;
		} else if (!applyGroupId.equals(other.applyGroupId))
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}
}
