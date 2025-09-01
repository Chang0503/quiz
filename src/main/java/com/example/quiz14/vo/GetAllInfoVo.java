package com.example.quiz14.vo;

import java.time.LocalDate;

public class GetAllInfoVo {
	

	private String Name;
	
	private int id;
	
	private LocalDate date;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}


}
