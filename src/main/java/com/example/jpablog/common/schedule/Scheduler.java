package com.example.jpablog.common.schedule;

import com.example.jpablog.logs.service.LogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private final LogsService logsService;

    /**
     * 99. 스프링 스케쥴러를 이용하여 매일 새벽4시에 로그정보를 삭제하는 기능을 작성해 보세요.
     */
    // 1분에 한번씩 실행
//    @Scheduled(fixedDelay = 1000 * 60)
    @Scheduled(cron = "0 0 4 * * *")
    public void deleteLog(){
        logsService.deleteLog();
    }

}
