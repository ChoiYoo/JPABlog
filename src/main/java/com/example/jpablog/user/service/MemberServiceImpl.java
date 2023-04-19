package com.example.jpablog.user.service;

import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.model.MemberReponse;
import com.example.jpablog.user.model.MemberStatus;
import com.example.jpablog.user.model.MemberSumary;
import com.example.jpablog.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;

    @Override
    public MemberSumary getMemberStatusCount() {
        long usingMemberCount =memberRepository.countByStatus(MemberStatus.Using);
        long stopMemberCount = memberRepository.countByStatus(MemberStatus.Stop);
        long totalMemberCount = memberRepository.count();

        return MemberSumary.builder()
                .usingMemberCount(usingMemberCount)
                .stopMemberCount(stopMemberCount)
                .totalMemberCount(totalMemberCount)
                .build();
    }

    @Override
    public List<Member> getTodayMembers() {

        LocalDateTime t = LocalDateTime.now();
        LocalDateTime startDate = LocalDateTime.of(t.getYear(), t.getMonth(), t.getDayOfMonth(), 0, 0);
        LocalDateTime endDate = startDate.plusDays(1);

        return memberRepository.findToday(startDate, endDate);

    }
}