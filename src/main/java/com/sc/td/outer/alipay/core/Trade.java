package com.sc.td.outer.alipay.core;

public class Trade {

	private String subject;//(必填)商品的标题/交易标题/订单标题/订单关键字等
	private String out_trade_no;//(必填)商户网站唯一订单号
	/* *
	 * 该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，
	 * 1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。
	 */
	private String timeout_express;
	private String total_amount;//(必填)订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]
	private String goods_type;//商品主类型：0—虚拟类商品，1—实物类商品     注：虚拟类商品不支持使用花呗渠道
	/* *
	 * 公用回传参数，如果请求时传递了该参数，则返回给商户时会回传该参数。
	 * 支付宝会在异步通知时将该参数原样返回。
	 * 本参数必须进行UrlEncode之后才可以发送给支付宝
	 */
	private String passback_params;
	private String promo_params;//优惠参数  注：仅与支付宝协商后可用
	private String extend_params;//业务扩展参数
	private String enable_pay_channels;//可用渠道
	private String disable_pay_channels;//禁用渠道
	private String userId;//用户手机号码
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTimeout_express() {
		return timeout_express;
	}
	public void setTimeout_express(String timeout_express) {
		this.timeout_express = timeout_express;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getGoods_type() {
		return goods_type;
	}
	public void setGoods_type(String goods_type) {
		this.goods_type = goods_type;
	}
	public String getPassback_params() {
		return passback_params;
	}
	public void setPassback_params(String passback_params) {
		this.passback_params = passback_params;
	}
	public String getPromo_params() {
		return promo_params;
	}
	public void setPromo_params(String promo_params) {
		this.promo_params = promo_params;
	}
	public String getExtend_params() {
		return extend_params;
	}
	public void setExtend_params(String extend_params) {
		this.extend_params = extend_params;
	}
	public String getEnable_pay_channels() {
		return enable_pay_channels;
	}
	public void setEnable_pay_channels(String enable_pay_channels) {
		this.enable_pay_channels = enable_pay_channels;
	}
	public String getDisable_pay_channels() {
		return disable_pay_channels;
	}
	public void setDisable_pay_channels(String disable_pay_channels) {
		this.disable_pay_channels = disable_pay_channels;
	}
}
