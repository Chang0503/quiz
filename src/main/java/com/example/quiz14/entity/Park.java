package com.example.quiz14.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;


import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "park")
public class Park {
	@Id
	@Column(name = "phone")
	private String phone;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "date")
	private LocalDate date;
	
	@Column(name = "time")
	private String time;
	
	@Column(name = "remark")
	private String remark;
	
	@Column(name = "carNumber")
	private String carNumber;
	
	
	 @Column(name = "id")
	 private int id;  
	 
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public void setCarNumber(String carNumber) {
		this.carNumber = carNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
	

}
