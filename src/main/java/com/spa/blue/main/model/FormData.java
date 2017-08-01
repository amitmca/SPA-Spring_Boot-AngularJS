package com.spa.blue.main.model;


import org.springframework.web.multipart.MultipartFile;

public class FormData {

	private MultipartFile file;

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
