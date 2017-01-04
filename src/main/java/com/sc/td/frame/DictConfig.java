package com.sc.td.frame;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource({ "classpath:/dict.properties" })
public class DictConfig {

	@Value("${vip.unable}")
	private String vip_unable; // VIP失效

	@Value("${vip.able}")
	private String vip_able; // VIP有效

	@Value("${money.vip.remark}")
	private String money_vip_remark; // 出入金的VIP金额

	@Value("${pk.inibalance.key}")
	private String pk_inibalance_key; // PK初始资金

	@Value("${reg.iniMoney.key}")
	private String reg_ini_money; // 新用户注册赠金
	
	@Value("${money.scuser.new.remark}")
	private String money_scuser_new_remark; // 新用户赠金备注

	@Value("${vip.todayCodeMoney.key}")
	private String vip_todayCodeMoney_key; // 开通今日代码VIP功能的月费用

	@Value("${vip.default.todayCodeMoney}")
	private String vip_default_todayCodeMoney; // 默认开通VIP每月需要的虚拟币

	@Value("${right.vip}")
	private String right_vip; // 权限为VIP

	@Value("${audit.ing.status}")
	private String audit_ing_status; // 审核中

	@Value("${audit.accept.status}")
	private String audit_accept_status; // 审核通过

	@Value("${audit.refuse.status}")
	private String audit_refuse_status; // 审核拒绝

	@Value("${group.direct.buy}")
	private String group_direct_buy;// 买

	@Value("${group.direct.sell}")
	private String group_direct_sell;// 卖

	@Value("${group.type.official}")
	private String group_type_official;// 组合类型：官方

	@Value("${group.type.personal}")
	private String group_type_personal;// 组合类型：个人

	@Value("${group.accept.pktimes}")
	private String group_accept_pktimes;// 当天接受PK场次

	@Value("${group.pk.allow}")
	private String group_pk_allow;// 允许PK

	@Value("${group.pk.notallow}")
	private String group_pk_notallow;// 不允许PK

	@Value("${compare.gt}")
	private String compare_gt;// 大于

	@Value("${compare.lt}")
	private String compare_lt;// 小于

	@Value("${group.minWinRate}")
	private String group_minWinRate;// 最小胜率

	@Value("${group.maxWinRate}")
	private String group_maxWinRate;// 最大胜率

	@Value("${group.minRowWin}")
	private String group_minRowWin;// 最小连胜场次

	@Value("${group.maxRowWin}")
	private String group_maxRowWin;// 最大连胜场次

	@Value("${group.minTotal}")
	private String group_minTotal;// 最小总场次

	@Value("${group.maxTotal}")
	private String group_maxTotal;// 最大总场次

	@Value("${pk.money.key}")
	private String pk_money_key;// PK时需要的虚拟币

	@Value("${pk.status.call}")
	private String pk_status_call;// 发起

	@Value("${pk.status.recall}")
	private String pk_status_recall;// 撤回

	@Value("${pk.status.refuse}")
	private String pk_status_refuse;// 拒绝

	@Value("${pk.status.ing}")
	private String pk_status_ing;// PK中

	@Value("${pk.status.end}")
	private String pk_status_end;// PK完毕

	@Value("${pk.result.applywin}")
	private String pk_result_applywin;// 发起方胜

	@Value("${pk.result.acceptwin}")
	private String pk_result_acceptwin;// 接受方胜

	@Value("${pk.result.planish}")
	private String pk_result_planish;// 打平

	@Value("${pk.result.pre}")
	private String pk_result_pre;// 未比赛

	@Value("${pk.days.key}")
	private String pk_days_key;// PK天数

	@Value("${pk.rejectMoney.key}")
	private String pk_rejectMoney_key;// 拒绝PK所需虚拟币

	@Value("${group.maxproducts.key}")
	private String group_maxproducts_key;// 从数据库获取最大商品数的key

	@Value("${group.max.products}")
	private String group_max_products;// 最大商品数

	@Value("${batch.id}")
	private String batch_id;// 批处理的ID
	
	@Value("${is.show}")
	private String is_show;// 显示
	
	@Value("${is.not.show}")
	private String is_not_show;// 不显示

