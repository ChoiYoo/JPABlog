package com.example.jpablog.user.service;

import com.example.jpablog.board.model.ServiceResult;
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

    List<MemberLogCount> getMemberLikeBest();

    ServiceResult addInterestMember(String email, Long id);
}
