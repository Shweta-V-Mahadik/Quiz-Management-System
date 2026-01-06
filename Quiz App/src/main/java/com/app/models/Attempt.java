package com.app.models;

import java.time.LocalDateTime;

public class Attempt {

	public int id;
	public int quizId;
	public int studentId;
	public int score;
	public int total;
	public LocalDateTime attemptTime;
	
	public Attempt(int id, int quizId, int studentId, int score, int total, LocalDateTime attemptTime) {
		super();
		this.id = id;
		this.quizId = quizId;
		this.studentId = studentId;
		this.score = score;
		this.total = total;
		this.attemptTime = attemptTime;
	}
	
	public Attempt() {
		super();
	}
}
