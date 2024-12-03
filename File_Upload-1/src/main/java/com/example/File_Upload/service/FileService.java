package com.example.File_Upload.service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.File_Upload.dto.FileMetadataDTO;
import com.example.File_Upload.mapper.FileMapper;

@Service
public class FileService {
	
	 @Value("${file.upload-dir}")
	    private String uploadDir;

	 	@Autowired
	    private final FileMapper fileMapper;

	    public FileService(FileMapper fileMapper) {
	        this.fileMapper = fileMapper;
	    }

	    // 단일 파일 업로드
	    public FileMetadataDTO uploadFile(MultipartFile file) throws IOException {
	        String originalFileName = file.getOriginalFilename();
	        String savedFileName = UUID.randomUUID() + "_" + originalFileName;
	        String filePath = uploadDir + savedFileName;

	        File destination = new File(filePath);
	        destination.getParentFile().mkdirs();
	        file.transferTo(destination);

	        FileMetadataDTO metadata = new FileMetadataDTO();
	        metadata.setOriginalFileName(originalFileName);
	        metadata.setSavedFileName(savedFileName);
	        metadata.setFilePath(filePath);
	        metadata.setFileSize(file.getSize());
	        metadata.setUploadDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

	        fileMapper.insertFileMetadata(metadata);
	        return metadata;
	    }

	    // 멀티 파일 업로드
	    public List<FileMetadataDTO> uploadMultipleFiles(List<MultipartFile> files) throws IOException {
	        List<FileMetadataDTO> uploadedFiles = new ArrayList<>();
	        for (MultipartFile file : files) {
	            uploadedFiles.add(uploadFile(file)); // 단일 파일 업로드 메소드 호출
	        }
	        return uploadedFiles;
	    }
	    
	    // 파일 삭제
	    public void deleteFile(String savedFileName) {
	    	// 파일 경로에서 파일 삭제
	    	String filePath = uploadDir + savedFileName;
	    	File file = new File(filePath);
	    	if(file.exists()) {
	    		file.delete(); // 파일 삭제
	    	
	    	// 데이터페이스에서 메타데이터 삭제
	    	fileMapper.deleteFileBySavedFileName(savedFileName);
	    	}
	    }

	    // 파일 목록 조회
	    public List<FileMetadataDTO> getAllFiles() {
	        return fileMapper.getAllFiles();
	    }
	    
	 // 특정 파일 조회 (ID로)
	    public FileMetadataDTO getFileById(Integer id) {
        return fileMapper.getFileById(id);
    }

    

}
