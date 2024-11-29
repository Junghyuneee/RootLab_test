package com.example.File_Upload.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.File_Upload.dto.FileMetadataDTO;
import com.example.File_Upload.service.FileService;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class FileDownloadController {
	
	@Autowired
	private FileService fileService;
	
	// 파일 목록 보여주기
	@RequestMapping("/fileDownloadList")
	public ModelAndView fileDownloadList(ModelAndView mv) {
		//File path = new File("/Library/springWorkspace/upload/");
		//String[] fileList = path.list();
		
		//DB에서 파일 메타데이터 조회
		List<FileMetadataDTO> fileList = fileService.getAllFiles();
		
		// 파일 목록을 모델에 추가
		mv.addObject("fileList", fileList);
		mv.setViewName("fileDownloadListView");
		
		return mv;
	}
	
	// 파일 다운로드 
	@RequestMapping("/fileDownload/{file}")
	public void fileDownload(@PathVariable String file, 
								HttpServletResponse response) throws IOException {
		
		//File f = new File("/Library/springWorkspace/upload/", file);
		// 파일명 인코딩
		//String encodedFileName = new String (file.getBytes("UTF-8"), "ISO-8859-1");
		
		//DB에서 해당 ID의 파일 메타데이터를 조회
		FileMetadataDTO fileMetadata = fileService.getFileById(id);
		
		// 파일 경로 가져오기
		File file = new File(fileMetadata.getFilePath());
		
		if (file.exists()) {
	        // 파일명 인코딩 처리 (한글 지원)
	        String encodedFileName = new String(fileMetadata.getOriginalFileName().getBytes("UTF-8"), "ISO-8859-1");
		
		// file 다운로드 설정
		
		response.setContentType("application/download");
		response.setContentLength((int)file.length());
		response.setHeader("Content-Disposition", "attatchment; filename=\"" + encodedFileName + "\"");
		
		// 파일 읽기 및 출력
        try (FileInputStream fis = new FileInputStream(file);
	             OutputStream os = response.getOutputStream()) {
	
	            FileCopyUtils.copy(fis, os);
	        }
	    } else {
	        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	    }
		
		// 다운로드 시 저장되는 이름은 Response Header의 "Content-Disposition"에 명시
		
		//OutputStream os = response.getOutputStream();
		
		//FileInputStream fis = new FileInputStream(f);
		//FileCopyUtils.copy(fis, os);
		
		// fis.close();
		// os.close();
		
	}

}
