package com.example.quiz14.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.quiz14.entity.Feedback;
import com.example.quiz14.entity.FeedbackId;
import com.example.quiz14.vo.FeedbackDto;

import jakarta.transaction.Transactional;

	@Repository
	public interface FeedbackDao extends JpaRepository<Feedback, FeedbackId> {

	    @Query(value = "SELECT COUNT(phone) FROM feedback WHERE phone = ?1 AND quiz_id = ?2", //
	            nativeQuery = true)
	    public int selectCountByPhoneAndQuizId(String phone, int quizId);

	    @Transactional
	    @Modifying
	    @Query(value = "INSERT INTO feedback " //
	            + " (user_name, phone, email, age, quiz_id, ques_id, answer, fillin_date) "//
	            + " VALUES "//
	            + " (:userName, :phone, :email, :age, :quizId, :quesId, :answer, :fillinDate)", //
	            nativeQuery = true)
	    public void insert(@Param("userName") String userName, //
	            @Param("phone") String phone, //
	            @Param("email") String email, //
	            @Param("age") int age, //
	            @Param("quizId") int quizId, //
	            @Param("quesId") int quesId, //
	            @Param("answer") String answer, //
	            @Param("fillinDate") LocalDate fillinDate);

	
	//nativeQuery = false時 SQL語法中
	//select的欄位名稱會變成FeedbackDto中的屬性變數名稱 
	//on後面的欄位名稱是Entity class中的屬性變數名稱
	//表的名稱會變成Entity class中的屬性變數名稱
	//select後面的欄位要透過new建構方法的方式來塞值
	//FeedbackDto要給定完整的路徑:com.example.quiz14.vo.FeedbackDto
	@Query(value = "select new com.example.quiz14.vo.FeedbackDto(Qz.title, Qz.direction, " //
			+ " Qu.questionId, Qu.question, " //
			+ " F.userName, F.phone, F.email, F.age, F.answer, F.fillinDate) " //
			+ " from Quiz as Qz " //
			+ " join Question as Qu on Qz.id = Qu.quizId" //
			+ " join Feedback as F on Qu.quizId = F.quizId where Qz.id = ?1", //
			nativeQuery = false)
	public List<FeedbackDto> selectJoinByQuizId(int quizId);
	
	@Query(value = "select * from feedback where quiz_id = ?1", //
			 nativeQuery = true)
	public List<Feedback> selectByQuizId(int quizId);
	
}
