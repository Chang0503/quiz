package com.example.quiz14.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource; // ✅ 注意是 javax.sql.DataSource
import java.sql.Connection;


@RestController
public class TestController {
	 private final DataSource dataSource;

	    public TestController(DataSource dataSource) {
	        this.dataSource = dataSource;
	    }

	    @GetMapping("/test-db")
	    public String testDatabase() {
	        try (Connection conn = dataSource.getConnection()) {
	            if (conn.isValid(2)) { // 2 秒超時
	                return "✅ Successfully connected to Neon PostgreSQL!";
	            } else {
	                return "❌ Connection failed!";
	            }
	        } catch (Exception e) {
	            return "❌ Connection failed: " + e.getMessage();
	        }
	    }
}
