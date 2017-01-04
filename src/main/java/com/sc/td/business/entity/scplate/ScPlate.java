package com.sc.td.business.entity.scplate;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 菜单板块
 * 
 * @author Administrator
 *
 */
@Table
@Entity
public class ScPlate extends BasePlate {

	private List<ScPlate> children; // 获取子菜单
	private String parentName;
	private List<ScPlateProduct> scPlateProductList;

	@Transient
	public List<ScPlateProduct> getScPlateProductList() {
		return scPlateProductList;
	}

	public void setScPlateProductList(List<ScPlateProduct> scPlateProductList) {
		this.scPlateProductList = scPlateProductList;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	@Transient
	public List<ScPlate> getChildren() {
		return children;
	}

	public void setChildren(List<ScPlate> children) {
		this.children = children;
	}

	@JsonIgnore
	public static String getRootId() {
		return "1";
	}

}
