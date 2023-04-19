package com.example.jpablog.user.service;

import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.MemberLogCount;
import com.example.jpablog.user.model.MemberNoticeCount;
import com.example.jpablog.user.model.MemberSumary;

import java.util.List;

public interface MemberService {

    MemberSumary getMemberStatusCount();

    List<Member> getTodayMembers();

    List<MemberNoticeCount> getMemberNoticeCount();

    List<MemberLogCount> getMemberLogCount();
}
