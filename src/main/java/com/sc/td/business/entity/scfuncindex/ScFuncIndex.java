package com.sc.td.business.entity.scfuncindex;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sc.td.common.persistence.BaseEntity;

@Table
@Entity
public class ScFuncIndex extends BaseEntity {

	private String funcIndexId;// 函数ID
	private String funcIndexName;// 函数名
	private String remark;// 备注
	private String funcParam;// 参数
	private String funcParamName;// 参数说明
	private String calcRange;// 计算范围
	private String moduleName;// 模块名
	private String funcCategory;// 指标分类
	private String isShow;// 是否显示
	private String iconPath;// 图标路径
	private String delFlag;// 删除标记

	private String categoryName;//类别名称

	@Id
	public String getFuncIndexId() {
		return funcIndexId;
	}

	public void setFuncIndexId(String funcIndexId) {
		this.funcIndexId = funcIndexId;
	}

	public String getFuncIndexName() {
		return funcIndexName;
	}

	public void setFuncIndexName(String funcIndexName) {
		this.funcIndexName = funcIndexName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getFuncParam() {
		return funcParam;
	}

	public void setFuncParam(String funcParam) {
		this.funcParam = funcParam;
	}

	public String getFuncParamName() {
		return funcParamName;
	}

	public void setFuncParamName(String funcParamName) {
		this.funcParamName = funcParamName;
	}

	public String getCalcRange() {
		return calcRange;
	}

	public void setCalcRange(String calcRange) {
		this.calcRange = calcRange;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getFuncCategory() {
		return funcCategory;
	}

	public void setFuncCategory(String funcCategory) {
		this.funcCategory = funcCategory;
	}

	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
}
