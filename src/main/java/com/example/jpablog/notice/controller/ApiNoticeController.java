package com.example.jpablog.notice.controller;

import com.example.jpablog.notice.model.NoticeModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ApiNoticeController {

//    @GetMapping("/api/notice")
//    public String noticeString() {
//
//        return "공지사항입니다.";
//    }

//    @GetMapping("/api/notice")
//    public NoticeModel notice() {
//
//        LocalDateTime regDate = LocalDateTime.of(2021, 2, 8, 0, 0);
//
//        NoticeModel notice = new NoticeModel();
//        notice.setId(1);
//        notice.setTitle("공지사항입니다.");
//        notice.setContents("공지사항 내용입니다.");
//        notice.setRegDate(regDate);
//
//        return notice;
//    }

//    @GetMapping("/api/notice")
//    public List<NoticeModel> notice() {
//
//        List<NoticeModel> noticeList = new ArrayList<>();
//
//        noticeList.add(NoticeModel.builder()
//                .id(1)
//                .title("공지사항입니다.")
//                .contents("공지사항내용입니다.")
//                .regDate(LocalDateTime.now())
//                .build());
//
//        noticeList.add(NoticeModel.builder()
//                .id(2)
//                .title("두번째 공지사항입니다.")
//                .contents("두번째 공지사항내용입니다.")
//                .regDate(LocalDateTime.now())
//                .build());
//
//        return noticeList;
//    }

    @GetMapping("/api/notice")
    public List<NoticeModel> notice() {

        List<NoticeModel> noticeList = new ArrayList<>();

        return noticeList;
    }

    @GetMapping("api/notice/count")
    public int noticeCount() {

        return 10;

    }
}
