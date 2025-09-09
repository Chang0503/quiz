package com.example.quiz14.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.quiz14.entity.Ip;


public interface IpDao extends JpaRepository<Ip, String> {

}
