package com.sc.td.business.entity.sccurrency;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sc.td.common.persistence.BaseEntity;

@Table
@Entity
public class ScCurrency extends BaseEntity {

	private String currency;// 货币
	private String currencyName;// 货币名字
	private String isPrimary;// 是否基币
	private Double exchange;// 汇率

	@Id
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public String getIsPrimary() {
		return isPrimary;
	}

	public void setIsPrimary(String isPrimary) {
		this.isPrimary = isPrimary;
	}

	public Double getExchange() {
		return exchange;
	}

	public void setExchange(Double exchange) {
		this.exchange = exchange;
	}

}
