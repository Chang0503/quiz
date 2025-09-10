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
	    String account = req.getAccount();
	    String ipStr = req.getIp();
	    LocalDateTime now = LocalDateTime.now();

	    // 1️⃣ 找管理員帳號
	    Admin admin = adminDao.findById(account)
	            .orElseThrow(() -> new RuntimeException("資料庫無此管理員"));

	    // 2️⃣ 找 IP 紀錄
	    Ip ip = ipDao.findById(ipStr).orElse(new Ip());
	    ip.setIp(ipStr);

	    // 3️⃣ 檢查 IP 是否鎖定
	    if (ip.getLockedUntil() != null && now.isBefore(ip.getLockedUntil())) {
	        return new BasicRes(400, "此 IP 已鎖定，請稍後再試");
	    }

	    // 4️⃣ 驗證帳號密碼
	    if (req.getPassword().equals(admin.getPassword())) {
	        // ✅ 登入成功 → 重置 IP 紀錄
	        ip.setFailCount(0);
	        ip.setLockedUntil(null);
	        ipDao.save(ip);

	        return new BasicRes(200, "登入成功");
	    } else {
	        // ❌ 登入失敗 → 增加次數
	        ip.setFailCount(ip.getFailCount() + 1);
	        ip.setLastFailTime(now);

	        // 5️⃣ 判斷鎖定規則
	        if (ip.getFailCount() >= 5 && ip.getFailCount() < 10) {
	            ip.setLockedUntil(now.plusMinutes(1)); // 鎖 1 分鐘
	        } else if (ip.getFailCount() >= 10 && ip.getFailCount() < 15) {
	            ip.setLockedUntil(now.plusMinutes(60)); // 鎖 1 小時
	        } else if (ip.getFailCount() >= 15) {
	            ip.setLockedUntil(LocalDateTime.of(2099, 12, 31, 23, 59)); // 永久鎖
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
