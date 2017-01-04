package com.sc.td.business.entity.scnotify;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Table
@Entity
public class ScNotifyRecord {

	private String id;//编号
	private String userId;//接受人
	private String readFlag;//阅读标记
	private String readDate;//阅读时间
	
	private ScNotify scNotify;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "scNotifyId",unique = true)
	public ScNotify getScNotify() {
		return scNotify;
	}
	public void setScNotify(ScNotify scNotify) {
		this.scNotify = scNotify;
	}
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}
	public String getReadDate() {
		return readDate;
	}
	public void setReadDate(String readDate) {
		this.readDate = readDate;
	}
	
}
