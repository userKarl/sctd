package com.sc.td.business.entity.scpklevel;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sc.td.common.persistence.BaseEntity;

@Table
@Entity
public class ScPklevel extends BaseEntity{

	private String levelId;
	private String levelName;
	private String sort;
	private Double money;
	private String remark;
	
	@Id
	public String getLevelId() {
		return levelId;
	}
	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
