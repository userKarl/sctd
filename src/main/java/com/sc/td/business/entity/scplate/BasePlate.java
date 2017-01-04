
package com.sc.td.business.entity.scplate;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.sc.td.common.persistence.BaseEntity;

@MappedSuperclass
public abstract class BasePlate extends BaseEntity {

	private String id;
	private String parentId;
	private String parentIds; // 所有父级编号
	private String name; // 名称
	private Integer sort; // 排序
	private String isShow;// 是否显示
	private String delFlag;// 删除标记
	private String remarks;// 备注信息

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort == null ? 0 : sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}