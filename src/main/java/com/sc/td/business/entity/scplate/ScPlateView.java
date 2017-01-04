package com.sc.td.business.entity.scplate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.sc.td.common.persistence.BaseEntity;

@Table
@Entity
@IdClass(PlateViewPK.class)
public class ScPlateView extends BaseEntity {

	private String plateGroupId;// 组合板块ID
	private String plateGroupName;// 组合板块名稱
	private String plateId;// 基础板块id
	private String plateName;// 基础板块名
	private String exchangeNo;// 交易所
	private String commodityNo;// 商品
	private String commodityType;// 商品类型
	private Integer groupSortId;// 组合板块排序
	private Integer sortId;// 基础板块排序

	@Id
	public String getPlateGroupId() {
		return plateGroupId;
	}

	public void setPlateGroupId(String plateGroupId) {
		this.plateGroupId = plateGroupId;
	}

	public String getPlateGroupName() {
		return plateGroupName;
	}

	public void setPlateGroupName(String plateGroupName) {
		this.plateGroupName = plateGroupName;
	}

	@Id
	public String getPlateId() {
		return plateId;
	}

	public void setPlateId(String plateId) {
		this.plateId = plateId;
	}

	public String getPlateName() {
		return plateName;
	}

	public void setPlateName(String plateName) {
		this.plateName = plateName;
	}

	@Id
	public String getExchangeNo() {
		return exchangeNo;
	}

	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	@Id
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

	public Integer getGroupSortId() {
		return groupSortId == null ? 0 : groupSortId;
	}

	public void setGroupSortId(Integer groupSortId) {
		this.groupSortId = groupSortId;
	}

	public Integer getSortId() {
		return sortId == null ? 0 : sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

}
