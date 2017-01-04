package com.sc.td.business.entity.schisgrpstock;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import com.sc.td.common.persistence.BaseEntity;

@Table
@Entity
@IdClass(HisGrpStockPK.class)
public class ScHisGrpStock extends BaseEntity {

	private String groupId;// 投资组合ID
	private String stockNo;// 股票代码
	private String exchangeNo;// 交易所代码
	private String commodityType;// 类型
	private Double percent;// 投资百分比
	private Integer tradeVol;// 投资数量
	private String direct;// 买卖
	private Double openPrice;// 开仓价格
	private Double closePrice;// 平仓价格
	private String startDate;// 开始时间(yyyyMMdd)

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

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getClosePrice() {
		return closePrice;
	}

	public void setClosePrice(Double closePrice) {
		this.closePrice = closePrice;
	}

	@Id
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
