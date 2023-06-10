package com.example.jpablog.common.schedule;

import com.example.jpablog.logs.service.LogsService;
import com.example.jpablog.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final LogsService logsService;
    private final MemberService memberService;

    /**
     * 99. 스프링 스케쥴러를 이용하여 매일 새벽4시에 로그정보를 삭제하는 기능을 작성해 보세요.
     */
    // 1분에 한번씩 실행
//    @Scheduled(fixedDelay = 1000 * 60)
    @Scheduled(cron = "0 0 4 * * *")
    public void deleteLog(){
        logsService.deleteLog();
    }

    /**
     * 100. 스프링 스케쥴러를 이용하여 회원중 가입일이 1년이 도래한 회원들에 대해서 서비스 이용내역 통지 메일을 보내는 기능을 작성해 보세요.
     */
    @Scheduled(fixedDelay = 1000 * 60)
    public void sendServiceNotice(){
        memberService.sendServiceNotice();
    }

}
