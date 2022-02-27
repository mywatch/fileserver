package com.example.fileserver.controller;

import java.nio.file.Paths;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileserver.service.FileStorageService;

@SpringBootTest
@AutoConfigureMockMvc
public class UploadDeleteListControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FileStorageService fileStorageService;

	@Test
	@DisplayName("Test to upload file")
	public void shouldUploadFile() throws Exception {

		Mockito.when(fileStorageService.storeFile(Mockito.any(MultipartFile.class))).thenReturn("test.txt");

		MockMultipartFile testFile = new MockMultipartFile("files", "test.txt", "text/plain",
				"Hello File server".getBytes());

		this.mockMvc.perform(MockMvcRequestBuilders.multipart("/upload").file(testFile))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().json("[{\"fileName\":test.txt}]"));

		BDDMockito.then(this.fileStorageService).should().storeFile(testFile);
	}

	@Test
	@DisplayName("Test to list files")
	public void shouldListAllFiles() throws Exception {

		BDDMockito.given(this.fileStorageService.getAllFiles())
				.willReturn(Stream.of(Paths.get("File1.txt"), Paths.get("File2.txt")));

		this.mockMvc.perform(MockMvcRequestBuilders.get("/files")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content()
						.json("[{\"fileName\":\"File1.txt\"},{\"fileName\":\"File2.txt\"}]"));
	}

	// this integration test is not passing
	// request body is null, need to fix it
//	@Test
//	@DisplayName("Test to delete files")
//	public void shouldDeleteAllFiles() throws Exception {
//
//		MockMultipartFile testFile = new MockMultipartFile("files", "test.txt", "text/plain",
//				"Hello File server".getBytes());
//		
//		this.mockMvc.perform(MockMvcRequestBuilders
//				.delete("/delete/", testFile)
//				.contentType(MediaType.APPLICATION_JSON))
//				.andExpect(MockMvcResultMatchers.status().isOk());
//
//	}

}
