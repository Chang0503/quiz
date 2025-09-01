package com.example.quiz14.vo;

import java.time.LocalDate;

public class GetInfoRes extends BasicRes{
	private String carNumber;
	
	private String phone;
	
	private LocalDate date;

	private String time;
	
	private String name;
	
	private String remark;

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public GetInfoRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetInfoRes(int code, String message) {
		super(code, message);
	}

	public GetInfoRes(String carNumber, String phone, LocalDate date, String time, String name, String remark) {
		super();
		this.carNumber = carNumber;
		this.phone = phone;
		this.date = date;
		this.time = time;
		this.name = name;
		this.remark = remark;
	}
	
	
}
