package com.example.jpablog.user.service;

import com.example.jpablog.board.model.ServiceResult;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.entity.MemberInterest;
import com.example.jpablog.user.model.*;
import com.example.jpablog.user.repository.MemberCustomRepository;
import com.example.jpablog.user.repository.MemberInterestRepository;
import com.example.jpablog.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MemberCustomRepository memberCustomRepository;
    private final MemberInterestRepository memberInterestRepository;

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

    @Override
    public List<MemberNoticeCount> getMemberNoticeCount() {

        return memberCustomRepository.findMemberNoticeCount();
    }

    @Override
    public List<MemberLogCount> getMemberLogCount() {

        return memberCustomRepository.findMemberLogCount();
    }

    @Override
    public List<MemberLogCount> getMemberLikeBest() {

        return memberCustomRepository.findMemberLikeBest();
    }

    @Override
    public ServiceResult addInterestMember(String email, Long id) {

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        Optional<Member> optionalInterestMember = memberRepository.findById(id);
        if(!optionalInterestMember.isPresent()){
            return ServiceResult.fail("관심사용자의 추가할 회원 정보가 존재하지 않습니다.");
        }
        Member interestMember = optionalInterestMember.get();

        // 내가 나를 추가하면 안됨!
        if(member.getId() == interestMember.getId()){
            return ServiceResult.fail("자기 자신을 추가할 수 없습니다.");
        }

        if(memberInterestRepository.countByMemberAndInterestMember(member, interestMember) > 0){
            return ServiceResult.fail("이미 관심사용자 목록에 추가하였습니다.");
        }

        MemberInterest memberInterest = MemberInterest.builder()
                .member(member)
                .interestMember(interestMember)
                .regDate(LocalDateTime.now())
                .build();

        memberInterestRepository.save(memberInterest);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult removeInterestMember(String email, Long id) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        Optional<MemberInterest> optionalMemberInterest = memberInterestRepository.findById(id);
        if(!optionalMemberInterest.isPresent()){
            return ServiceResult.fail("삭제할 정보가 없습니다.");
        }

        MemberInterest memberInterest = optionalMemberInterest.get();
        if(memberInterest.getMember().getId() != member.getId()){
            return ServiceResult.fail("본인의 관심자 정보만 삭제할 수 있습니다.");
        }

        memberInterestRepository.delete(memberInterest);
        return ServiceResult.success();
    }
}
