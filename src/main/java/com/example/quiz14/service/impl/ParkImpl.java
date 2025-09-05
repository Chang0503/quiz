package com.example.quiz14.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.quiz14.dao.ParkDao;
import com.example.quiz14.entity.Park;
import com.example.quiz14.service.ifs.ParkService;
import com.example.quiz14.vo.BasicRes;
import com.example.quiz14.vo.GetAllInfoRes;
import com.example.quiz14.vo.GetAllInfoVo;
import com.example.quiz14.vo.GetInfoRes;
import com.example.quiz14.vo.createParkReq;
import com.example.quiz14.vo.updateParkReq;

import jakarta.transaction.Transactional;

@Service
public class ParkImpl implements ParkService{

	 @Autowired
	 private ParkDao parkDao;
	
	@Override
	public BasicRes create(createParkReq req) {
		  try {
	            // 1. 檢查必要欄位（這裡也可靠 @Valid 自動驗證）
	            if (req.getPhone() == null || req.getPhone().isEmpty()) {
	                return new BasicRes(400, "電話不能空");
	            }

	            // 2. 檢查該電話是否已存在（因 phone 是主鍵）
	            if (parkDao.existsById(req.getPhone())) {
	                return new BasicRes(400, "該電話已經訂過車位");
	            }
	            
//	            // 2. 電話格式驗證 (4碼-3碼-3碼)
//	            String phoneRegex = "\\d{4}-\\d{3}-\\d{3}";
//	            if (!Pattern.matches(phoneRegex, req.getPhone())) {
//	                return new BasicRes(400, "電話格式錯誤，正確格式為 xxxx-xxx-xxx");
//	            }
//
//	            // 3. 車牌格式驗證 (三個英文-三個數字)
//	            String carNumberRegex = "^[A-Z]{3}-\\d{4}$";
//	            if (!Pattern.matches(carNumberRegex, req.getCarNumber().toUpperCase())) {
//	                return new BasicRes(400, "車牌格式錯誤，正確格式為 ABC-1234");
//	            }

	            // 3. 建立新 Park 實體
	            Park park = new Park();
	            park.setPhone(req.getPhone());
	            park.setName(req.getName());
	            park.setDate(req.getDate());
	            park.setTime(req.getTime());
	            park.setRemark(req.getRemark());
	            park.setCarNumber(req.getCarNumber());

	            parkDao.save(park);

	            return new BasicRes(200, "訂車位成功");
	        } catch (Exception e) {
	            return new BasicRes(400, "訂車位失敗: ");
	        }
	    }
	

	@Override
	public GetInfoRes getInfo(String phone) {
		System.out.println("received phone: " + phone);
	    try {
	        if (phone == null || phone.isEmpty()) {
	            return new GetInfoRes(400, "電話不能為空");
	        }

//	        // 移除非數字，保證和資料庫一致
//	        String normalizedPhone = phone.replaceAll("\\D", "");

	        Park park = parkDao.findByPhone(phone).orElse(null);
	        System.out.println("found: " + park);
	        if (park == null) {
	            return new GetInfoRes(404, "查無資料");
	        }

	        return new GetInfoRes(
	        		 200, // ✅ 設定 code = 200
	        		    "查詢成功", // 可加訊息
	            park.getCarNumber(),
	            park.getPhone(),
	            park.getDate(),
	            park.getTime(),
	            park.getName(),
	            park.getRemark()
	        );

	    } catch (Exception e) {
	        return new GetInfoRes(400, "查詢失敗: " + e.getMessage());
	    }
	}


	@Override
	public GetAllInfoRes getAllInfo(LocalDate date) {
	    try {
	        List<Park> parkList = parkDao.findByDate(date);

	        List<GetAllInfoVo> voList = parkList.stream().map(park -> {
	            GetAllInfoVo vo = new GetAllInfoVo();
	            vo.setPhone(park.getPhone()); // 或其他唯一值
	            vo.setName(park.getName());
	            vo.setDate(park.getDate());
	            return vo;
	        }).toList();
	        System.out.println("查到資料數量: " + voList.size());
	        voList.forEach(vo -> System.out.println(vo.getPhone() + " " + vo.getName() + " " + vo.getDate()));
	        GetAllInfoRes res = new GetAllInfoRes();
	        res.setCode(200);          // 一定要有 code 200
	        res.setMessage("查詢成功"); // 一定要有 message
	        res.setGetAllInfoVoList(voList);      // <- 這裡改成你類別已有的 setter
	        return res;

	    } catch (Exception e) {
	        return new GetAllInfoRes(400, "查詢失敗: " + e.getMessage());
	    }
	}


	@Override
	public BasicRes update(updateParkReq req) {
		 try {
		       
		        // 2. 先查詢資料是否存在
		        Optional<Park> optionalPark = parkDao.findById(req.getPhone());
		        if (optionalPark.isEmpty()) {
		            return new BasicRes(404, "找不到該車位資料");
		        }

		        // 3. 更新資料
		        Park park = optionalPark.get();
		        if (req.getName() != null) park.setName(req.getName());
		        if (req.getDate() != null) park.setDate(req.getDate());
		        if (req.getTime() != null) park.setTime(req.getTime());
		        if (req.getRemark() != null) park.setRemark(req.getRemark());
		        if (req.getCarNumber() != null) park.setCarNumber(req.getCarNumber());

		        parkDao.save(park);

		        return new BasicRes(200, "更新成功");

		    } catch (Exception e) {
		        return new BasicRes(400, "更新失敗: " + e.getMessage());
		    }
	}

	@Transactional
	@Override
	public BasicRes delete(String phone) {
	    try {
	        // 移除非數字，和資料庫一致
	        String normalizedPhone = phone.replaceAll("\\D", "");

	        // 先查是否存在
	        if (!parkDao.existsById(normalizedPhone)) {
	            return new BasicRes(404, "查無資料，刪除失敗");
	        }

	        // 刪除資料
	        parkDao.deleteById(normalizedPhone);

	        return new BasicRes(200, "刪除成功");

	    } catch (Exception e) {
	        return new BasicRes(400, "刪除失敗: " + e.getMessage());
	    }
	}



}
