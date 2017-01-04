package com.sc.td.business.entity.scplate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.sc.td.common.persistence.BaseEntity;

/**
 * 板块所属商品
 * 
 * @author Administrator
 *
 */
@Table
@Entity
@IdClass(PlateProductPK.class)
public class ScPlateProduct extends BaseEntity {

	private String id;// 板块的编号
	private String commodity_no;// 商品编号
	private String commodity_type;// 商品类型
	private Integer sort; // 排序
	private String isShow;// 是否显示
	private String delFlag;// 删除标记
	private String remarks;// 备注

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Id
	public String getCommodity_no() {
		return commodity_no;
	}

	public void setCommodity_no(String commodity_no) {
		this.commodity_no = commodity_no;
	}

	public String getCommodity_type() {
		return commodity_type;
	}

	public void setCommodity_type(String commodity_type) {
		this.commodity_type = commodity_type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
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

}
