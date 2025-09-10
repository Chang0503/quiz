package com.example.quiz14.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quiz14.entity.Admin;

public interface AdminDao extends JpaRepository<Admin, String> {

	
}