	@Value("${read.flag}")
	private String read_flag;//已读

	@Value("${not.read.flag}")
	private String not_read_flag;//未读
	
	@Value("${msg.draft}")
	private String msg_draft;//草稿

	@Value("${msg.publish}")
	private String msg_publish;//发布
	
	@Value("${msg.type.system}")
	private String msg_type_system;//系统消息
	
	@Value("${msg.type.pk}")
	private String msg_type_pk;//战况公告
			
	// TODO
	
	public String getBatch_id() {
		return batch_id;
	}

	public String getReg_ini_money() {
		return reg_ini_money;
	}

	public String getMsg_type_system() {
		return msg_type_system;
	}

	public String getMsg_type_pk() {
		return msg_type_pk;
	}

	public String getMsg_draft() {
		return msg_draft;
	}

	public String getMsg_publish() {
		return msg_publish;
	}

	public String getRead_flag() {
		return read_flag;
	}

	public String getNot_read_flag() {
		return not_read_flag;
	}

	public String getIs_show() {
		return is_show;
	}

	public String getIs_not_show() {
		return is_not_show;
	}

	public String getGroup_maxproducts_key() {
		return group_maxproducts_key;
	}

	public String getGroup_max_products() {
		return group_max_products;
	}

	public String getPk_rejectMoney_key() {
		return pk_rejectMoney_key;
	}

	public String getPk_days_key() {
		return pk_days_key;
	}

	public String getPk_result_applywin() {
		return pk_result_applywin;
	}

	public String getPk_result_acceptwin() {
		return pk_result_acceptwin;
	}

	public String getPk_result_planish() {
		return pk_result_planish;
	}

	public String getPk_result_pre() {
		return pk_result_pre;
	}

	public String getPk_status_call() {
		return pk_status_call;
	}

	public String getPk_status_recall() {
		return pk_status_recall;
	}

	public String getPk_status_refuse() {
		return pk_status_refuse;
	}

	public String getPk_status_ing() {
		return pk_status_ing;
	}

	public String getPk_status_end() {
		return pk_status_end;
	}

	public String getPk_money_key() {
		return pk_money_key;
	}

	public String getGroup_minWinRate() {
		return group_minWinRate;
	}

	public String getGroup_maxWinRate() {
		return group_maxWinRate;
	}

	public String getGroup_minRowWin() {
		return group_minRowWin;
	}

	public String getGroup_maxRowWin() {
		return group_maxRowWin;
	}

	public String getGroup_minTotal() {
		return group_minTotal;
	}

	public String getGroup_maxTotal() {
		return group_maxTotal;
	}

	public String getCompare_gt() {
		return compare_gt;
	}

	public String getCompare_lt() {
		return compare_lt;
	}

	public String getGroup_pk_allow() {
		return group_pk_allow;
	}

	public String getGroup_pk_notallow() {
		return group_pk_notallow;
	}

	public String getGroup_accept_pktimes() {
		return group_accept_pktimes;
	}

	public String getGroup_direct_buy() {
		return group_direct_buy;
	}

	public String getGroup_direct_sell() {
		return group_direct_sell;
	}

	public String getGroup_type_official() {
		return group_type_official;
	}

	public String getGroup_type_personal() {
		return group_type_personal;
	}

	public String getMoney_scuser_new_remark() {
		return money_scuser_new_remark;
	}

	public String getAudit_ing_status() {
		return audit_ing_status;
	}

	public String getAudit_accept_status() {
		return audit_accept_status;
	}

	public String getAudit_refuse_status() {
		return audit_refuse_status;
	}

	public String getRight_vip() {
		return right_vip;
	}

	public String getVip_default_todayCodeMoney() {
		return vip_default_todayCodeMoney;
	}

	public String getVip_unable() {
		return vip_unable;
	}

	public String getVip_able() {
		return vip_able;
	}

	public String getMoney_vip_remark() {
		return money_vip_remark;
	}

	public String getPk_inibalance_key() {
		return pk_inibalance_key;
	}

	public String getVip_todayCodeMoney_key() {
		return vip_todayCodeMoney_key;
	}

	@Bean
	public PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
		return new PropertySourcesPlaceholderConfigurer();
	}
}
