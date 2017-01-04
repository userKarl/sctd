package com.sc.td.business.entity.scmarket;

public class MarketInfo {
	 
    /// 交易所代码
    private String exchangeCode;
    /// 合约代码
    private String code;
    /// 买价
    private String buyPrice;
    /// 买量
    private String buyNumber;
    /// 卖价
    private String salePrice;
    /// 卖量
    private String saleNumber;
    /// 最新价
    private String currPrice;
    /// 现量
    private String currNumber;
    /// 当天最高价
    private String high;
    /// 当天最低价
    private String low;
    /// 开盘价
    private String open;
    /// 昨结算
    private String oldClose;
    /// 当天结算价
    private String close;
    /// 行情时间
    private String time;
    /// 成交量
    private String filledNum;
    /// 持仓量
    private String holdNum;
    /// 买价2
    private String buyPrice2;
    /// 买价3
    private String buyPrice3;
    /// 买价4
    private String buyPrice4;
    /// 买价5
    private String buyPrice5;
    /// 买量2
    private String buyNumber2;
    /// 买量3
    private String buyNumber3;
    /// 买量4
    private String buyNumber4;
    /// 买量5
    private String buyNumber5;
    /// 卖价2
    private String salePrice2;
    /// 卖价3
    private String salePrice3;
    /// 卖价4
    private String salePrice4;
    /// 卖价5
    private String salePrice5;
    /// 卖量2
    private String saleNumber2;
    /// 卖量3
    private String saleNumber3;
    /// 卖量4
    private String saleNumber4;
    /// 卖量5
    private String saleNumber5;
    /// 隐藏买价
    private String hideBuyPrice;
    /// 隐藏买量
    private String hideBuyNumber;
    /// 隐藏卖价
    private String hideSalePrice;
    /// 隐藏卖量
    private String hideSaleNumber;
    /// 行情区分
    private String type;
    /// 跌停价
    private String limitDownPrice;
    /// 涨停价
    private String limitUpPrice;
    /// 交易日
    private String tradeDay;
    private String buyPrice6;
    private String buyPrice7;
    private String buyPrice8;
    private String buyPrice9;
    private String buyPrice10;
    private String buyNumber6;
    private String buyNumber7;
    private String buyNumber8;
    private String buyNumber9;
    private String buyNumber10;
    private String salePrice6;
    private String salePrice7;
    private String salePrice8;
    private String salePrice9;
    private String salePrice10;
    private String saleNumber6;
    private String saleNumber7;
    private String saleNumber8;
    private String saleNumber9;
    private String saleNumber10;
    /// 港交所股票行情：成交类型
    private String tradeFlag;
    /// 交易所数据时间戳
    private String dataTimestamp;
    /// 数据来源。考虑到不同交易所可能有不同数据时间戳格式，可以用该字段确定数据来源
    private String dataSourceId;
	public String getExchangeCode() {
		return exchangeCode;
	}
	public void setExchangeCode(String exchangeCode) {
		this.exchangeCode = exchangeCode;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBuyPrice() {
		return buyPrice;
	}
	public void setBuyPrice(String buyPrice) {
		this.buyPrice = buyPrice;
	}
	public String getBuyNumber() {
		return buyNumber;
	}
	public void setBuyNumber(String buyNumber) {
		this.buyNumber = buyNumber;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getSaleNumber() {
		return saleNumber;
	}
	public void setSaleNumber(String saleNumber) {
		this.saleNumber = saleNumber;
	}
	public String getCurrPrice() {
		return currPrice;
	}
	public void setCurrPrice(String currPrice) {
		this.currPrice = currPrice;
	}
	public String getCurrNumber() {
		return currNumber;
	}
	public void setCurrNumber(String currNumber) {
		this.currNumber = currNumber;
	}
	public String getHigh() {
		return high;
	}
	public void setHigh(String high) {
		this.high = high;
	}
	public String getLow() {
		return low;
	}
	public void setLow(String low) {
		this.low = low;
	}
	public String getOpen() {
		return open;
	}
	public void setOpen(String open) {
		this.open = open;
	}
	public String getOldClose() {
		return oldClose;
	}
	public void setOldClose(String oldClose) {
		this.oldClose = oldClose;
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getFilledNum() {
		return filledNum;
	}
	public void setFilledNum(String filledNum) {
		this.filledNum = filledNum;
	}
	public String getHoldNum() {
		return holdNum;
	}
	public void setHoldNum(String holdNum) {
		this.holdNum = holdNum;
	}
	public String getBuyPrice2() {
		return buyPrice2;
	}
	public void setBuyPrice2(String buyPrice2) {
		this.buyPrice2 = buyPrice2;
	}
	public String getBuyPrice3() {
		return buyPrice3;
	}
	public void setBuyPrice3(String buyPrice3) {
		this.buyPrice3 = buyPrice3;
	}
	public String getBuyPrice4() {
		return buyPrice4;
	}
	public void setBuyPrice4(String buyPrice4) {
		this.buyPrice4 = buyPrice4;
	}
	public String getBuyPrice5() {
		return buyPrice5;
	}
	public void setBuyPrice5(String buyPrice5) {
		this.buyPrice5 = buyPrice5;
	}
	public String getBuyNumber2() {
		return buyNumber2;
	}
	public void setBuyNumber2(String buyNumber2) {
		this.buyNumber2 = buyNumber2;
	}
	public String getBuyNumber3() {
		return buyNumber3;
	}
	public void setBuyNumber3(String buyNumber3) {
		this.buyNumber3 = buyNumber3;
	}
	public String getBuyNumber4() {
		return buyNumber4;
	}
	public void setBuyNumber4(String buyNumber4) {
		this.buyNumber4 = buyNumber4;
	}
	public String getBuyNumber5() {
		return buyNumber5;
	}
	public void setBuyNumber5(String buyNumber5) {
		this.buyNumber5 = buyNumber5;
	}
	public String getSalePrice2() {
		return salePrice2;
	}
	public void setSalePrice2(String salePrice2) {
		this.salePrice2 = salePrice2;
	}
	public String getSalePrice3() {
		return salePrice3;
	}
	public void setSalePrice3(String salePrice3) {
		this.salePrice3 = salePrice3;
	}
	public String getSalePrice4() {
		return salePrice4;
	}
	public void setSalePrice4(String salePrice4) {
		this.salePrice4 = salePrice4;
	}
	public String getSalePrice5() {
		return salePrice5;
	}
	public void setSalePrice5(String salePrice5) {
		this.salePrice5 = salePrice5;
	}
	public String getSaleNumber2() {
		return saleNumber2;
	}
	public void setSaleNumber2(String saleNumber2) {
		this.saleNumber2 = saleNumber2;
	}
	public String getSaleNumber3() {
		return saleNumber3;
	}
	public void setSaleNumber3(String saleNumber3) {
		this.saleNumber3 = saleNumber3;
	}
	public String getSaleNumber4() {
		return saleNumber4;
	}
	public void setSaleNumber4(String saleNumber4) {
		this.saleNumber4 = saleNumber4;
	}
	public String getSaleNumber5() {
		return saleNumber5;
	}
	public void setSaleNumber5(String saleNumber5) {
		this.saleNumber5 = saleNumber5;
	}
	public String getHideBuyPrice() {
		return hideBuyPrice;
	}
	public void setHideBuyPrice(String hideBuyPrice) {
		this.hideBuyPrice = hideBuyPrice;
	}
	public String getHideBuyNumber() {
		return hideBuyNumber;
	}
	public void setHideBuyNumber(String hideBuyNumber) {
		this.hideBuyNumber = hideBuyNumber;
	}
	public String getHideSalePrice() {
		return hideSalePrice;
	}
	public void setHideSalePrice(String hideSalePrice) {
		this.hideSalePrice = hideSalePrice;
	}
	public String getHideSaleNumber() {
		return hideSaleNumber;
	}
	public void setHideSaleNumber(String hideSaleNumber) {
		this.hideSaleNumber = hideSaleNumber;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLimitDownPrice() {
		return limitDownPrice;
	}
	public void setLimitDownPrice(String limitDownPrice) {
		this.limitDownPrice = limitDownPrice;
	}
	public String getLimitUpPrice() {
		return limitUpPrice;
	}
	public void setLimitUpPrice(String limitUpPrice) {
		this.limitUpPrice = limitUpPrice;
	}
	public String getTradeDay() {
		return tradeDay;
	}
	public void setTradeDay(String tradeDay) {
		this.tradeDay = tradeDay;
	}
	public String getBuyPrice6() {
		return buyPrice6;
	}
	public void setBuyPrice6(String buyPrice6) {
		this.buyPrice6 = buyPrice6;
	}
	public String getBuyPrice7() {
		return buyPrice7;
	}
	public void setBuyPrice7(String buyPrice7) {
		this.buyPrice7 = buyPrice7;
	}
	public String getBuyPrice8() {
		return buyPrice8;
	}
	public void setBuyPrice8(String buyPrice8) {
		this.buyPrice8 = buyPrice8;
	}
	public String getBuyPrice9() {
		return buyPrice9;
	}
	public void setBuyPrice9(String buyPrice9) {
		this.buyPrice9 = buyPrice9;
	}
	public String getBuyPrice10() {
		return buyPrice10;
	}
	public void setBuyPrice10(String buyPrice10) {
		this.buyPrice10 = buyPrice10;
	}
	public String getBuyNumber6() {
		return buyNumber6;
	}
	public void setBuyNumber6(String buyNumber6) {
		this.buyNumber6 = buyNumber6;
	}
	public String getBuyNumber7() {
		return buyNumber7;
	}
	public void setBuyNumber7(String buyNumber7) {
		this.buyNumber7 = buyNumber7;
	}
	public String getBuyNumber8() {
		return buyNumber8;
	}
	public void setBuyNumber8(String buyNumber8) {
		this.buyNumber8 = buyNumber8;
	}
	public String getBuyNumber9() {
		return buyNumber9;
	}
	public void setBuyNumber9(String buyNumber9) {
		this.buyNumber9 = buyNumber9;
	}
	public String getBuyNumber10() {
		return buyNumber10;
	}
	public void setBuyNumber10(String buyNumber10) {
		this.buyNumber10 = buyNumber10;
	}
	public String getSalePrice6() {
		return salePrice6;
	}
	public void setSalePrice6(String salePrice6) {
		this.salePrice6 = salePrice6;
	}
	public String getSalePrice7() {
		return salePrice7;
	}
	public void setSalePrice7(String salePrice7) {
		this.salePrice7 = salePrice7;
	}
	public String getSalePrice8() {
		return salePrice8;
	}
	public void setSalePrice8(String salePrice8) {
		this.salePrice8 = salePrice8;
	}
	public String getSalePrice9() {
		return salePrice9;
	}
	public void setSalePrice9(String salePrice9) {
		this.salePrice9 = salePrice9;
	}
	public String getSalePrice10() {
		return salePrice10;
	}
	public void setSalePrice10(String salePrice10) {
		this.salePrice10 = salePrice10;
	}
	public String getSaleNumber6() {
		return saleNumber6;
	}
	public void setSaleNumber6(String saleNumber6) {
		this.saleNumber6 = saleNumber6;
	}
	public String getSaleNumber7() {
		return saleNumber7;
	}
	public void setSaleNumber7(String saleNumber7) {
		this.saleNumber7 = saleNumber7;
	}
	public String getSaleNumber8() {
		return saleNumber8;
	}
	public void setSaleNumber8(String saleNumber8) {
		this.saleNumber8 = saleNumber8;
	}
	public String getSaleNumber9() {
		return saleNumber9;
	}
	public void setSaleNumber9(String saleNumber9) {
		this.saleNumber9 = saleNumber9;
	}
	public String getSaleNumber10() {
		return saleNumber10;
	}
	public void setSaleNumber10(String saleNumber10) {
		this.saleNumber10 = saleNumber10;
	}
	public String getTradeFlag() {
		return tradeFlag;
	}
	public void setTradeFlag(String tradeFlag) {
		this.tradeFlag = tradeFlag;
	}
	public String getDataTimestamp() {
		return dataTimestamp;
	}
	public void setDataTimestamp(String dataTimestamp) {
		this.dataTimestamp = dataTimestamp;
	}
	public String getDataSourceId() {
		return dataSourceId;
	}
	public void setDataSourceId(String dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
    
}
