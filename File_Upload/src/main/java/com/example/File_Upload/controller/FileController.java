package com.example.File_Upload.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.File_Upload.dto.FileMetadataDTO;
import com.example.File_Upload.service.FileService;

@RestController
@RequestMapping("/api/files")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	@PostMapping("/upload")
	public ResponseEntity<?> uploadFiles(@RequestParam("files") MultipartFile[] files){
		try {
			fileService.uploadFiles(files);
			return ResponseEntity.ok("Files uploaded successfully.");
		} catch(IOException e) {
			return ResponseEntity.status(500).body("File upload failed: " + e.getMessage());
		}
	}
	
	@GetMapping
	public ResponseEntity<List<FileMetadataDTO>> getAllFiles(){
		return ResponseEntity.ok(fileService.getAllFiles());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<byte[]> downloadFile(@PathVariable Integer id){
		try {
			FileMetadataDTO fileMetadata = fileService.getFileById(id);
			File file = new File(fileMetadata.getFilePath());
			byte[] fileContent = Files.readAllBytes(file.toPath());
			
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Disposition", "attachment; filename=" + fileMetadata.getFileName());
			
			return ResponseEntity.ok()
					.headers(headers)
					.body(fileContent);
		} catch(IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

}
