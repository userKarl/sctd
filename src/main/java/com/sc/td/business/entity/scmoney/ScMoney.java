package com.sc.td.business.entity.scmoney;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sc.td.common.persistence.BaseEntity;

/**
 * 出入金
 * 
 * @author Administrator
 *
 */
@Table
@Entity
public class ScMoney extends BaseEntity implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = 7509856084467688340L;
	private String seqNo; // 主键编码
	private String userId; // 用户ID
	private Double money; // 出入金
	private String applyTime; // 申请时间
	private String acceptTime; // 审核时间
	private String applyUserName; // 申请人
	private String acceptUserName; // 审核人
	private String acceptStatus; // 审核状态
	private String remark; // 备注

	@Id
	public String getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getApplyTime() {
		return applyTime;
	}

	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}

	public String getAcceptTime() {
		return acceptTime;
	}

	public void setAcceptTime(String acceptTime) {
		this.acceptTime = acceptTime;
	}

	public String getApplyUserName() {
		return applyUserName;
	}

	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}

	public String getAcceptUserName() {
		return acceptUserName;
	}

	public void setAcceptUserName(String acceptUserName) {
		this.acceptUserName = acceptUserName;
	}

	public String getAcceptStatus() {
		return acceptStatus;
	}

	public void setAcceptStatus(String acceptStatus) {
		this.acceptStatus = acceptStatus;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
