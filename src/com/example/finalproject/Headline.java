package com.example.finalproject;

public class Headline {
	
	String title, link, source;
	
	public Headline(){
		
	}

	public Headline(String title, String link) {
		super();
		this.title = title;
		this.link = link;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
	
	

	@Override
	public String toString() {
		return title;
	}
	
	
	

}
