package com.example.Calendar;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    // "/" 요청을 처리하여 index.html 페이지를 반환
    @GetMapping("/")
    public String index() {
        return "index"; // "index"는 templates 폴더에 있는 index.html을 의미
    }
}