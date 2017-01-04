package com.sc.td.business.entity.scstockgroup;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "sc_stock_group")
@Entity
public class StockGroupF extends ScStockGroup implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = 6515257428194531535L;

	private Integer dotNum; // 小数点位数
	private Integer lowerTick;// 进阶单位
	private Double exchange;// 汇率
	private Double upperTick;// 跳点
	private Double productDot;// 每点价值

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

	public Double getExchange() {
		return exchange;
	}

	public void setExchange(Double exchange) {
		this.exchange = exchange;
	}

	public Integer getLowerTick() {
		return lowerTick == null ? 0 : lowerTick;
	}

	public void setLowerTick(Integer lowerTick) {
		this.lowerTick = lowerTick;
	}

	public Integer getDotNum() {
		return dotNum == null ? 0 : dotNum;
	}

	public void setDotNum(Integer dotNum) {
		this.dotNum = dotNum;
	}
}
