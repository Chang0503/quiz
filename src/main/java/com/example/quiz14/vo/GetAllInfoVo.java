package com.example.quiz14.vo;

import java.time.LocalDate;

public class GetAllInfoVo {
	

	private String Name;
	
	private String phone;
	
	private LocalDate date;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
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


}
