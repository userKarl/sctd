package com.sc.td.business.entity.scregistbroker;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sc.td.common.persistence.BaseEntity;

@Table
@Entity
public class ScRegistBroker extends BaseEntity{

	private String id;
	private String brokerType;//经纪商类型
	private String brokerName;//经纪商名字
	private String brokerUrl;//经纪商开户URL
	private String brokerImg;//经纪商图标
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getBrokerType() {
		return brokerType;
	}
	public void setBrokerType(String brokerType) {
		this.brokerType = brokerType;
	}
	public String getBrokerName() {
		return brokerName;
	}
	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}
	public String getBrokerUrl() {
		return brokerUrl;
	}
	public void setBrokerUrl(String brokerUrl) {
		this.brokerUrl = brokerUrl;
	}
	public String getBrokerImg() {
		return brokerImg;
	}
	public void setBrokerImg(String brokerImg) {
		this.brokerImg = brokerImg;
	}
	
}
