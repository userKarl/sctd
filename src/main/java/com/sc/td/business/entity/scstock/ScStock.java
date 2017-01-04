package com.sc.td.business.entity.scstock;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import com.sc.td.common.persistence.BaseEntity;

/**
 * 股票
 * 
 * @author Administrator
 *
 */

@Table
@Entity
@IdClass(StockIdPK.class)
public class ScStock extends BaseEntity {

	private String exchangeNo; // 交易所代码
	private String stockNo; // 股票代码
	private String exchangeName; // 交易所名称
	private String stockName; // 股票名称
	private String stockType; // 股票类型
	private String regDate; // 更新时间
	private String currencyNo; // 货币
	private String currencyName; // 货币名称
	private Integer lotSize; // 每手股数
	private String mortgagePercent; // 按揭百分比
	private String upperTickCode; // 跳点编号
	private String commodityType;// 内外盘标志
	private Double conversionRatio;// 行使比率
	private Double strickPrice;// 行使价
	private String maturityDate;// 到期日
	private String callPutFlag;// 认沽认购标识
	private Double callPrice;// 回收价
	private String pyName;// 拼音名字

	@Id
	public String getExchangeNo() {
		return exchangeNo;
	}

	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	@Id
	public String getStockNo() {
		return stockNo;
	}

	public void setStockNo(String stockNo) {
		this.stockNo = stockNo;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getStockType() {
		return stockType;
	}

	public void setStockType(String stockType) {
		this.stockType = stockType;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getCurrencyNo() {
		return currencyNo;
	}

	public void setCurrencyNo(String currencyNo) {
		this.currencyNo = currencyNo;
	}

	public String getCurrencyName() {
		return currencyName;
	}

	public void setCurrencyName(String currencyName) {
		this.currencyName = currencyName;
	}

	public Integer getLotSize() {
		return lotSize == null ? 0 : lotSize;
	}

	public void setLotSize(Integer lotSize) {
		this.lotSize = lotSize;
	}

	public String getMortgagePercent() {
		return mortgagePercent;
	}

	public void setMortgagePercent(String mortgagePercent) {
		this.mortgagePercent = mortgagePercent;
	}

	public String getUpperTickCode() {
		return upperTickCode;
	}

	public void setUpperTickCode(String upperTickCode) {
		this.upperTickCode = upperTickCode;
	}

	public String getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}

	public Double getConversionRatio() {
		return conversionRatio;
	}

	public void setConversionRatio(Double conversionRatio) {
		this.conversionRatio = conversionRatio;
	}

	public Double getStrickPrice() {
		return strickPrice;
	}

	public void setStrickPrice(Double strickPrice) {
		this.strickPrice = strickPrice;
	}

	public String getMaturityDate() {
		return maturityDate;
	}

	public void setMaturityDate(String maturityDate) {
		this.maturityDate = maturityDate;
	}

	public String getCallPutFlag() {
		return callPutFlag;
	}

	public void setCallPutFlag(String callPutFlag) {
		this.callPutFlag = callPutFlag;
	}

	public Double getCallPrice() {
		return callPrice;
	}

	public void setCallPrice(Double callPrice) {
		this.callPrice = callPrice;
	}

	public String getPyName() {
		return pyName;
	}

	public void setPyName(String pyName) {
		this.pyName = pyName;
	}

}
