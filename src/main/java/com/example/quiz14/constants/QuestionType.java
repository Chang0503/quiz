package com.example.quiz14.constants;

public enum QuestionType {

	SINGLE("Single"), // 單選
	MULTI("Multi"), // 多選
	TEXT("Text"); // 文字

	private String type;

	private QuestionType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static boolean checkChoiceType(String input) {
		if (input.equalsIgnoreCase(QuestionType.SINGLE.getType())
				|| input.equalsIgnoreCase(QuestionType.MULTI.getType())) {
			return true;
		}
		return false;
	}
	
	public static boolean checkAllType(String input) {
		for(QuestionType type : values()) {
			if(input.equalsIgnoreCase(type.getType())) {
				return true;
			}
		}
		return false;
	}

}
