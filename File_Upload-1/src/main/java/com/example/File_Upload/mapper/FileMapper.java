package com.example.File_Upload.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.File_Upload.dto.FileMetadataDTO;

@Mapper
public interface FileMapper {
	// 파일 메타데이터 삽입
	void insertFileMetadata(FileMetadataDTO fileMetadata);
	// 모든 파일 메타데이터 조회
    List<FileMetadataDTO> getAllFiles();
    // 파일 ID로 조회
    FileMetadataDTO getFileById(Integer id);
    // 파일 메타데이터 삭제(파일명으로 삭제)
    void deleteFileBySavedFileName(String savedFileName);

}
