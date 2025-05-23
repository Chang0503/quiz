package com.example.quiz14.vo;

import java.util.List;

import com.example.quiz14.constants.ConstantsMessage;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

public class FillinReq {

	@NotBlank(message = ConstantsMessage.USER_NAME_IS_NECESSARY)
	private String userName;

	@NotBlank(message = ConstantsMessage.Phone_IS_NECESSARY)
	private String phone;

	@NotBlank(message = ConstantsMessage.EMAIL_IS_NECESSARY)
	private String email;

	@Min(value = 18, message = ConstantsMessage.USER_AGE_ERROR)//設定至少18歲才能填
	private int age;

	@Min(value = 1, message = ConstantsMessage.PARAM_QUIZ_ID_ERROR)
	private int quizId;

	@Valid
	@NotEmpty(message = ConstantsMessage.ANSWER_IS_NECESSARY)
	private List<QuesIdAnswerVo> answerVoList;

	public String getUserName() {
		return userName;
	}

	public void setUesrName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public int getQuizId() {
		return quizId;
	}

	public void setQuizId(int quizId) {
		this.quizId = quizId;
	}

	public List<QuesIdAnswerVo> getAnswerVoList() {
		return answerVoList;
	}

	public void setAnswerVoList(List<QuesIdAnswerVo> answerVoList) {
		this.answerVoList = answerVoList;
	}

}
