package com.example.quiz14.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Ip")
public class Ip {
	
	@Id
	@Column(name = "Ip")
	private String ip;
	
	@Column(name = "fail_count")
	private int failCount;
	
	@Column(name = "last_fail_time")
	private LocalDateTime lastFailTime;
	
	@Column(name = "locked_until")
	private LocalDateTime lockedUntil;
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getFailCount() {
		return failCount;
	}
	public void setFailCount(int failCount) {
		this.failCount = failCount;
	}
	public LocalDateTime getLastFailTime() {
		return lastFailTime;
	}
	public void setLastFailTime(LocalDateTime lastFailTime) {
		this.lastFailTime = lastFailTime;
	}
	public LocalDateTime getLockedUntil() {
		return lockedUntil;
	}
	public void setLockedUntil(LocalDateTime lockedUntil) {
		this.lockedUntil = lockedUntil;
	}
	
	
}
