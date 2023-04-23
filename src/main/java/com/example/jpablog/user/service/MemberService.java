package com.example.jpablog.user.service;

import com.example.jpablog.board.model.ServiceResult;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.*;

import java.util.List;

public interface MemberService {

    MemberSumary getMemberStatusCount();

    List<Member> getTodayMembers();

    List<MemberNoticeCount> getMemberNoticeCount();

    List<MemberLogCount> getMemberLogCount();

    List<MemberLogCount> getMemberLikeBest();

    ServiceResult addInterestMember(String email, Long id);

    ServiceResult removeInterestMember(String email, Long id);

    Member login(MemberLogin memberLogin);

    ServiceResult add(MemberInput memberInput);

    ServiceResult resetPassword(MemberPasswordResetInput memberPasswordResetInput);
}
