package com.example.jpablog.user.service;

import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.MemberSumary;

import java.util.List;

public interface MemberService {

    MemberSumary getMemberStatusCount();

    List<Member> getTodayMembers();

}
