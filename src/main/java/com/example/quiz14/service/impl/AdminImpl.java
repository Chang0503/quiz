package com.example.quiz14.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz14.dao.AdminDao;
import com.example.quiz14.dao.IpDao;
import com.example.quiz14.entity.Admin;
import com.example.quiz14.entity.Ip;
import com.example.quiz14.service.ifs.AdminService;
import com.example.quiz14.vo.BasicRes;
import com.example.quiz14.vo.logginReq;

@Service
public class AdminImpl implements AdminService{

	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private IpDao ipDao;
	
	@Override
	public BasicRes loggin(logginReq req) {
		Admin admin = adminDao.findById("admin").orElseThrow(() -> new RuntimeException("資料庫無管理員"));
		
		Ip ip = ipDao.findById(req.getIp()).orElse(new Ip());
		ip.setIp(req.getIp());
		LocalDateTime now = LocalDateTime.now();
		if (ip.getLockedUntil() != null && now.isBefore(ip.getLockedUntil())) {
		    return new BasicRes(400, "此 IP 已鎖定，請稍後再試");
		}

	    // 驗證帳號密碼
	    if (req.getAccount().equals(admin.getAccount()) && req.getPassword().equals(admin.getPassword())) {
	        // 登入成功 → 重置 IP 紀錄
	    	ip.setFailCount(0);
	    	ip.setLockedUntil(null);
	    	ipDao.save(ip);
	        return new BasicRes(200, "登入成功");
	    } else {
	        // 登入失敗 → 增加次數
	    	ip.setFailCount(ip.getFailCount() + 1);
	    	ip.setLastFailTime(now);

	        if (ip.getFailCount() >= 5) {
	        	ip.setLockedUntil(now.plusMinutes(1)); // 鎖 1 分鐘
	        }
	        if (ip.getFailCount() >= 10) {
	        	ip.setLockedUntil(now.plusMinutes(60)); // 鎖 60 分鐘
	        }
	        if (ip.getFailCount() >= 15) {
	        	ip.setLockedUntil(LocalDateTime.of(2099, 12, 31, 23, 59));
	        }

	        ipDao.save(ip);
	        return new BasicRes(400, "帳號或密碼錯誤");
	    }
	}


	@Override
	public BasicRes logout() {
		 return new BasicRes(200, "已登出");
	}

}
