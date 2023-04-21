package com.example.jpablog.user.service;

import com.example.jpablog.board.model.ServiceResult;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.entity.MemberPoint;
import com.example.jpablog.user.model.MemberPointInput;
import com.example.jpablog.user.model.MemberPointType;
import com.example.jpablog.user.repository.MemberPointRepository;
import com.example.jpablog.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PointServiceImpl implements PointService{

    private final MemberPointRepository memberPointRepository;
    private final MemberRepository memberRepository;
    @Override
    public ServiceResult addPoint(String email, MemberPointInput memberPointInput) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        MemberPoint memberPoint = MemberPoint.builder()
                .member(member)
                .memberPointType(memberPointInput.getMemberPointType())
                .point(memberPointInput.getMemberPointType().getValue())
                .build();
        memberPointRepository.save(memberPoint);
        return ServiceResult.success();
    }
}
