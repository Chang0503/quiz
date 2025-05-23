package com.example.quiz14.entity;

import java.time.LocalDate;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz")
public class Quiz {
	@Id
	@Column(name = "id")
	private int id;
	@Column(name = "title")
	private String title;
	@Column(name = "direction")
	private String direction;
	@Column(name = "start_date")
	private LocalDate startDate;
//	LocalDate日期 LocalTime 時間 LocalDateTime 時間日期 後面加個Now就是當下時間
	@Column(name = "end_date")
	private LocalDate endDate;
	@Column(name = "published")
	private boolean published;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

}
