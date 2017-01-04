package com.sc.td.business.entity.scright;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.sc.td.common.persistence.BaseEntity;

/**
 * 用户权限
 * 
 * @author Administrator
 *
 */

@Table
@Entity
@IdClass(RightIdPK.class)
public class ScRight extends BaseEntity implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = -3535310294658676936L;
	private String userId; // 用户ID
	private String rightId; // 权限
	private String isEnable; // 是否有效
	private String endDate; // 过期时间

	@Id
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Id
	public String getRightId() {
		return rightId;
	}

	public void setRightId(String rightId) {
		this.rightId = rightId;
	}

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

}
