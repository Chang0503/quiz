package com.example.quiz14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz14.service.ifs.AdminService;
import com.example.quiz14.vo.BasicRes;
import com.example.quiz14.vo.logginReq;

import jakarta.servlet.http.HttpServletRequest;

@CrossOrigin(origins = "https://quizfront-3bx3.onrender.com") // 允許前端網域
@RestController
@RequestMapping("/quiz")  // 👈 加上這行！
public class AdminController {
	 private final AdminService adminService;

	    @Autowired
	    public AdminController(AdminService adminService) {
	        this.adminService = adminService;
	    }

	    // 登入
	    @PostMapping("/login")
	    public BasicRes login(@RequestBody logginReq req, HttpServletRequest request) {
	        String ip = request.getRemoteAddr();  // 取得使用者 IP
	        req.setIp(ip);
	        return adminService.loggin(req);
	    }

	    // 登出
	    @PostMapping("/logout")
	    public BasicRes logout() {
	        return adminService.logout();
	    }
}
