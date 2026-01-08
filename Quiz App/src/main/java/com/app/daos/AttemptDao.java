package com.app.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.models.Question;
import com.app.models.Quiz;
import com.app.utils.DbUtil;

public class AttemptDao implements AutoCloseable{

	Connection connection = null;
	
	public AttemptDao() throws SQLException {
		connection = DbUtil.getConnection();
	}
	
	//Displaying quizzes to students
	public List<Quiz> displayStudentQuiz(int student_id) throws SQLException{
		
		List<Quiz> quiz = new ArrayList<>();
		
		String sql = "SELECT * FROM quizzes WHERE quiz_id NOT IN (SELECT q.quiz_id FROM quizzes q INNER JOIN quiz_attempts a ON q.quiz_id = a.quiz_id WHERE a.student_id = ?)";
		try(PreparedStatement selectStatement = connection.prepareStatement(sql)) {
			selectStatement.setInt(1, student_id);
			ResultSet rs = selectStatement.executeQuery();
			while(rs.next()) {
				Quiz q = new Quiz();
				q.setId(rs.getInt(1));
				q.setTitle(rs.getString(2));
				q.setCreatorId(rs.getInt(3));
				q.setDate(rs.getString(4));
				quiz.add(q);
			}
		}
		return quiz;
		
	}
	
	//Take quiz
	public List<Question> takeQuiz(int id) throws SQLException{
		
		List<Question> questionList = new ArrayList<>();
		String sql = "SELECT * FROM questions WHERE quiz_id = ?";
		try(PreparedStatement selectStatement = connection.prepareStatement(sql)){
			selectStatement.setInt(1, id);
			ResultSet rs = selectStatement.executeQuery();
			
			while(rs.next()) {
				
				Question q = new Question();
				q.setId(rs.getInt(1));
				q.setQuizId(rs.getInt(2));
				q.setText(rs.getString(3));
				q.setA(rs.getString(4));
				q.setB(rs.getString(5));
				q.setC(rs.getString(6));
				q.setD(rs.getString(7));
				q.setCorrect(rs.getString(8).charAt(0));
				questionList.add(q);
			}
		}
		
		return questionList;
		
	}
	
	//Inserting quiz attempts
	public void insertQuizAttempts(int quiz_id, int student_id, int final_score, int total_questions) throws SQLException {
		
		String sql = "INSERT INTO quiz_attempts (quiz_id, student_id, final_score, total_questions) VALUES (?, ?, ?, ?)";
		try(PreparedStatement insertStatement = connection.prepareStatement(sql)){
			insertStatement.setInt(1, quiz_id);
			insertStatement.setInt(2, student_id);
			insertStatement.setInt(3, final_score);
			insertStatement.setInt(4, total_questions);
			insertStatement.executeUpdate();
		}
	}
	
	//Displaying score to Student
	public List<Object[]> displayScoreToStudent(int student_id) throws SQLException{
		
		List<Object[]> object = new ArrayList<>();
		String sql =  "SELECT qz.title, a.final_score, a.total_questions FROM quizzes qz INNER JOIN quiz_attempts a ON qz.quiz_id = a.quiz_id WHERE student_id = ?";
		try(PreparedStatement selectStatement = connection.prepareStatement(sql)){
			ResultSet rs = selectStatement.executeQuery();
			
			while(rs.next()) {
				String title = rs.getString(1);
				int score = rs.getInt(2);
				int total_ques = rs.getInt(3);
				object.add(new Object[] {title, score, total_ques});
			}
		}
		
		return object;
		
	}
	
	//Displaying score to admin
	public List<Object[]> displayScoreToAdmin() throws SQLException{
		
		List<Object[]> object = new ArrayList<>();
		String sql =  "SELECT qz.title,a.student_id, a.final_score, a.total_questions FROM quizzes qz INNER JOIN quiz_attempts a ON qz.quiz_id = a.quiz_id";
		try(PreparedStatement selectStatement = connection.prepareStatement(sql)){
			ResultSet rs = selectStatement.executeQuery();
			
			while(rs.next()) {
				String title = rs.getString(1);
				int stud_id = rs.getInt(2);
				int score = rs.getInt(3);
				int total_ques = rs.getInt(4);
				object.add(new Object[] {title, stud_id, score, total_ques});
			}
		}
		
		return object;
		
	}
	
	
	@Override
	public void close() throws Exception {
		if(connection != null) {
			connection.close();
			connection = null;
		}
		
	}

}
