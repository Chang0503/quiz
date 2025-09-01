package com.example.quiz14.service.ifs;

import java.time.LocalDate;

import com.example.quiz14.vo.BasicRes;
import com.example.quiz14.vo.GetAllInfoRes;
import com.example.quiz14.vo.GetInfoRes;
import com.example.quiz14.vo.createParkReq;
import com.example.quiz14.vo.updateParkReq;

public interface ParkService {

	public BasicRes create(createParkReq req); //訂車位
	
	public GetInfoRes getInfo(String phone,String carNumber); //查詢訂車資訊(單人)
	
	public GetAllInfoRes getAllInfo(LocalDate date); //查詢訂車資訊(全部)
	
	public BasicRes update(updateParkReq req); //編輯訂車資訊
	
	public BasicRes delete(String phone); //刪除
	
}
