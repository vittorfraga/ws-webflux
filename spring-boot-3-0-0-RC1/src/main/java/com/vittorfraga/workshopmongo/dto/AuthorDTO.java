package com.vittorfraga.workshopmongo.dto;

public class AuthorDTO {
	
	private String id;
	private String name;

	public AuthorDTO() {
	}

	public AuthorDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}