package com.example.File_Upload.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FileMetadataDTO {
	
	private Integer id;
	private String fileName;
	private Long fileSize;
	private String fileType;
	private LocalDateTime uploadedDate;
	private String filePath;

}
