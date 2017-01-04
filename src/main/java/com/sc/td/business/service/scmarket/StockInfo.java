package com.sc.td.business.service.scmarket;

import java.util.List;

public class StockInfo {
	private String commodityType;
	private String stockNo;
	private List<StockInfo> stockInfoList;
	public List<StockInfo> getStockInfoList() {
		return stockInfoList;
	}
	public void setStockInfoList(List<StockInfo> stockInfoList) {
		this.stockInfoList = stockInfoList;
	}
	public String getCommodityType() {
		return commodityType;
	}
	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}
	public String getStockNo() {
		return stockNo;
	}
	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}
	
}
