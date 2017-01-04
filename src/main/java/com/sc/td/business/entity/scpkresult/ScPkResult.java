package com.sc.td.business.entity.scpkresult;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sc.td.business.entity.scgroup.Group;
import com.sc.td.common.persistence.BaseEntity;

@Table
@Entity
@IdClass(PkResultPk.class)
public class ScPkResult extends BaseEntity {

	private String applyGroupId;// PK发起方
	private String acceptGroupId;// PK接受方
	private String pkResult;// PK结果
	private String startDate;// 开始日期
	private String endDate;// 结束日期
	private Double money;// 赌注
	private String pkStatus;// PK状态

	private Group group;// PK战队
	private Group hisgroup;// 自身战队历史信息

	@Transient
	public Group getHisgroup() {
		return hisgroup;
	}

	public void setHisgroup(Group hisgroup) {
		this.hisgroup = hisgroup;
	}

	@Transient
	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Id
	public String getApplyGroupId() {
		return applyGroupId;
	}

	public void setApplyGroupId(String applyGroupId) {
		this.applyGroupId = applyGroupId;
	}

	@Id
	public String getAcceptGroupId() {
		return acceptGroupId;
	}

	public void setAcceptGroupId(String acceptGroupId) {
		this.acceptGroupId = acceptGroupId;
	}

	public String getPkResult() {
		return pkResult;
	}

	public void setPkResult(String pkResult) {
		this.pkResult = pkResult;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getPkStatus() {
		return pkStatus;
	}

	public void setPkStatus(String pkStatus) {
		this.pkStatus = pkStatus;
	}

}
