package com.example.fileserver.dto;

public class FileResponse {
	
	private String fileName;
	
	public FileResponse(String fileName) {
		this.fileName = fileName;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
}
