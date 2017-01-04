package com.sc.td.web.evaluate;

public class EvaluateUser {

	@Override
	public String toString() {
		return "EvaluateUser [username=" + username + ", sex=" + sex + ", birth=" + birth + ", mobile=" + mobile
				+ ", email=" + email + "]";
	}
	private String username;
	private String sex;
	private String birth;
	private String mobile;
	private String email;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
