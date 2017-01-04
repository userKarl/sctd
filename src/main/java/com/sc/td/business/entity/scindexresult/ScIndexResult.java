package com.sc.td.business.entity.scindexresult;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.sc.td.common.persistence.BaseEntity;

@Table
@Entity
@IdClass(IndexResultPk.class)
public class ScIndexResult extends BaseEntity {

	private String exchangeNo;//交易所
	private String commodityNo;//品种
	private String commodityType;//类型
	private String funcIndexId;//指标ID
	private String funcResult;//计算结果
	private String remark;//备注
	
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
	@Id
	public String getCommodityType() {
		return commodityType;
	}
	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}
	@Id
	public String getFuncIndexId() {
		return funcIndexId;
	}
	public void setFuncIndexId(String funcIndexId) {
		this.funcIndexId = funcIndexId;
	}
	public String getFuncResult() {
		return funcResult;
	}
	public void setFuncResult(String funcResult) {
		this.funcResult = funcResult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
