package com.sc.td;

import com.sc.td.common.utils.GetHttpMsg;

public class Test {

	
	public static void main(String[] args) throws Exception {	
		String str=GetHttpMsg.getHttp("http://www.8tech.cc:6080/sctd/reload/user");
		System.out.println(str);
	}
}
