package com.sc.td.business.entity.scstockgroup;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sc.td.business.entity.scfutures.ScFutures;
import com.sc.td.business.entity.scstock.ScStock;

@Table(name = "sc_stock_group")
@Entity
public class StockGroup extends ScStockGroup {

	private Double price;
	private ScStock scStock;
	private ScFutures scFutures;

	@Transient
	public ScStock getScStock() {
		return scStock;
	}

	public void setScStock(ScStock scStock) {
		this.scStock = scStock;
	}

	@Transient
	public ScFutures getScFutures() {
		return scFutures;
	}

	public void setScFutures(ScFutures scFutures) {
		this.scFutures = scFutures;
	}

	@Transient
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

}
