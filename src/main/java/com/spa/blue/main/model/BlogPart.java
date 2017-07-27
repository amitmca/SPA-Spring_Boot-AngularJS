package com.spa.blue.main.model;

import javax.persistence.Lob;

public class BlogPart {

	
	private String topic;
	
	
	private String title;
		
	@Lob
	private String content;



	public String getTopic() {
		return topic;
	}



	public void setTopic(String topic) {
		this.topic = topic;
	}



	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getContent() {
		return content;
	}



	public void setContent(String content) {
		this.content = content;
	}
	
}
