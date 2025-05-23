package com.example.quiz14.constants;

public enum ResMessage {
	SUCCESS(200, "Success!!"), //
	DATE_FORMAT_ERROR(400, "Date format error!!"), //
	OPTIONS_INSUFFICIENT(400, "Options insufficient"), //
	TEXT_HAS_OPTIONS_ERROR(400, "Text has options error"), //
	QUIZ_ID_MISMATCH(400, "Quiz id mismatch!"), //
	QUIZ_NOT_FOUND(404, "Quiz not found!"), //
	CAN_NOT_UPDATE(400, "Quiz can not update!"), //
	CAN_NOt_DELETE(400, "Quiz can not delete!"), //
	UPDATE_FAIL(400, "Quiz update fail!"), //
	QUESTION_TYPE_ERROR(400, "question type error!"), //
	ANSWER_IS_REQUIRED(400, " Answer is required!"), //
	OPTION_ANSWER_MISMATCH(400, "Option answer mismatch"), //
	PHONE_DUPLICATED(400, "phone duplicaed"), //
	QUIZ_ID_ERROR(400, "Quiz id error!!");
	
	

	private int code;

	private String message;

	private ResMessage(int code, String message) {
		this.code = code;
		this.message = message;

	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
