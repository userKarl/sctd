package com.sc.td.business.entity.scnotify;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.sc.td.common.persistence.BaseEntity;

@Table
@Entity
public class ScNotify extends BaseEntity{

	private String id;//编号
	private String type;//类型
	private String title;//标题
	private String content;//内容
	private String remarks;//备注信息
	private String status;//状态
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
