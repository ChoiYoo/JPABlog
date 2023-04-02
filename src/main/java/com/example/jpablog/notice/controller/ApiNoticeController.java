package com.example.jpablog.notice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiNoticeController {

    @GetMapping("/api/notice")
    public String noticeString() {

        return "공지사항입니다.";
    }
}
