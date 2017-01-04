package com.sc.td.outer.alipay.core;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.joda.time.DateTime;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.google.common.collect.Maps;
import com.sc.td.common.utils.IdGen;
import com.sc.td.common.utils.datetime.TimeUtil;
import com.sc.td.common.utils.json.JacksonUtil;
import com.sc.td.outer.alipay.config.AlipayConfig;
import com.sc.td.outer.alipay.sign.RSA;


/* *
 *类名：AlipaySubmit
 *功能：支付宝各接口请求提交类
 */

public class AlipaySubmit {
    
	
    
	/**
     * 生成签名结果
     * @param sPara 要签名的数组
     * @return 签名结果字符串
	 * @ 
     */
	public static String buildRequestMysign(Map<String, String> sPara)  {
    	String prestr = AlipayCore.createLinkString(sPara); //把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        String mysign = "";
        if(AlipayConfig.sign_type.equals("RSA") ){
        	mysign = RSA.sign(prestr, AlipayConfig.private_key, AlipayConfig.charset);
        }
        return mysign;
    }
	
	/**
	 * 将参数中的value进行URLEncode
	 * @param map
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static Map<String,String> encode(Map<String,String> map) throws UnsupportedEncodingException{
		if(map.size()>0){
			for(Map.Entry<String, String> entry:map.entrySet()){
				entry.setValue(java.net.URLEncoder.encode(entry.getValue(),AlipayConfig.charset));
			}
		}
		return map;
	}
	/**
	 * 创建上传到支付宝的参数
	 * @param trade
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws AlipayApiException 
	 */
   public static String buildParams(Trade trade) throws UnsupportedEncodingException, AlipayApiException {
	   
	   //构建业务参数
	   Map<String, String> busiMap=Maps.newHashMap();
	   busiMap.put("subject", trade.getSubject());
	   busiMap.put("out_trade_no", IdGen.uuid());
	   busiMap.put("total_amount", trade.getTotal_amount());
	   busiMap.put("seller_id", AlipayConfig.seller_id);
	   busiMap.put("product_code", AlipayConfig.product_code);
	   //公共参数（userID）
	   busiMap.put("passback_params", trade.getUserId());
	   String json=JacksonUtil.objToJson(busiMap);
	   
	   //构建公共参数
	   StringBuffer publicParams=new StringBuffer();
	   Map<String, String> publicMap=Maps.newHashMap();
	   publicMap.put("app_id", AlipayConfig.app_id);
	   publicMap.put("method", AlipayConfig.method);
	   publicMap.put("charset", AlipayConfig.charset);
	   publicMap.put("sign_type", AlipayConfig.sign_type);
	   publicMap.put("timestamp", TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
	   publicMap.put("version", AlipayConfig.version);
	   publicMap.put("notify_url", AlipayConfig.notify_url);
	   publicMap.put("biz_content", json);
	   publicParams.append(AlipayCore.createLinkString(publicMap));
	   publicParams.append("&sign="+AlipaySignature.rsaSign(publicMap, AlipayConfig.private_key, AlipayConfig.charset));
	   return publicParams.toString();
   }
   
   
   /**
	 * 创建上传到支付宝的参数
	 * @param trade
	 * @return
	 * @throws UnsupportedEncodingException 
 * @throws AlipayApiException 
	 */
  public static Map<String,String> buildParamsMap(Trade trade) throws UnsupportedEncodingException, AlipayApiException {
	   
	   //构建业务参数
	   Map<String, String> busiMap=Maps.newHashMap();
	   busiMap.put("subject", trade.getSubject());
	   busiMap.put("out_trade_no", trade.getOut_trade_no());
	   busiMap.put("total_amount", trade.getTotal_amount());
	   busiMap.put("seller_id", AlipayConfig.seller_id);
	   busiMap.put("product_code", AlipayConfig.product_code);
	   //公共回传参数
	   busiMap.put("passback_params", trade.getUserId());
	   String json=JacksonUtil.objToJson(busiMap);
	   
	   //构建公共参数
	   Map<String, String> publicMap=Maps.newHashMap();
	   publicMap.put("app_id", AlipayConfig.app_id);
	   publicMap.put("method", AlipayConfig.method);
	   publicMap.put("charset", AlipayConfig.charset);
	   publicMap.put("sign_type", AlipayConfig.sign_type);
	   publicMap.put("timestamp", TimeUtil.dateTime2Str(DateTime.now(), TimeUtil.DSPdaytimeFormat));
	   publicMap.put("version", AlipayConfig.version);
	   publicMap.put("notify_url", AlipayConfig.notify_url);
	   publicMap.put("biz_content", json);
	   publicMap.put("sign", AlipaySignature.rsaSign(publicMap, AlipayConfig.private_key, AlipayConfig.charset));
	   return publicMap;
  }
  

	
	public static void main(String[] args) throws UnsupportedEncodingException, AlipayApiException {
	}

}
