package com.example.Api_WebData.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.Api_WebData.KakaoWebSearch;

import lombok.RequiredArgsConstructor;


@Controller
@RequestMapping("/search")
@RequiredArgsConstructor
public class KakaoController {

	private final KakaoWebSearch kakaoSearch; 
	
	@GetMapping("")
	public String search() {
		return "/search/search";
	}
	
	@GetMapping("/get")
	@ResponseBody
	public Map<String, Object> getSearchResult(@RequestParam("keyword") String keyword,
										   @RequestParam("page") Integer page)  {
		
		// kakaosearch에서 검색 결과를 받아옴
		System.out.println(keyword + " " + page);

		String jsonResponse = kakaoSearch.searchResult(keyword, page);
		System.out.println(jsonResponse);
		
		// jacksoon을 사용하여 json 파싱
		Map<String, Object> mapData = kakaoSearch.parseJson(jsonResponse);

		System.out.println(mapData);
		
		// 파싱한 데이터를 반환
		return mapData;
	}
}
