package com.sc.td.business.service.sendsms;

import org.springframework.stereotype.Service;

import com.sc.td.common.utils.GetHttpMsg;

@Service
public class SendSmsService {

	public String sendMsg(String url) {
		String resp = GetHttpMsg.getHttp(url);
		return resp;
	}
}
