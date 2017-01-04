package com.sc.td.business.entity.scgroup;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import com.sc.td.common.persistence.BaseEntity;

@MappedSuperclass
public abstract class BaseGroup extends BaseEntity {

	private String groupId; // 股票组合
	private String groupName; // 股票组合名
	private String groupType; // 组合类型
	private String userId;// 用户ID
	private Integer pkTotal; // 总比赛场次
	private Integer pkWin; // 胜场数
	private Integer pkRowWin; // 连胜场数
	private Integer acceptPkTimes; // 当天接受PK场次
	private String isAllowPk;// 是否允许PK

	@Id
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getPkTotal() {
		return pkTotal == null ? 0 : pkTotal;
	}

	public void setPkTotal(Integer pkTotal) {
		this.pkTotal = pkTotal;
	}

	public Integer getPkWin() {
		return pkWin == null ? 0 : pkWin;
	}

	public void setPkWin(Integer pkWin) {
		this.pkWin = pkWin;
	}

	public Integer getPkRowWin() {
		return pkRowWin == null ? 0 : pkRowWin;
	}

	public void setPkRowWin(Integer pkRowWin) {
		this.pkRowWin = pkRowWin;
	}

	public Integer getAcceptPkTimes() {
		return acceptPkTimes == null ? 0 : acceptPkTimes;
	}

	public void setAcceptPkTimes(Integer acceptPkTimes) {
		this.acceptPkTimes = acceptPkTimes;
	}

	public String getIsAllowPk() {
		return isAllowPk;
	}

	public void setIsAllowPk(String isAllowPk) {
		this.isAllowPk = isAllowPk;
	}

}
