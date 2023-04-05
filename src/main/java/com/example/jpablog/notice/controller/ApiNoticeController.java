package com.example.jpablog.notice.controller;

import com.example.jpablog.notice.entity.Notice;
import com.example.jpablog.notice.model.NoticeInput;
import com.example.jpablog.notice.model.NoticeModel;
import com.example.jpablog.notice.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class ApiNoticeController {


    private final NoticeRepository noticeRepository;

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

//    @PostMapping("/api/notice")
//    public NoticeModel addNotice(@RequestParam String title, @RequestParam String contents) {
//
//        NoticeModel notice = NoticeModel.builder()
//                .id(1)
//                .title(title)
//                .contents(contents)
//                .regDate(LocalDateTime.now())
//                .build();
//
//        return notice;
//    }

//    @PostMapping("/api/notice")
//    public NoticeModel addNotice(NoticeModel noticeModel) {
//
//        noticeModel.setId(2);
//        noticeModel.setRegDate(LocalDateTime.now());
//
//        return noticeModel;
//
//    }

//    @PostMapping("/api/notice")
//    public NoticeModel addNotice(@RequestBody NoticeModel noticeModel) {
//
//        noticeModel.setId(3);
//        noticeModel.setRegDate(LocalDateTime.now());
//
//        return noticeModel;음
//    }

//    @PostMapping("/api/notice")
//    public Notice addNotice(@RequestBody NoticeInput noticeInput) {
//        Notice notice = Notice.builder()
//                .title(noticeInput.getTitle())
//                .contents(noticeInput.getContents())
//                .regDate(LocalDateTime.now())
//                .build();
//
//        noticeRepository.save(notice);
//
//        return notice;
//    }
    //@RequestBody 필수!!!!!!!!
    @PostMapping("/api/notice")
    public Notice addNotice(@RequestBody NoticeInput noticeInput) {

        Notice notice = Notice.builder()
                .title(noticeInput.getTitle())
                .contents(noticeInput.getContents())
                .regDate(LocalDateTime.now())
                .hits(0)
                .likes(0)
                .build();

        Notice resultNotice = noticeRepository.save(notice);

        return resultNotice;
    }

    @GetMapping("api/notice/count")
    public int noticeCount() {

        return 10;

    }

    // url에 있는 id를 가져오기 위해서는 @PathVariable 필수!!!!!
    @GetMapping("/api/notice/{id}")
    public Notice notice(@PathVariable Long id) {

        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            return notice.get();
        }

        return null;

    }

    @PutMapping("/api/notice/{id}")
    public void updatNotice(@PathVariable Long id, @RequestBody NoticeInput noticeInput) {

        Optional<Notice> notice = noticeRepository.findById(id);
        if (notice.isPresent()) {
            notice.get().setTitle(noticeInput.getTitle());
            notice.get().setContents(noticeInput.getContents());
            notice.get().setUpdateDate(LocalDateTime.now());
            noticeRepository.save(notice.get());
        }

    }
}
