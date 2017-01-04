package com.sc.td.outer.alipay.core;

public enum TradeStatus {

	WAIT_BUYER_PAY("交易创建，等待买家付款"),
	TRADE_CLOSED("未付款交易超时关闭，或支付完成后全额退款"),
	TRADE_SUCCESS("交易支付成功"),
	TRADE_FINISHED("交易结束，不可退款");
	
	private String desc;
	
	private TradeStatus(String desc){
		this.desc=desc;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
	@Override
	public String toString() {
		return this.desc;
	}
	
}
