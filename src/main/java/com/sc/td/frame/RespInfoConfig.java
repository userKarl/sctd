package com.sc.td.frame;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource({ "classpath:/respInfo.properties" })
public class RespInfoConfig {

	@Value("${paramsNull}")
	private String paramsNull; // 参数为空

	@Value("${paramsError}")
	private String paramsError; // 参数错误

	@Value("${mobileOrPwdError}")
	private String mobileOrPwdError; // 手机号或密码错误

	@Value("${userNameNull}")
	private String userNameNull; // 用户名为空

	@Value("${userNameExist}")
	private String userNameExist; // 用户名已存在

	@Value("${mobileNull}")
	private String mobileNull; // 手机号为空

	@Value("${mobileExist}")
	private String mobileExist; // 手机号已存在

	@Value("${tokenIllegal}")
	private String tokenIllegal; // token非法

	@Value("${tokenInvalid}")
	private String tokenInvalid; // token失效

	@Value("${sysExeption}")
	private String sysExeption; // 系统异常

	@Value("${smsException}")
	private String smsException; // 短信发送异常

	@Value("${logout}")
	private String logout; // 用户登出

	@Value("${logoutException}")
	private String logoutException; // 登出异常

	@Value("${resetpwd.success}")
	private String resetpwdsuccess; // 密码修改成功

	@Value("${resetpwd.fail}")
	private String resetpwdfail; // 密码修改失败

	@Value("${validCodeType.null}")
	private String validCodeTypeNull; // 验证码类型为空

	@Value("${uploadImg.success}")
	private String uploadImgSuccess; // 头像上传成功

	@Value("${uploadImg.fail}")
	private String uploadImgFail; // 头像上传失败

	@Value("${nondata}")
	private String nonData; // 无数据

	@Value("${isnotvip}")
	private String isnotVip; // 不是VIP

	@Value("${userNotExist}")
	private String userNotExist; // 用户不存在

	@Value("${password.old.error}")
	private String passwordOldError; // 原密码填写错误

	@Value("${password.re.error}")
	private String passwordReError; // 两次密码输入不一致

	@Value("${becomevip.success}")
	private String becomevipSuccess; // VIP开通成功

	@Value("${becomevip.fail}")
	private String becomevipFail; // VIP开通失败

	@Value("${vip.expire}")
	private String vipExpire; // VIP已过期

	@Value("${moeny.notenough}")
	private String moenyNotenough; // 虚拟币不足

	@Value("${create.group.error}")
	private String createGroupError;// 创建战队失败

	@Value("${create.group.success}")
	private String createGroupSuccess;// 创建战队成功

	@Value("${group.create.limit}")
	private String groupCreateLimit;//只能创建一只战队
	
	@Value("${sendsms.limit}")
	private String sendsmsLimit;// 不能频繁发送短信

	@Value("${accept.pkmoeny.notenough}")
	private String acceptPkmoenyNotenough;// PK发起者余额不足

	@Value("${apply.pkmoeny.notenough}")
	private String applyPkmoenyNotenough;// PK对象余额不足

	@Value("${pk.exist}")
	private String pkExist;// 已存在PK

	@Value("${group.notexist}")
	private String groupNotexist;// 战队不存在

	@Value("${group.pk.acceptlimit}")
	private String groupPkAcceptlimit;// PK上限

	@Value("${operate.success}")
	private String operateSuccess; // 操作成功

	@Value("${pk.apply.close}")
	private String pkApplyClose;// 发起方不允许PK

	@Value("${pk.accept.close}")
	private String pkAcceptClose;// 接受方不允许PK

	@Value("${operate.fail}")
	private String operateFail;// 操作失败

	@Value("${group.max.products}")
	private String groupMaxProducts;// 组合最大产品数

	@Value("${pk.ing}")
	private String pkIng;// PK进行中，不能修改状态

	@Value("${pk.end}")
	private String pkEnd;// PK已结束，不能修改状态

	@Value("${group.name.exist}")
	private String groupNameExist;// 战队组合名称已存在

	@Value("${group.stock.percent}")
	private String groupStockPercent;// 总投资比例不能大于1

	@Value("${pk.self.user}")
	private String pkSelfUser;// 不能和自己PK
	// TODO
	public String getGroupCreateLimit() {
		return groupCreateLimit;
	}

	public String getGroupNameExist() {
		return groupNameExist;
	}

	public String getPkSelfUser() {
		return pkSelfUser;
	}

	public String getGroupStockPercent() {
		return groupStockPercent;
	}

	public String getPkIng() {
		return pkIng;
	}

	public String getPkEnd() {
		return pkEnd;
	}

	public String getGroupMaxProducts() {
		return groupMaxProducts;
	}

	public String getOperateFail() {
		return operateFail;
	}

	public String getPkApplyClose() {
		return pkApplyClose;
	}

	public String getPkAcceptClose() {
		return pkAcceptClose;
	}

	public String getOperateSuccess() {
		return operateSuccess;
	}

	public String getGroupPkAcceptlimit() {
		return groupPkAcceptlimit;
	}

	public String getGroupNotexist() {
		return groupNotexist;
	}

	public String getPkExist() {
		return pkExist;
	}

	public String getAcceptPkmoenyNotenough() {
		return acceptPkmoenyNotenough;
	}

	public String getApplyPkmoenyNotenough() {
		return applyPkmoenyNotenough;
	}

	public String getSendsmsLimit() {
		return sendsmsLimit;
	}

	public String getCreateGroupSuccess() {
		return createGroupSuccess;
	}

	public String getCreateGroupError() {
		return createGroupError;
	}

	public String getMoenyNotenough() {
		return moenyNotenough;
	}

	public String getVipExpire() {
		return vipExpire;
	}

	public String getParamsNull() {
		return paramsNull;
	}

	public String getParamsError() {
		return paramsError;
	}

	public String getMobileOrPwdError() {
		return mobileOrPwdError;
	}

	public String getUserNameNull() {
		return userNameNull;
	}

	public String getUserNameExist() {
		return userNameExist;
	}

	public String getMobileNull() {
		return mobileNull;
	}

	public String getMobileExist() {
		return mobileExist;
	}

	public String getTokenIllegal() {
		return tokenIllegal;
	}

	public String getTokenInvalid() {
		return tokenInvalid;
	}

	public String getSysExeption() {
		return sysExeption;
	}

	public String getSmsException() {
		return smsException;
	}

	public String getLogout() {
		return logout;
	}

	public String getLogoutException() {
		return logoutException;
	}

	public String getResetpwdsuccess() {
		return resetpwdsuccess;
	}

	public String getResetpwdfail() {
		return resetpwdfail;
	}

	public String getValidCodeTypeNull() {
		return validCodeTypeNull;
	}

	public String getUploadImgSuccess() {
		return uploadImgSuccess;
	}

	public String getUploadImgFail() {
		return uploadImgFail;
	}

	public String getNonData() {
		return nonData;
	}

	public String getIsnotVip() {
		return isnotVip;
	}

	public String getUserNotExist() {
		return userNotExist;
	}

	public String getPasswordOldError() {
		return passwordOldError;
	}

	public String getPasswordReError() {
		return passwordReError;
	}

	public String getBecomevipSuccess() {
		return becomevipSuccess;
	}

	public String getBecomevipFail() {
		return becomevipFail;
	}

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
