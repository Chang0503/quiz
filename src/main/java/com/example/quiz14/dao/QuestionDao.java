package com.example.quiz14.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quiz14.entity.Question;
import com.example.quiz14.entity.QuestionId;


import jakarta.transaction.Transactional;

@Repository
public interface QuestionDao extends JpaRepository<Question, QuestionId> {

	@Transactional
	@Modifying
	@Query(value = "INSERT INTO question (quiz_id, question_id, question, type, required, options) "//
			+ " VALUES (:quizId, :questionId, :question, :type, :required, :options)", nativeQuery = true)
	public void insert(//
			@Param("quizId") int quizId, //
			@Param("questionId") int questionId, //
			@Param("question") String question, //
			@Param("type") String type, //
			@Param("required") boolean required, //
			@Param("options") String options);

	// 因為表 quiz 中的 id 是流水號，所以取 id 最大值表示取最新 新增的一筆資料的 id
	@Query(value = "SELECT * FROM question WHERE quiz_id = ?1", nativeQuery = true)
    public List<Question> getByQuizId(int quizId);

	@Transactional
	@Modifying
	@Query(value = "DELETE FROM question WHERE quiz_id = ?1 ", nativeQuery = true)
	public void deleteByQuizId(int quizId);
	
	@Transactional
	@Modifying
	@Query(value = "DELETE FROM question WHERE quiz_id In (?1)", nativeQuery = true)
	public void deleteByQuizIdIn(List<Integer> quizIdList);
}
