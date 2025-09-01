package com.example.quiz14.vo;

import java.util.List;

public class GetAllInfoRes extends BasicRes{
	
	List<GetAllInfoVo> getAllInfoVoList;

	public List<GetAllInfoVo> getGetAllInfoVoList() {
		return getAllInfoVoList;
	}

	public void setGetAllInfoVoList(List<GetAllInfoVo> getAllInfoVoList) {
		this.getAllInfoVoList = getAllInfoVoList;
	}

	public GetAllInfoRes() {
		super();
		// TODO Auto-generated constructor stub
	}

	public GetAllInfoRes(int code, String message) {
		super(code, message);
		// TODO Auto-generated constructor stub
	}

	public GetAllInfoRes(List<GetAllInfoVo> getAllInfoVoList) {
		super();
		this.getAllInfoVoList = getAllInfoVoList;
	}
	
	

}
