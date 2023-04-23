package com.example.jpablog.logs.service;

import com.example.jpablog.board.model.ServiceResult;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.MemberLogCount;
import com.example.jpablog.user.model.MemberLogin;
import com.example.jpablog.user.model.MemberNoticeCount;
import com.example.jpablog.user.model.MemberSumary;

import java.util.List;

public interface LogsService {

    void add(String text);
}
