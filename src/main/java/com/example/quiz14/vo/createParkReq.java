package com.example.quiz14.vo;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class createParkReq {

	@NotBlank(message = "電話不能空")
	private String phone;

	@NotBlank(message = "名字不能空")
	private String Name;
	
	@NotNull(message = "日期不能空")
	private LocalDate date;
	
	private String remark;
	
	@NotBlank(message = "時間不能空")
	private String time;
	
	@NotBlank(message = "車牌不能空")
	private String carNumber;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}
	
	

	
	
	
}
