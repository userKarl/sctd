package com.sc.td.business.entity.scpkresult;

import java.io.Serializable;

public class PkResultPk implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = 1542885211365384L;

	public PkResultPk() {

	}

	private String applyGroupId;
	private String acceptGroupId;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acceptGroupId == null) ? 0 : acceptGroupId.hashCode());
		result = prime * result + ((applyGroupId == null) ? 0 : applyGroupId.hashCode());
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
		PkResultPk other = (PkResultPk) obj;
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
		return true;
	}

}
