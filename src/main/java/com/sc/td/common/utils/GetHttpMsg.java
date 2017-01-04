package com.sc.td.common.utils;



import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;


public class GetHttpMsg {
	static RestTemplate restTemplate = new RestTemplate();
	 public  static String getHttp(String url){		 
			String respHtml = restTemplate.getForObject(url, String.class);
			return respHtml;

	 }
	
	public  static String postHttp(String url, String body) {
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<String> entity = new HttpEntity<String>(body,headers);
		String respHtml = restTemplate.postForObject(url, entity, String.class);
		return respHtml;

	 }
}
