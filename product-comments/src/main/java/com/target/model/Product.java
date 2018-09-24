package com.target.model;

import java.util.HashMap;
import java.util.Map;

public class Product {

	private String id;
	private Map<String, Comment> comments;

	public Product() {
		setComments(new HashMap<String, Comment>());
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Map<String, Comment> getComments() {
		return comments;
	}

	public void setComments(Map<String, Comment> comments) {
		this.comments = comments;
	}
}
