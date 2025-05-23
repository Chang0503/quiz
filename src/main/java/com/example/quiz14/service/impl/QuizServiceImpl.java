package com.example.quiz14.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.example.quiz14.constants.QuestionType;
import com.example.quiz14.constants.ResMessage;
import com.example.quiz14.dao.QuestionDao;
import com.example.quiz14.dao.QuizDao;
import com.example.quiz14.entity.Question;
import com.example.quiz14.entity.Quiz;
import com.example.quiz14.service.ifs.QuizService;

import com.example.quiz14.vo.BasicRes;
import com.example.quiz14.vo.CreateReq;
import com.example.quiz14.vo.DeleteReq;
import com.example.quiz14.vo.GetQuestionRes;
import com.example.quiz14.vo.QuestionVo;
import com.example.quiz14.vo.SearchReq;
import com.example.quiz14.vo.SearchRes;
import com.example.quiz14.vo.UpdateReq;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class QuizServiceImpl implements QuizService {
	private ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private QuizDao quizDao;

	@Autowired
	private QuestionDao questionDao;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public BasicRes create(CreateReq req) throws Exception {
		// 檢查日期 1. 開始日期不能比當天早(過去問卷) 2. 開始日期不能比結束時間晚
		// 排除法: 排除 1.開始日期比當天早(req 中已驗證) 2.開始日期比結束日期晚
		if (req.getStartDate().isAfter(req.getEndDate())) {
			return new BasicRes(ResMessage.DATE_FORMAT_ERROR.getCode(), //
					ResMessage.DATE_FORMAT_ERROR.getMessage());
		}
		// 檢查問題
		List<QuestionVo> questionVos = req.getQuestionVos();
		BasicRes checkres = checkQuestions(questionVos);
		if (checkres != null) {
			return checkres;
		}
		try {
			// 新增問卷
			quizDao.create(req.getTitle(), req.getDirection(), req.getStartDate(), req.getEndDate(), //
					req.isPublished());
			// 取得上面新增問卷 quiz 的 id
			// 雖然透過 @Transactional 尚未將資料交(commit)進自料庫，但實際上SQL語法已經執行完畢，
			// 依然可以取得對應的值
			int quizId = quizDao.selectMaxId();
			// 新增問題
			for (QuestionVo vo : questionVos) {
				// 將 vo 中 options 的資料型態從 List<String> 轉成 String (沒辦法用 toString 會是記憶體位置)
				String optionsStr = mapper.writeValueAsString(vo.getOptions());
				questionDao.insert(quizId, vo.getQuestionId(), vo.getQuestion(), //
						vo.getType(), vo.isRequired(), optionsStr);
			}
		} catch (Exception e) {
			// 將 exception 拋出
			throw e;
		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());
	}

	private BasicRes checkQuestions(List<QuestionVo> questionVos) {
		for (QuestionVo item : questionVos) {
			String type = item.getType();
		//檢查問題型態是否符合規範
		if(!QuestionType.checkAllType(type)) {
			return new BasicRes(ResMessage.QUESTION_TYPE_ERROR.getCode(), //
					ResMessage.QUESTION_TYPE_ERROR.getMessage());
		}
		// 1. 單選或多選 --> 選項至少要有2個 2. 問答 --> 選項不能有值
		
			if (QuestionType.checkChoiceType(type)) {
				// QuesType.checkChoiceType(type) == true 表示是單選或多選
				// 排除: 選項是 null 或 選項小於2 Options = null || OptionsSize > 2
				if (item.getOptions() == null || item.getOptions().size() < 2) { // 選擇題的選項至少要2個
					return new BasicRes(ResMessage.OPTIONS_INSUFFICIENT.getCode(), //
							ResMessage.OPTIONS_INSUFFICIENT.getMessage());
				}
			}
			// 2. 問答 --> 選項不能有值，表示選項可以是 null 或是 不能有內容
			if (type.equalsIgnoreCase(QuestionType.TEXT.getType())) {
				// 排除法: 排除 非 null 且 內容的 size > 0
				if (item.getOptions() != null && item.getOptions().size() > 0) {
					return new BasicRes(ResMessage.TEXT_HAS_OPTIONS_ERROR.getCode(), //
							ResMessage.TEXT_HAS_OPTIONS_ERROR.getMessage());
				}
			}
		}
		return null;
	}

	@Override
	public SearchRes getAll() {
		return new SearchRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), quizDao.selectAll());
	}

	@Override
	public GetQuestionRes getQuestionByQuizId(int quizId) throws JsonProcessingException {
		List<Question> questionList = questionDao.getByQuizId(quizId);
		List<QuestionVo> voList = new ArrayList<>();
		// 把每個 Question 中的值設定到 QuestionVo 裡
		for (Question item : questionList) {
			QuestionVo vo = new QuestionVo();
			vo.setQuizId(item.getQuizId());
			vo.setQuestionId(item.getQuestionId());
			vo.setQuestion(item.getQuestion());
			vo.setType(item.getType());
			vo.setRequired(item.isRequired());
			// 要把原本資料型態是 List<String> 的 String字串 轉回成原本的格式 List<String>
			try {
				List<String> options = mapper.readValue(item.getOptions(), new TypeReference<>() {
				});
				vo.setOptions(options);
			} catch (JsonProcessingException e) {
				throw e;
			}
			voList.add(vo);
		}

		return new GetQuestionRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), voList);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public BasicRes update(UpdateReq req) throws Exception {
		// 檢查 quiz 的 id 和 question 中的 quizId 是否一致
		List<QuestionVo> questionVoList = req.getQuestionVos();
		for (QuestionVo vo : questionVoList) {
			if (vo.getQuizId() != req.getQuizId()) {
				return new BasicRes(ResMessage.QUIZ_ID_MISMATCH.getCode(), //
						ResMessage.QUIZ_ID_MISMATCH.getMessage());
			}
		}
		// 檢查日期 1. 開始日期不能比當天早(過去問卷) 2. 開始日期不能比結束時間晚
		// 排除法: 排除 1.開始日期比當天早(req 中已驗證) 2.開始日期比結束日期晚
		if (req.getStartDate().isAfter(req.getEndDate())) {
			return new BasicRes(ResMessage.DATE_FORMAT_ERROR.getCode(), //
					ResMessage.DATE_FORMAT_ERROR.getMessage());
		}
		// 檢查問題
		BasicRes checkres = checkQuestions(questionVoList);
		if (checkres != null) {
			return checkres;
		}
		// 檢查問卷是否能更新: 問卷狀態進行中或已結束 都不能更新
		Quiz quiz = quizDao.selectById(req.getQuizId());
		if (quiz == null) {
			return new BasicRes(ResMessage.QUIZ_NOT_FOUND.getCode(), //
					ResMessage.QUIZ_NOT_FOUND.getMessage());
		}
		if (quiz.isPublished()) {// 問卷已發布
			LocalDate nowDate = LocalDate.now();
			LocalDate startDate = quiz.getStartDate();
			// 只要判斷當前日期是否大於等於開始日期
			// 進行中: 當前日期 >= 開始日期
			// 已結束: 當前日期 > 結束日期 --> 也表示 當前日期 > 開始日期
			// nowDate.isEqual(startDate) || nowDate.isAfter(startDate) -->
			// !nowDate.isBefore(startDate) : 當前日期 >= 開始時間
			// 當前日期大於開始日期 --> 當前日期不在開始日期之前
			if (!nowDate.isBefore(startDate)) {
				return new BasicRes(ResMessage.CAN_NOT_UPDATE.getCode(), //
						ResMessage.CAN_NOT_UPDATE.getMessage());
			}
		}
		try {
			// 更新問卷
			int res = quizDao.update(req.getQuizId(), req.getTitle(), req.getDirection(), //
					req.getStartDate(), req.getEndDate(), req.isPublished());
			// 因為問卷的 id 是 PK ，所以更新成功回傳是 1 ，沒資料更新成功則回傳 0
			if (res != 1) {
				return new BasicRes(ResMessage.UPDATE_FAIL.getCode(), //
						ResMessage.UPDATE_FAIL.getMessage());
			}
			// 更新問題: 步驟1.刪除相同 quizId 的問題 、 步驟2.新增問題
			questionDao.deleteByQuizId(req.getQuizId());
			// 新增問題
			for (QuestionVo vo : questionVoList) {
				// 將 vo 中 options 的資料型態從 List<String> 轉成 String (沒辦法用 toString 會是記憶體位置)
				String optionsStr = mapper.writeValueAsString(vo.getOptions());
				questionDao.insert(vo.getQuestionId(), vo.getQuestionId(), vo.getQuestion(), //
						vo.getType(), vo.isRequired(), optionsStr);
			}
		} catch (Exception e) {
//			 將 exception 拋出
			throw e;
		}
		return new BasicRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public BasicRes delete(DeleteReq req) {
		// 檢查問卷是否能刪除: 問卷狀態進行中或已結束 都不能刪除
		List<Quiz> quizList = quizDao.selectByIdIn(req.getIdList());
		for (Quiz item : quizList) {
			LocalDate nowDate = LocalDate.now();
			LocalDate startDate = item.getStartDate();
			if (!nowDate.isBefore(startDate)) {
				return new BasicRes(ResMessage.CAN_NOt_DELETE.getCode(), //
						ResMessage.CAN_NOt_DELETE.getMessage());
			}
		}
		// 刪除問卷和問題
		quizDao.deleteByIdIn(req.getIdList());
		questionDao.deleteByQuizIdIn(req.getIdList());
		return new BasicRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage());

	}

	// 搜尋
	@Override
	public SearchRes getAll(SearchReq req) {
		// 轉換參數值
		String quizName = req.getQuizName();

		if (!StringUtils.hasText(quizName)) {
			// 若quizName是null或空字串或全空白字串 設定成空字串
			quizName = "";
		}
		LocalDate startDate = req.getStartDate();
		// 設定 startDate一個很早的時間
		if (startDate == null) {
			startDate = LocalDate.of(1970, 1, 1);
		}
		LocalDate endDate = req.getEndDate();
		if (endDate == null) {
			// 設定 endDate一個很久之後的未來時間
			endDate = LocalDate.of(2999, 1, 1);
		}
		List<Quiz> resList = new ArrayList<>();
		if (req.isForFrontEnd()) {
			resList = quizDao.selectAllforFront(quizName, startDate, endDate);
		} else {
			resList = quizDao.selectAll(quizName, startDate, endDate);

		}
		return new SearchRes(ResMessage.SUCCESS.getCode(), //
				ResMessage.SUCCESS.getMessage(), resList);
	}

}
