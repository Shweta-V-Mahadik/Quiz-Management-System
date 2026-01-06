package com.app.models;

public class Quiz {

	public int id;
	public String title;
	public int creatorId;
	public String date;
	public Quiz(int id, String title, int creatorId) {
		super();
		this.id = id;
		this.title = title;
		this.creatorId = creatorId;
	}

	public Quiz() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCreatorId() {
		return creatorId;
	}
	
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
}
