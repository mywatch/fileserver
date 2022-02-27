package com.example.fileserver.controller;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileserver.dto.FileResponse;
import com.example.fileserver.service.FileStorageService;

@RestController
public class UploadDeleteListController {

	private FileStorageService fileStorageService;

	public UploadDeleteListController(FileStorageService fileStorageService) {
		this.fileStorageService = fileStorageService;
	}

	@PostMapping("/upload")
	List<FileResponse> multipleFileUpload(@RequestParam("files") MultipartFile[] files) {

		if (files.length > 6) {
			throw new RuntimeException("System accepts less than 6 files at once");
		}

		List<FileResponse> uploadResponseList = new ArrayList<>();

		Arrays.asList(files).stream().forEach(file -> {
			String fileName = fileStorageService.storeFile(file);

			FileResponse response = new FileResponse(fileName);

			uploadResponseList.add(response);
		});

		return uploadResponseList;
	}

	@GetMapping("/files")
	List<FileResponse> getFilesList() {

		List<FileResponse> fileList = new ArrayList<>();

		List<String> listOfFiles = fileStorageService.getAllFiles().map(path -> path.getFileName())
				.map(Object::toString).collect(Collectors.toList());

		for (String file : listOfFiles) {
			String fileName = file;

			FileResponse response = new FileResponse(fileName);

			fileList.add(response);
		}
		return fileList;

	}

	@DeleteMapping("/delete")
	List<FileResponse> deleteFiles(@RequestParam("files") MultipartFile[] files) {

		if (files.length > 6) {
			throw new RuntimeException("System accepts less than 6 files at once");
		}

		List<FileResponse> deleteFileList = new ArrayList<>();

		Arrays.asList(files).stream().forEach(file -> {
			String fileName = null;
			FileResponse response = null;

			try {
				fileName = fileStorageService.getFileName(file);
			} catch (NoSuchFileException e) {
				e.printStackTrace();
			}

			if (fileStorageService.deleteFile(file)) {
				response = new FileResponse(fileName + " successfully deleted");
			} else {
				response = new FileResponse(fileName + " not found");
			}

			deleteFileList.add(response);
		});

		return deleteFileList;

	}

}