package com.sc.td.business.entity.scprice;

import java.util.List;

public class ScPrice {

	private String commodityType;
	private String stockNo;
	private Double price;
	private List<ScPrice> scPriceList;

	public List<ScPrice> getScPriceList() {
		return scPriceList;
	}

	public void setScPriceList(List<ScPrice> scPriceList) {
		this.scPriceList = scPriceList;
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

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
