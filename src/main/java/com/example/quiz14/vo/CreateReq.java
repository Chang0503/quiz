package com.example.quiz14.vo;

import java.time.LocalDate;
import java.util.List;

import com.example.quiz14.constants.ConstantsMessage;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateReq {
	@NotBlank(message = ConstantsMessage.PARAM_TITLE_ERROR)
	private String title;

	@NotBlank(message = ConstantsMessage.PARAM_DIRECTION_ERROR)
	private String direction;

	@FutureOrPresent(message = ConstantsMessage.PARAM_START_DATE_ERROR) // 日期只能是未來或當天
	@NotNull(message = ConstantsMessage.PARAM_START_DATE_ERROR)
	private LocalDate startDate;

	@NotNull(message = ConstantsMessage.PARAM_END_DATE_ERROR)
	private LocalDate endDate;

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	private boolean published;

	@Valid
	private List<QuestionVo> questionVos;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}



	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public List<QuestionVo> getQuestionVos() {
		return questionVos;
	}

	public void setQuestionVos(List<QuestionVo> questionVos) {
		this.questionVos = questionVos;
	}

}
