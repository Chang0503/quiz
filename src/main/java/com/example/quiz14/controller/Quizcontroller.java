package com.example.quiz14.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import com.example.quiz14.service.ifs.QuizService;
import com.example.quiz14.vo.BasicRes;
import com.example.quiz14.vo.CreateReq;
import com.example.quiz14.vo.DeleteReq;
import com.example.quiz14.vo.GetQuestionRes;
import com.example.quiz14.vo.SearchReq;
import com.example.quiz14.vo.SearchRes;
import com.example.quiz14.vo.UpdateReq;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/quiz")  // 👈 加上這行！
@CrossOrigin(origins = "https://quizfront-3bx3.onrender.com") // 允許前端網域
public class Quizcontroller {
	// 即預設的路徑會是 localhost:8080/quiz/
	// @RequestMapping(value = "quiz/") // 表示此 controller 底下的所有 API 路徑的前綴會是以 quiz/開頭
	// 若有使用的話， Controller 中的 API 路徑就可以拿掉 quiz/
	@Autowired
	private QuizService quizService;

	// http://localhost:8080/quiz/create
	@PostMapping(value = "/create")
	public BasicRes create(@Valid @RequestBody CreateReq req) throws Exception {
		return quizService.create(req);
	}
	

	@GetMapping(value = "/getAll") // 沒參數用 @GetMapping ， 有參數用 @PostMapping
	public SearchRes getAll() {
		return quizService.getAll();
	}

	@PostMapping(value = "/getAll")
	public SearchRes getAll(@RequestBody SearchReq req) {
		return quizService.getAll(req);
	}

	// API 的路徑: http://localhost:8080/quiz/getByQuizId?quizId=1
	@PostMapping(value = "/getByQuizId")
	public GetQuestionRes getQuestionsByQuizId(@RequestParam("quizId") int quizId) throws JsonProcessingException {
		return quizService.getQuestionByQuizId(quizId);
	}

	@PostMapping(value = "/update")
	public BasicRes update(@Valid @RequestBody UpdateReq req) throws Exception {
		return quizService.update(req);
	}

	@PostMapping(value = "/delete")
	public BasicRes delete(@Valid @RequestBody DeleteReq req) {
		return quizService.delete(req);
	}
}
