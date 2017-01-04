package com.sc.td.business.entity.scplate;

import java.io.Serializable;

public class PlateProductPK implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = -6618336703208220999L;

	public PlateProductPK() {

	}

	private String id;// 板块的编号
	private String commodity_no;// 商品编号

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCommodity_no() {
		return commodity_no;
	}

	public void setCommodity_no(String commodity_no) {
		this.commodity_no = commodity_no;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commodity_no == null) ? 0 : commodity_no.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		PlateProductPK other = (PlateProductPK) obj;
		if (commodity_no == null) {
			if (other.commodity_no != null)
				return false;
		} else if (!commodity_no.equals(other.commodity_no))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
