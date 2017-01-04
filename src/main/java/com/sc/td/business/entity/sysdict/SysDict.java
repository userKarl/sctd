package com.sc.td.business.entity.sysdict;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.sc.td.common.persistence.BaseEntity;

/**
 * 数据字典
 * 
 * @author Administrator
 *
 */
@Table
@Entity
public class SysDict extends BaseEntity implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = 4929923561403672494L;
	private String id; // 编号
	private String value; // 数据值
	private String label; // 标签名
	private String type; // 类型
	private String description; // 描述
	private Long sort; // 排序
	private String parentId; // 父级编号
	private String remarks; // 备注
	private String delFlag; // 删除标记

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getSort() {
		return sort == null ? 0 : sort;
	}

	public void setSort(Long sort) {
		this.sort = sort;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

}
