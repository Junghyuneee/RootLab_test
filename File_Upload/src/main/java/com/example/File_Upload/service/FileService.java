package com.example.File_Upload.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.File_Upload.dto.FileMetadataDTO;
import com.example.File_Upload.mapper.FileMapper;

@Service
public class FileService {

	@Value("${file.upload-dir}")
	private String uploadDir;
	
	private final FileMapper fileMapper;
	
	// 허용된 확장자 목록
    private static final List<String> ALLOWED_EXTENSIONS = List.of("jpg", "png", "pdf", "pptx");
	
	public FileService(FileMapper fileMapper) {
		this.fileMapper = fileMapper;
	}
	
	public void uploadFiles(MultipartFile[] files) throws IOException{
		for(MultipartFile file : files) {
			if(!file.isEmpty()) {
				String originalFileName = file.getOriginalFilename();
				
				//파일 이름과 확장자 검증
				if(originalFileName == null || !originalFileName.contains(".")) {
					throw new IOException("Invalid file: " + originalFileName);
				}
				
				String fileExtension = originalFileName.substring(originalFileName.lastIndexOf(".") + 1).toLowerCase();
				
				//확장자 검증
				if(!ALLOWED_EXTENSIONS.contains(fileExtension)) {
					throw new IOException("Unsupported file type: " + fileExtension);
				}
				
				//고유한 파일명 생성
				String uuid = UUID.randomUUID().toString();
				String savedFileName = uuid + "_" + originalFileName;
				String filePath = uploadDir + File.separator + savedFileName;
				
				// 파일 저장
				File destination = new File(filePath);
				destination.getParentFile().mkdirs();
				file.transferTo(destination);
				
				// 메타데이터 저장
				FileMetadataDTO metadata = new FileMetadataDTO();
				metadata.setFileName(originalFileName);
				metadata.setFilePath(filePath);
				metadata.setFileSize(file.getSize());
				metadata.setFileType(file.getContentType());
				
				try {
				fileMapper.insertFileMetadata(metadata);
			}catch (Exception e) {
				e.printStackTrace();
				throw new IOException("Failed to insert file metadata into database.", e);
			}
		}
	}
	}
	
	//모든 파일 메타데이터 조회
	public List<FileMetadataDTO> getAllFiles(){
		return fileMapper.getAllFiles();
	}
	
	//파일 ID로 메타데이터 조회
	public FileMetadataDTO getFileById(Integer id) {
		return fileMapper.getFileById(id);
	}
}
