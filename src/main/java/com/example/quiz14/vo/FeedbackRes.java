package com.example.quiz14.vo;

import java.util.List;

public class FeedbackRes extends BasicRes {

	private String title;

	private String dirction;

	private List<FeedbackVo> feedbackVoList;

	public FeedbackRes() {
		super();
	}

	public FeedbackRes(int code, String message) {
		super(code, message);
	}

	public FeedbackRes(int code, String message, String title, String dirction, List<FeedbackVo> feedbackVoList) {
		super(code, message);
		this.title = title;
		this.dirction = dirction;
		this.feedbackVoList = feedbackVoList;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirction() {
		return dirction;
	}

	public void setDirction(String dirction) {
		this.dirction = dirction;
	}

	public List<FeedbackVo> getFeedbackVoList() {
		return feedbackVoList;
	}

	public void setFeedbackVoList(List<FeedbackVo> feedbackVoList) {
		this.feedbackVoList = feedbackVoList;
	}

}
