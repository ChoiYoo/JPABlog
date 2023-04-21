package com.example.jpablog.logs.service;

import com.example.jpablog.board.model.ServiceResult;
import com.example.jpablog.common.exception.BizException;
import com.example.jpablog.logs.entity.Logs;
import com.example.jpablog.logs.repository.LogsRepository;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.entity.MemberInterest;
import com.example.jpablog.user.model.*;
import com.example.jpablog.user.repository.MemberCustomRepository;
import com.example.jpablog.user.repository.MemberInterestRepository;
import com.example.jpablog.user.repository.MemberRepository;
import com.example.jpablog.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogsServiceImpl implements LogsService{

    private final LogsRepository logsRepository;

    @Override
    public void add(String text) {

        logsRepository.save(Logs.builder()
                .text(text)
                .regDate(LocalDateTime.now())
                .build());

    }
}
