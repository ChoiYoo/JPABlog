package com.example.jpablog.user.service;

import com.example.jpablog.user.model.MemberReponse;
import com.example.jpablog.user.model.MemberStatus;
import com.example.jpablog.user.model.MemberSumary;
import com.example.jpablog.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
