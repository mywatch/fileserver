package com.example.fileserver.service;

import java.util.stream.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

	private Path fileStoragePath;
	private String fileStorageLocation;

	public FileStorageService(@Value("${file.storage.location:temp}") String fileStorageLocation) {

		this.fileStorageLocation = fileStorageLocation;
		fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();

		try {
			Files.createDirectories(fileStoragePath);
		} catch (IOException e) {
			throw new RuntimeException("Issue in creating file storage location");
		}

	}

	public String storeFile(MultipartFile file) {

		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		Path filePath = Paths.get(fileStoragePath + File.separator + fileName);

		try {
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new RuntimeException("File upload failed", e);
		}
		return fileName;
	}

	public Stream<Path> getAllFiles() {
		try {
			return Files.walk(this.fileStoragePath, 1).filter(Files::isRegularFile).map(p -> p.toAbsolutePath());
		} catch (Exception e) {
			throw new RuntimeException("Could not load the files!");
		}
	}

	public boolean deleteFile(MultipartFile file) {
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());

		Path filePath = Paths.get(fileStoragePath + File.separator + fileName);
		System.out.println("The filepath passed to boolean function is: " + fileName);
		boolean result = false;

		try {
			if (Files.deleteIfExists(filePath)) {
				result = true;
			}
		} catch (NoSuchFileException x) {
			System.err.format("%s: no such" + " file", filePath);
		} catch (IOException e) {
			throw new RuntimeException("File not found ", e);
		}

		return result;

	}

	public String getFileName(MultipartFile file) throws NoSuchFileException {
		try {
			return StringUtils.cleanPath(file.getOriginalFilename());
		} catch (Exception e) {
			throw new RuntimeException("Could not find fileName: %s" + file);
		}
	}

}
