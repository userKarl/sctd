package com.sc.td.business.entity.scstockgroup;

import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.MappedSuperclass;
import com.sc.td.common.persistence.BaseEntity;

@MappedSuperclass
@IdClass(StockGroupPK.class)
public abstract class ScStockGroup extends BaseEntity {

	private String groupId; // 投资组合ID
	private String stockNo; // 股票代码
	private String exchangeNo; // 交易所代码
	private String commodityType; // 类型
	private Double percent; // 投资比例
	private Integer tradeVol; // 投资数量
	private String direct; // 买卖

	@Id
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Id
	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	@Id
	public String getExchangeNo() {
		return exchangeNo;
	}

	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	@Id
	public String getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}

	public Integer getTradeVol() {
		return tradeVol == null ? 0 : tradeVol;
	}

	public void setTradeVol(Integer tradeVol) {
		this.tradeVol = tradeVol;
	}

	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

}
