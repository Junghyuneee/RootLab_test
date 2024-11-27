package com.example.File_Upload.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.File_Upload.dto.FileMetadataDTO;

@Mapper
public interface FileMapper {
	void inserFileMetadata(FileMetadataDTO file);
	
	FileMetadataDTO getFileById(@Param("id") Integer id);
	
	List<FileMetadataDTO> getAllFiles();

}
