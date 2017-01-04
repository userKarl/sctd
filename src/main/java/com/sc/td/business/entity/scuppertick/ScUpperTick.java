package com.sc.td.business.entity.scuppertick;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import com.sc.td.common.persistence.BaseEntity;

/**
 * 跳点信息
 * 
 * @author Administrator
 *
 */

@Table
@Entity
@IdClass(UpperTickPK.class)
public class ScUpperTick extends BaseEntity implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = 6509703915796523186L;
	private String upperTickCode; // 跳点编号
	private String priceFrom; // 价格区间
	private Double upperTick; // 跳点
	private Double productDot; // 每点价值
	private Integer dotNum; // 小数点位数
	private Integer lowerTick; // 进阶单位

	@Id
	public String getUpperTickCode() {
		return upperTickCode;
	}

	public void setUpperTickCode(String upperTickCode) {
		this.upperTickCode = upperTickCode;
	}

	@Id
	public String getPriceFrom() {
		return priceFrom;
	}

	public void setPriceFrom(String priceFrom) {
		this.priceFrom = priceFrom;
	}

	public Double getUpperTick() {
		return upperTick;
	}

	public void setUpperTick(Double upperTick) {
		this.upperTick = upperTick;
	}

	public Double getProductDot() {
		return productDot;
	}

	public void setProductDot(Double productDot) {
		this.productDot = productDot;
	}

	public Integer getDotNum() {
		return dotNum == null ? 0 : dotNum;
	}

	public void setDotNum(Integer dotNum) {
		this.dotNum = dotNum;
	}

	public Integer getLowerTick() {
		return lowerTick == null ? 0 : lowerTick;
	}

	public void setLowerTick(Integer lowerTick) {
		this.lowerTick = lowerTick;
	}

}
