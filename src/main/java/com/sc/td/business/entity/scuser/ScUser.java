
package com.sc.td.business.entity.scuser;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.joda.time.DateTime;

import com.sc.td.common.persistence.BaseEntity;
import com.sc.td.common.utils.datetime.TimeUtil;

/**
 * 注册用户Entity
 */

@Table
@Entity
public class ScUser extends BaseEntity implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = 2205393907497644586L;
	private String userId;
	private String userName; // 用户名
	private String password; // 密码
	private String mobile; // 手机号码
	private Double money; // 虚拟币
	private Double frozenMoney; // 冻结虚拟币
	private String image; // 头像
	private String levelId;//等级
	
	private String validCode; // 验证码
	private String validCodeType; // 验证码类型
	private String levelName;//等级名称
	
	@Transient
	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

	@Id
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Double getFrozenMoney() {
		return frozenMoney;
	}

	public void setFrozenMoney(Double frozenMoney) {
		this.frozenMoney = frozenMoney;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	@Transient
	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	@Transient
	public String getValidCodeType() {
		return validCodeType;
	}

	public void setValidCodeType(String validCodeType) {
		this.validCodeType = validCodeType;
	}

	public String getLevelId() {
		return levelId;
	}

	public void setLevelId(String levelId) {
		this.levelId = levelId;
	}

	public void setInitValue(ScUser t) {
		t.setCreateBy(t.getUserId());
		t.setCreateDate(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
		t.setUpdateBy(t.getUserId());
		t.setUpdateDate(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
	}

}