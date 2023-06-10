package com.example.jpablog.user.service;

import com.example.jpablog.board.model.ServiceResult;
import com.example.jpablog.common.MailComponent;
import com.example.jpablog.common.exception.BizException;
import com.example.jpablog.logs.service.LogsService;
import com.example.jpablog.mail.entity.MailTemplate;
import com.example.jpablog.mail.repository.MailTemplateRepository;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.entity.MemberInterest;
import com.example.jpablog.user.model.*;
import com.example.jpablog.user.repository.MemberCustomRepository;
import com.example.jpablog.user.repository.MemberInterestRepository;
import com.example.jpablog.user.repository.MemberRepository;
import com.example.jpablog.util.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final MemberCustomRepository memberCustomRepository;
    private final MemberInterestRepository memberInterestRepository;

    private final LogsService logsService;
    private final MailComponent mailComponent;
    private final MailTemplateRepository mailTemplateRepository;

    private final String fromEmailAddress = "";

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

    @Override
    public Member login(MemberLogin memberLogin) {
        Optional<Member> optionalMember = memberRepository.findByEmail(memberLogin.getEmail());
        if(!optionalMember.isPresent()){
            throw new BizException("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        if(!PasswordUtils.equalPassword(memberLogin.getPassword(), member.getPassword())){
            throw new BizException("일치하는 정보가 없습니다.");
        }

        return member;

    }

    @Override
    public ServiceResult add(MemberInput memberInput) {
        Optional<Member> optionalMember = memberRepository.findByEmail(memberInput.getEmail());
        if(optionalMember.isPresent()){
            throw new BizException("이미 가입된 이메일입니다.");
        }

        String encryptPassword = PasswordUtils.encryptedPassword(memberInput.getPassword());

        Member member = Member.builder()
                .userName(memberInput.getUserName())
                .email(memberInput.getEmail())
                .phone(memberInput.getPhone())
                .password(encryptPassword)
                .regDate(LocalDateTime.now())
                .status(MemberStatus.Using)
                .build();

        memberRepository.save(member);

        //메일전송
        String fromEmail = fromEmailAddress;
        String fromName = "관리자";
        String toEmail = member.getEmail();
        String toName = member.getUserName();

        String title = "회원가입을 축하드립니다!";
        String contents = "환영합니다.";

        mailComponent.send(fromEmail, fromName, toEmail, toName, title, contents);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult resetPassword(MemberPasswordResetInput memberInput) {
        Optional<Member> optionalMember = memberRepository.findByEmailAndUserName(memberInput.getEmail()
                , memberInput.getUserName());
        if(!optionalMember.isPresent()){
            throw new BizException("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        String passwordResetKey = UUID.randomUUID().toString();

        member.setPasswordResetYn(true);
        member.setPasswordResetKey(passwordResetKey);
        memberRepository.save(member);

        String serverUrl = "http://localhost:8080";


        Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findByTemplateId("USER_RESET_PASSWORD");
        optionalMailTemplate.ifPresent(e -> {

            String fromEmail = e.getSendEmail();
            String fromUserName = e.getSendUserName();
            String title = e.getTitle().replaceAll("\\{USER_NAME\\}", member.getUserName());
            String contents = e.getContents().replaceAll("\\{USER_NAME\\}", member.getUserName())
                    .replaceAll("\\{SERVER_URL\\}", serverUrl)
                    .replaceAll("\\{RESET_PASSWORD_KEY\\}", passwordResetKey);

            mailComponent.send(fromEmail, fromUserName
                    , member.getEmail(), member.getUserName(), title, contents);

        });

        return ServiceResult.success();
    }

    @Override
    public void sendServiceNotice() {

        Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findByTemplateId("USER_SERVICE_NOTICE");
        optionalMailTemplate.ifPresent(e -> {

            String fromEmail = e.getSendEmail();
            String fromUserName = e.getSendUserName();
            String contents = e.getContents();

            memberRepository.findAll().stream().forEach(m -> {
                String title = e.getTitle().replaceAll("\\{MEMBER_NAME\\}", m.getUserName());
                mailComponent.send(fromEmail, fromUserName
                        , m.getEmail(), m.getUserName(), title, contents);
            });
        });
    }
}
