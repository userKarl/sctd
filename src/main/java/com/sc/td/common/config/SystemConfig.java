package com.sc.td.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource({ "classpath:/cfg.properties" })
public class SystemConfig {


	@Value("${userfiles.img.url}")
	private String userfilesImgUrl;// 用户上传头像的URL

	@Value("${userImg.url}")
	private String userImgUrl;// 获取用户头像的URL
	
	@Value("${userfiles.icon.url}")
	private String userfilesIconUrl;// 保存指标图标的URL

	@Value("${idxIcon.url}")
	private String idxIconUrl;// 获取指标图标的URL

	@Value("${userfiles.broker.url}")
	private String userfilesBrokerUrl;// 经纪商图片文件夹
	
	@Value("${broker.url}")
	private String brokerUrl;// 获取经纪商图片

	// TODO


	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	public String getUserfilesImgUrl() {
		return userfilesImgUrl;
	}

	public String getUserImgUrl() {
		return userImgUrl;
	}

	public String getUserfilesIconUrl() {
		return userfilesIconUrl;
	}

	public String getIdxIconUrl() {
		return idxIconUrl;
	}

	public String getUserfilesBrokerUrl() {
		return userfilesBrokerUrl;
	}

	public String getBrokerUrl() {
		return brokerUrl;
	}
}
