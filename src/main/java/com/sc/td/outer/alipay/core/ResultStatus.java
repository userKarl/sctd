package com.sc.td.outer.alipay.core;

public enum ResultStatus {

	success("交易成功",9000),
	processing("正在处理中，支付结果未知",8000),
	fail("订单支付失败",4000),
	request("重复请求",5000),
	cancle("用户中途取消",6001),
	connecerror("网络连接出错",6002),
	unknown("支付结果未知",6004);
	
	private String name;
	private int code;
	
	private ResultStatus(String name,int code){
		this.name=name;
		this.code=code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
	
	@Override
	public String toString() {
		return this.code + "_" + this.name;
	}
	
}
