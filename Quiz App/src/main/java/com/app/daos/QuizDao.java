package com.app.daos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.models.Quiz;
import com.app.utils.DbUtil;

public class QuizDao implements AutoCloseable{

	Connection connection = null;
	
	public QuizDao() throws SQLException {
		connection = DbUtil.getConnection();
	}
	
	//Insert Quiz Dao
	public int insertQuiz(Quiz quiz) throws SQLException {
		
		String sql = "INSERT INTO quizzes (title, creator_id) VALUES (?, ?)";
		try(PreparedStatement insertStatement = connection.prepareStatement(sql)){
			insertStatement.setString(1, quiz.getTitle());
			insertStatement.setInt(2,quiz.getCreatorId());
			insertStatement.executeUpdate();
			
			ResultSet rs = insertStatement.getGeneratedKeys();
			if(rs.next()) {
				return rs.getInt(1);
			}
		}
		return 0;
		
	}
	
	//Display quizzes to admin 
	public List<Quiz> displayQuizzes() throws SQLException{
		
		List<Quiz> quiz = new ArrayList<>();
		String sql = "SELECT * FROM quizzes";
		try(PreparedStatement selectStatement = connection.prepareStatement(sql)){
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
	
	//Delete quiz
	public int deleteQuiz(int quizId) throws SQLException {
		int res = 0;
		String sql1 = "DELETE q FROM questions q INNER JOIN quizzes qz ON q.quiz_id = qz.quiz_id WHERE q.quiz_id = ?";
		
		try(PreparedStatement deleteStatement = connection.prepareStatement(sql1)){
			deleteStatement.setInt(1, quizId);
			deleteStatement.executeUpdate();
			
			String sql2 = "DELETE FROM quizzes WHERE quiz_id = ?";
			try(PreparedStatement delStatement = connection.prepareStatement(sql2)){
				delStatement.setInt(1, quizId);
				res = delStatement.executeUpdate();
			}
		}
		return res;
		
	}
	
	//Find quiz by ID
	public Quiz findByQuizId(int id) throws SQLException {
		
		String sql = "SELECT * FROM quizzes WHERE quiz_id = ?";
		Quiz quiz = new Quiz();
		try(PreparedStatement selectStatement = connection.prepareStatement(sql)){
			selectStatement.setInt(1, id);
			ResultSet rs = selectStatement.executeQuery();
			while(rs.next()) {
				quiz.setId(rs.getInt(id));
				quiz.setTitle(rs.getString(2));
				quiz.setCreatorId(rs.getInt(3));
				return quiz;
			}
		}
		return null;
		
	}
	
	@Override
	public void close() throws Exception {
		if(connection != null) {
			connection.close();
			connection = null;
		}
		
	}
	
	
	
}
