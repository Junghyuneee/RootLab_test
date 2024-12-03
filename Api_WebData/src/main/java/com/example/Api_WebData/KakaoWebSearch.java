package com.example.Api_WebData;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KakaoWebSearch {

    // 카카오 REST API 키
    private static final String API_KEY = "11d9b1fd43fafba8c681ac8cc025e38e";
    // 카카오 주소
    private static final String API_URL = "https://dapi.kakao.com/v2/search/web";

    // jackson objectMapper 객체 생성
    private ObjectMapper objectMapper = new ObjectMapper();
    
    public String searchResult(String keyword, Integer page) {

    	try {
	    	// URL 설정
			String url = API_URL + "?query=" + keyword + "&page=" + page;
			
			// HTTP 헤더 설정
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "KakaoAK " + API_KEY);
				
			HttpEntity<String> entity = new HttpEntity<>(headers);
	
			// REST 요청
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
			
			System.out.println(response.getBody());
			System.out.println("완료");
			
			// 응답 본문 반환
			return response.getBody();
    	} catch (Exception e) {
    		e.printStackTrace();
    		return "{\"error\":\"API 호출 중 오류 발생\"}";
    	}
	}
    
    // JSON 문자열을 Map으로 변환하는 메서드
    public Map<String, Object> parseJson(String json){
    	try {
    		// Json을 Map<String, Object>으로 변환
    		return objectMapper.readValue(json, Map.class);
    	} catch(Exception e) {
    		e.printStackTrace();
    		return new HashMap<>();
    	}
    }
}
