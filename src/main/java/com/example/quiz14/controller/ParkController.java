package com.example.quiz14.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.quiz14.service.ifs.ParkService;
import com.example.quiz14.vo.BasicRes;
import com.example.quiz14.vo.GetAllInfoRes;
import com.example.quiz14.vo.GetInfoRes;
import com.example.quiz14.vo.createParkReq;
import com.example.quiz14.vo.updateParkReq;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")  // 允許 Angular 前端
@RestController
@RequestMapping("quiz")
public class ParkController {

	@Autowired
	private ParkService parkService;
	
	@PostMapping(value = "/park/create")
	public BasicRes create(@Valid @RequestBody createParkReq req) throws Exception {
		return parkService.create(req);
	}
	
	// 更新車位
    @PutMapping("/park/update")
    public BasicRes update(@Valid @RequestBody updateParkReq req) {
        return parkService.update(req);
    }
    
 // 或用 RequestParam
    @DeleteMapping("/park/delete")
    public BasicRes delete(@RequestParam("phone") String phone) {
        return parkService.delete(phone);
    }
    
    @GetMapping("/park/getInfo")
    public GetInfoRes getInfo(@RequestParam("phone") String phone) {
        return parkService.getInfo(phone);
    }

    @GetMapping("/park/getAllInfo")
    public GetAllInfoRes getAllInfo(@RequestParam("date") String dateStr) {
    	System.out.println("收到前端日期: [" + dateStr + "]");
        LocalDate date = LocalDate.parse(dateStr.trim()); // trim() 去掉前後空白或換行
        System.out.println("轉成 LocalDate: " + date);
        GetAllInfoRes res = parkService.getAllInfo(date);
        System.out.println("查詢結果: " + res);
        return res;
    }

}
