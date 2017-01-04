package com.sc.td.business.entity.scgroup;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * PK组合
 * 
 * @author Administrator
 *
 */
@Table(name = "sc_group")
@Entity
public class ScGroup extends BaseGroup implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = -4041013631725731867L;

	private Double winRate; // 胜率
	private String userName;// 用户名
	private String image;// 头像
	private Double buysellRatio;// 多空比例

	@Transient
	public Double getBuysellRatio() {
		return buysellRatio;
	}

	public void setBuysellRatio(Double buysellRatio) {
		this.buysellRatio = buysellRatio;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Double getWinRate() {
		return winRate;
	}

	public void setWinRate(Double winRate) {
		this.winRate = winRate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

}
