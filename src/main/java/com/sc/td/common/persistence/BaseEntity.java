package com.sc.td.common.persistence;

import javax.persistence.MappedSuperclass;
import org.joda.time.DateTime;
import com.sc.td.common.utils.StringUtils;
import com.sc.td.common.utils.datetime.TimeUtil;

@MappedSuperclass
public abstract class BaseEntity {

	private String createBy; // 创建者
	private String createDate; // 创建日期
	private String updateBy; // 更新者
	private String updateDate; // 更新日期

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getCreateDate() {
		return (StringUtils.isBlank(createDate)) ? createDate : createDate.replace(".0", "");
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getUpdateDate() {
		return (StringUtils.isBlank(updateDate)) ? updateDate : updateDate.replace(".0", "");
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public void setInitValue(BaseEntity t, String userId) {
		t.setCreateBy(userId);
		t.setCreateDate(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
		t.setUpdateBy(userId);
		t.setUpdateDate(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
	}

	public void setUpdateValue(BaseEntity t, String userId) {
		t.setUpdateBy(userId);
		t.setUpdateDate(TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
	}
}
