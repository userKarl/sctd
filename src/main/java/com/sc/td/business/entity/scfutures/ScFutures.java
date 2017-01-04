package com.sc.td.business.entity.scfutures;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.sc.td.common.persistence.BaseEntity;

@Table
@Entity
@IdClass(FuturesPK.class)
public class ScFutures extends BaseEntity {

	private String exchangeNo;// 交易所
	private String exchangeName;// 交易所名
	private String commodityNo;// 商品代码
	private String commodityName;// 商品名
	private String code;// 完整的期货合约代码
	private String contractNo;// 合约代码（YYMM）
	private String contractName;// 合约名称
	private String futuresType;// 商品类型
	private Double productDot;// 每点价值
	private Double upperTick;// 跳点
	private String regDate;// 更新日期
	private String expiryDate;// 最后交易日
	private Integer dotNum; // 小数点位数
	private String currencyNo;// 货币
	private String currencyName;// 货币名
	private Integer lowerTick;// 进阶单位
	private String exchangeNo2;// 备用交易所
	private Double deposit;// 保证金
	private Double depositPercent;// 保证金百分比
	private String firstNoticeDay;// 首次通知日
	private String commodityType;// 内外盘标志
	private String pyName;// 拼音名字

	@Id
	public String getExchangeNo() {
		return exchangeNo;
	}

	public void setExchangeNo(String exchangeNo) {
		this.exchangeNo = exchangeNo;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getCommodityNo() {
		return commodityNo;
	}

	public void setCommodityNo(String commodityNo) {
		this.commodityNo = commodityNo;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	@Id
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getContractNo() {
		return contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
	}

	public String getFuturesType() {
		return futuresType;
	}

	public void setFuturesType(String futuresType) {
		this.futuresType = futuresType;
	}

	public Double getProductDot() {
		return productDot;
	}

	public void setProductDot(Double productDot) {
		this.productDot = productDot;
	}

	public Double getUpperTick() {
		return upperTick;
	}

	public void setUpperTick(Double upperTick) {
		this.upperTick = upperTick;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getDotNum() {
		return dotNum == null ? 0 : dotNum;
	}

	public void setDotNum(Integer dotNum) {
		this.dotNum = dotNum;
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

	public Integer getLowerTick() {
		return lowerTick == null ? 0 : lowerTick;
	}

	public void setLowerTick(Integer lowerTick) {
		this.lowerTick = lowerTick;
	}

	public String getExchangeNo2() {
		return exchangeNo2;
	}

	public void setExchangeNo2(String exchangeNo2) {
		this.exchangeNo2 = exchangeNo2;
	}

	public Double getDeposit() {
		return deposit;
	}

	public void setDeposit(Double deposit) {
		this.deposit = deposit;
	}

	public Double getDepositPercent() {
		return depositPercent;
	}

	public void setDepositPercent(Double depositPercent) {
		this.depositPercent = depositPercent;
	}

	public String getFirstNoticeDay() {
		return firstNoticeDay;
	}

	public void setFirstNoticeDay(String firstNoticeDay) {
		this.firstNoticeDay = firstNoticeDay;
	}

	public String getCommodityType() {
		return commodityType;
	}

	public void setCommodityType(String commodityType) {
		this.commodityType = commodityType;
	}

	public String getPyName() {
		return pyName;
	}

	public void setPyName(String pyName) {
		this.pyName = pyName;
	}

}
