package com.sc.td.outer.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 */

public class AlipayConfig {
	
	 
     //支付宝提供给商户的服务接入网关URL(新)
	public static final String ALIPAY_GATEWAY_NEW = "https://openapi.alipay.com/gateway.do?";
    
	//开发者应用ID
	public static final String app_id="2016111602878526";
	
	//支付宝收款ID
	public static final String seller_id="2088521118596996";
	
	//接口名称
	public static final String method="alipay.trade.app.pay";
	
	//编码格式
	public static final String charset="utf-8";
	
	//签名类型
	public static final String sign_type="RSA";
	
	//调用的接口版本，固定为：1.0
	public static final String version="1.0";
	
	//支付宝服务器主动通知商户服务器里指定的页面
	public static final String notify_url="https://www.wtrade365.com:8443/sctd/alipay/notify";
	
	//销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY
	public static final String product_code="QUICK_MSECURITY_PAY";
	
	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static final String private_key = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL9+p+e7iheN47vG"+
			"hL56zw4u14kNa1Wp6LUNJ88lzzctEKSh1kA+9Hsm3n1rEmDwp0MbCJ6bP8stw8zf"+
			"jOsnXFYWG/ua9hKBCVQjRuaNCv4Pjt0FQmH+w0LjvKDIaLXpFqniHiZ7IvqJF5f9"+
			"PN2EOWgLeWw4NG3JWHP6DtS/QfxPAgMBAAECgYEAthhKsiGz/xH+LOFp+XfuzR3d"+
			"94W7O4qGryQDZwgdM+q2mNcMVauH8FPV3o2ivGwbocRjPe+DikHKn1c1OagENHNQ"+
			"DSEXpUvnlGUKaRVKVVyap5brnrO/WE+mELreZ7/DcM12kn1UGy3PNudsU9+94QgP"+
			"dbgj2NgEWUVVRBPrvAECQQDgg6hg6W1gw9BD+chJyFSDDQVxM36bLvA4xg/K0Yf1"+
			"QL5YWSoTZJtUITWy2/jz3xz9O+1X+Ug1J5bxQI5etAPhAkEA2lmOPk1K3uNBLMvY"+
			"IpS1nSK4awhK6K5dx49xHCgG/CMDdV1Fip3eYKCHAPM3dak54H5PU0bXg6O+9UqW"+
			"p94GLwJBAKuOLvWOqNOODPBER3WJrHpPUt9DN/UgktLvFVYncbpH9KrRx0L2V3Kb"+
			"xcqcJ8lBrYcbaasVgsWLMN28NI3NBEECQGEQAHBnkYnFQfq03u54ZsdkYNCLjikj"+
			"IeMNO73FeV1p/yaUAM7e4LTZh0n/1D6ErIrD+2VMj48gIkoODyyuQNcCQB+HbahS"+
			"pmndJgqgaXncRWZBMNgAafLJTYjTfdNBtf97AG6nvIdzYKo4UArD5cf+TOwb3lwc"+
			"i+yOcEgTZqqrtoA=";
	
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static final String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc"
			+ "73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjz"
			+ "OmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
		
	public static final String rate_key="virtual_money_rate";
	
	//出入金表添加记录备注
	public static final String money_remark="用户充值墨币";
	
}

