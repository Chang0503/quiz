package com.example.quiz14.service.ifs;

import com.example.quiz14.vo.BasicRes;
import com.example.quiz14.vo.logginReq;

public interface AdminService {

	public BasicRes loggin(logginReq req);
	
	public BasicRes logout();
	
}
