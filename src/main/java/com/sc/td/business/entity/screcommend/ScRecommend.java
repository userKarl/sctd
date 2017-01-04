package com.sc.td.business.entity.screcommend;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Transient;
import com.sc.td.common.persistence.BaseEntity;

/**
 * 今日代码
 * 
 * @author Administrator
 *
 */

@Table
@Entity
@IdClass(ReCommendIdPK.class)
public class ScRecommend extends BaseEntity implements Serializable {

	/**
	 * 唯一标识
	 */
	private static final long serialVersionUID = -4773945554629623390L;
	private String stockNo; // 股票名称
	private String exchangeNo; // 交易所代码
	private String commodityType;// 类型
	private String startDate; // 开始时间
	private String reason; // 理由
	private String isVip; // 是否VIP

	private String stockName; // 股票名
	private String contractName; // 期货合约名
	private Integer dotNum; // 小数点位数
	private Integer lowerTick; // 期货时进阶单位
	private String upperTickCode; // 股票时跳点编码，以此确定小数位

	private Double price; // 价格
	private String uad; // 涨跌
	private String dspName;

	public Integer getLowerTick() {
		return lowerTick == null ? 0 : lowerTick;
	}

	public void setLowerTick(Integer lowerTick) {
		this.lowerTick = lowerTick;
	}

	public String getUpperTickCode() {
		return upperTickCode;
	}

	public void setUpperTickCode(String upperTickCode) {
		this.upperTickCode = upperTickCode;
	}

	public Integer getDotNum() {
		return dotNum == null ? 0 : dotNum;
	}

	public void setDotNum(Integer dotNum) {
		this.dotNum = dotNum;
	}

	@Transient
	public String getDspName() {
		return dspName;
	}

	public void setDspName(String dspName) {
		this.dspName = dspName;
	}

	@Transient
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Transient
	public String getUad() {
		return uad;
	}

	public void setUad(String uad) {
		this.uad = uad;
	}

	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	public String getContractName() {
		return contractName;
	}

	public void setContractName(String contractName) {
		this.contractName = contractName;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getIsVip() {
		return isVip;
	}

	public void setIsVip(String isVip) {
		this.isVip = isVip;
	}

}
