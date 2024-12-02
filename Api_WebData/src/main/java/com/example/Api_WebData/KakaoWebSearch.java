package com.example.Api_WebData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class KakaoWebSearch {

    // 카카오 REST API 키 (본인의 키로 대체)
    private static final String API_KEY = "11d9b1fd43fafba8c681ac8cc025e38e"; // 발급받은 키로 변경
    private static final String API_URL = "https://dapi.kakao.com/v2/search/web";

    public static void main(String[] args) {
        String query = "흑백요리사"; // 검색어
        try {
            // API 호출
            String jsonResponse = searchWeb(query);

            // JSON 데이터 파싱 및 저장
            ArrayList<Map<String, String>> results = parseJson(jsonResponse);

            // 결과를 화면에 출력
            displayResults(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 카카오 웹 검색 API 요청
    private static String searchWeb(String query) throws Exception {
        String encodedQuery = java.net.URLEncoder.encode(query, "UTF-8");
        String requestUrl = API_URL + "?query=" + encodedQuery;

        URL url = new URL(requestUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "KakaoAK " + API_KEY);

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("HTTP 요청 실패: 응답 코드 " + responseCode);
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();
        return response.toString();
    }

    // JSON 데이터 파싱(JSON문자열을 객체로 생성)
    private static ArrayList<Map<String, String>> parseJson(String json) {
        ArrayList<Map<String, String>> list = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(json);
        JSONArray documents = jsonObject.getJSONArray("documents");

        for (int i = 0; i < documents.length(); i++) {
            JSONObject doc = documents.getJSONObject(i);
            Map<String, String> map = new HashMap<>();
            map.put("title", doc.getString("title"));
            map.put("url", doc.getString("url"));
            map.put("contents", doc.getString("contents"));
            list.add(map);
        }

        return list;
    }

    // 결과를 화면에 출력
    private static void displayResults(ArrayList<Map<String, String>> results) {
        System.out.println("=== 검색 결과 ===");
        for (Map<String, String> result : results) {
            System.out.println("제목: " + result.get("title"));
            System.out.println("URL: " + result.get("url"));
            System.out.println("요약: " + result.get("contents"));
            System.out.println("-----------------------------");
        }
    }
}
