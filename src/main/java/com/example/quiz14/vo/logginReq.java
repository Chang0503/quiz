package com.example.quiz14.vo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class logginReq {

	@NotBlank(message = "帳號不能空")
	private String account;
	
	@NotBlank(message = "密碼不能空")
	private String password;
	
	@NotNull(message = "Ip位置不能空")
	private String ip;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
	
	
	
}
