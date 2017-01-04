package com.sc.td.business.entity.scpromotion;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sc.td.common.persistence.BaseEntity;

@Table
@Entity
public class ScPromotion extends BaseEntity{

	private String id;
	private String promotionUrl;
	private String promotionName;
	private String promotionRemark;
	private String promotionImg;
	private String isShow;
	
	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPromotionUrl() {
		return promotionUrl;
	}
	public void setPromotionUrl(String promotionUrl) {
		this.promotionUrl = promotionUrl;
	}
	public String getPromotionName() {
		return promotionName;
	}
	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}
	public String getPromotionRemark() {
		return promotionRemark;
	}
	public void setPromotionRemark(String promotionRemark) {
		this.promotionRemark = promotionRemark;
	}
	public String getPromotionImg() {
		return promotionImg;
	}
	public void setPromotionImg(String promotionImg) {
		this.promotionImg = promotionImg;
	}
	public String getIsShow() {
		return isShow;
	}
	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}
	
}
