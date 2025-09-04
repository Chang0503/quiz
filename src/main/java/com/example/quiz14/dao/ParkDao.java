package com.example.quiz14.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quiz14.entity.Park;

public interface ParkDao extends JpaRepository<Park, String>{
	boolean existsByPhone(String phone);
    boolean existsByDateAndTime(LocalDate date, String time);
    Park findByCarNumber(String carNumber);
	List<Park> findByDate(LocalDate date);
	
}
