package com.example.jpablog.board.service;

import com.example.jpablog.board.entity.*;
import com.example.jpablog.board.model.*;
import com.example.jpablog.board.repository.*;
import com.example.jpablog.common.MailComponent;
import com.example.jpablog.common.exception.BizException;
import com.example.jpablog.mail.entity.MailTemplate;
import com.example.jpablog.mail.repository.MailTemplateRepository;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardTypeRepository boardTypeRepository;
    private final BoardRepository boardRepository;
    private final BoardTypeCustomRepository  boardTypeCustomRepository;
    private final BoardHitsRepository boardHitsRepository;
    private final MemberRepository memberRepository;
    private final BoardLikesRepository boardLikesRepository;
    private final BoardBadReportRepository boardBadReportRepository;
    private final BoardScrapRepository boardScrapRepository;
    private final BoardBookmarkRepository boardBookmarkRepository;
    private final BoardCommentRepository boardCommentRepository;
    private final MailTemplateRepository mailTemplateRepository;
    private final MailComponent mailComponent;
    @Override
    public ServiceResult addBoard(BoardTypeInput boardTypeInput) {

        BoardType boardType = boardTypeRepository.findByBoardName(boardTypeInput.getName());
        if(boardType != null && boardTypeInput.getName().equals(boardType.getBoardName())) {
            return ServiceResult.fail("이미 동일한 게시판이 존재합니다.");
        }

        BoardType addBoardType = BoardType.builder()
                .boardName(boardTypeInput.getName())
                .regDate(LocalDateTime.now())
                .build();

        boardTypeRepository.save(addBoardType);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult updateBoard(long id, BoardTypeInput boardTypeInput) {
        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);
        BoardType boardTypName = boardTypeRepository.findByBoardName(boardTypeInput.getName());


        if(!optionalBoardType.isPresent()) {
            return ServiceResult.fail("수정할 게시판 타입이 없습니다.");
        }

        BoardType boardType = optionalBoardType.get();

        if(boardType.getBoardName().equals(boardTypeInput.getName())) {
            return ServiceResult.fail("수정할 이름이 동일한 게시판명입니다.");
        }
        if(boardTypName != null && boardTypeInput.getName().equals(boardTypName.getBoardName())) {
            return ServiceResult.fail("이미 동일한 게시판이 존재합니다.");
        }


        boardType.setBoardName(boardTypeInput.getName());
        boardType.setUpdateDate(LocalDateTime.now());
        boardTypeRepository.save(boardType);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult deleteBoard(Long id) {

        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);
        if(!optionalBoardType.isPresent()){
            return ServiceResult.fail("삭제할 게시판 타입이 없습니다.");
        }

        BoardType boardType = optionalBoardType.get();

        if (boardRepository.countByBoardType(boardType) > 0) {
            return ServiceResult.fail("삭제할 게시판 타입의 게시글이 존재합니다.");
        }

        boardTypeRepository.delete(boardType);
        return ServiceResult.success();
    }

    @Override
    public List<BoardType> getAllBoardType() {

        return boardTypeRepository.findAll();
    }

    @Override
    public ServiceResult setBoardTypeUsing(Long id, BoardTypeUsing boardTypeUsing) {

        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(id);
        if(!optionalBoardType.isPresent()){
            return ServiceResult.fail("삭제할 게시판 타입이 없습니다.");
        }

        BoardType boardType = optionalBoardType.get();

        boardType.setUsingYn(boardTypeUsing.isUsingYn());
        boardTypeRepository.save(boardType);

        return ServiceResult.success();
    }

    @Override
    public List<BoardTypeCount> getBoardTypeCount() {

        return boardTypeCustomRepository.getBoardTypeCount();
    }

    @Override
    public ServiceResult setBoardTop(Long id, boolean topYn) {

        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();
        if(board.isTopYn() == topYn){
            if (topYn){
                return ServiceResult.fail("이미 게시글이 최상단에 배치되어 있습니다.");
            }else {
                return ServiceResult.fail("이미 게시글이 최상단에 배치가 해제되어 있습니다.");
            }
        }
        board.setTopYn(topYn);
        boardRepository.save(board);

        return ServiceResult.success();
    }

    @Override
    public ServiceResult setBoardPeriod(Long id, BoardPeriod boardPeriod) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }

        Board board = optionalBoard.get();

        board.setStartDate(boardPeriod.getStartDate());
        board.setEndDate(boardPeriod.getEndDate());

        boardRepository.save(board);

        return ServiceResult.success();

    }

    @Override
    public ServiceResult setBoardHits(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        if (boardHitsRepository.countByBoardAndMember(board, member) > 0){
            return ServiceResult.fail("이미 조회수가 있습니다.");
        }
        boardHitsRepository.save(BoardHits.builder()
                .board(board)
                .member(member)
                .regDate(LocalDateTime.now())
                .build());

        return ServiceResult.success();
    }

    @Override
    public ServiceResult setBoardLikes(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        if(boardLikesRepository.countByBoardAndMember(board, member) > 0){
            return ServiceResult.fail("이미 좋아요한 내용이 있습니다.");
        }
        boardLikesRepository.save(BoardLikes.builder()
                .board(board)
                .member(member)
                .regDate(LocalDateTime.now())
                .build());

        return ServiceResult.success();

    }

    @Override
    public ServiceResult setBoardUnLikes(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        Optional<BoardLikes> optionalBoardLikes = boardLikesRepository.findByBoardAndMember(board, member);

        if(!optionalBoardLikes.isPresent()){
            return ServiceResult.fail("좋아요한 내용이 없습니다.");
        }
        BoardLikes boardLikes = optionalBoardLikes.get();

        boardLikesRepository.delete(boardLikes);

        return ServiceResult.success();

    }

    @Override
    public ServiceResult addBadReport(Long id, String email, BoardBadReportInput boardBadReportInput) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        boardBadReportRepository.save(BoardBadReport.builder()
                .memberId(member.getId())
                .userName(member.getUserName())
                .userEmail(member.getEmail())
                .boardId(board.getId())
                .boardTitle(board.getTitle())
                .boardContents(board.getContents())
                .boardMemberId(board.getMember().getId())
                .boardRegDate(board.getRegDate())
                .comments(boardBadReportInput.getComments())
                .regDate(LocalDateTime.now())
                .build());

        return ServiceResult.success();


    }

    @Override
    public List<BoardBadReport> badReportList() {

        return boardBadReportRepository.findAll();
    }

    @Override
    public ServiceResult scrapBoard(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();


        BoardScrap boardScrap = BoardScrap.builder()
                .member(member)
                .boardId(board.getId())
                .boardTypeId(board.getBoardType().getId())
                .boardTitle(board.getTitle())
                .boardContents(board.getContents())
                .boardMemberId(board.getMember().getId())
                .boardRegDate(board.getRegDate())
                .regDate(LocalDateTime.now())
                .build();

        boardScrapRepository.save(boardScrap);

        return ServiceResult.success();

    }

    @Override
    public ServiceResult removeScrap(Long id, String email) {

        Optional<BoardScrap> optionalBoardScrap = boardScrapRepository.findById(id);
        if(!optionalBoardScrap.isPresent()){
            return ServiceResult.fail("삭제할 스크랩이 없습니다.");
        }
        BoardScrap boardScrap = optionalBoardScrap.get();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        if(member.getId() != boardScrap.getMember().getId()){
            return ServiceResult.fail("본인의 스크랩만 삭제할 수 있습니다.");
        }

        boardScrapRepository.delete(boardScrap);
        return ServiceResult.success();
    }

    private String getBoardUrl(long boardId){
        return String.format("/board/%d", boardId);
    }

    @Override
    public ServiceResult bookmarkBoard(Long id, String email) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        BoardBookmark boardBookmark = BoardBookmark.builder()
                .member(member)
                .boardId(board.getId())
                .boardTitle(board.getTitle())
                .boardTypeId(board.getBoardType().getId())
                .boardUrl(getBoardUrl(board.getId()))
                .regDate(LocalDateTime.now())
                .build();

        boardBookmarkRepository.save(boardBookmark);
        return ServiceResult.success();
    }

    @Override
    public ServiceResult removeBookmark(Long id, String email) {
        Optional<BoardBookmark> optionalBoardBookmark = boardBookmarkRepository.findById(id);
        if(!optionalBoardBookmark.isPresent()){
            return ServiceResult.fail("삭제할 북마크가 없습니다.");
        }
        BoardBookmark boardBookmark = optionalBoardBookmark.get();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            return ServiceResult.fail("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        if(member.getId() != boardBookmark.getMember().getId()){
            return ServiceResult.fail("본인의 북마크만 삭제할 수 있습니다.");
        }

        boardBookmarkRepository.delete(boardBookmark);
        return ServiceResult.success();
    }

    @Override
    public List<Board> postList(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            throw new BizException("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        List<Board> list = boardRepository.findByMember(member);
        return list;
    }

    @Override
    public List<BoardComment> commentList(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            throw new BizException("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        List<BoardComment> list = boardCommentRepository.findByMember(member);
        return list;

    }

    @Override
    public Board detail(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if(!optionalBoard.isPresent()){
            throw new BizException("게시글이 존재하지 않습니다.");
        }
        return optionalBoard.get();


    }

    @Override
    public List<Board> list() {
        List<Board> boardList = boardRepository.findAll();
        return boardList;
    }

    @Override
    public ServiceResult add(String email, BoardInput boardInput) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        if(!optionalMember.isPresent()){
            throw new BizException("회원 정보가 존재하지 않습니다.");
        }
        Member member = optionalMember.get();

        Optional<BoardType> optionalBoardType = boardTypeRepository.findById(boardInput.getBoardType());
        if(!optionalBoardType.isPresent()){
            return ServiceResult.fail("해당 타입이 없습니다.");
        }

        Board board = Board.builder()
                .member(member)
                .title(boardInput.getTitle())
                .contents(boardInput.getContents())
                .boardType(optionalBoardType.get())
                .regDate(LocalDateTime.now())
                .build();
        boardRepository.save(board);

        //메일전송로직

        Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findByTemplateId("BOARD_ADD");
        optionalMailTemplate.ifPresent((e) ->{

            String fromEmail = e.getSendEmail();
            String fromUserName = e.getSendUserName();
            String title = e.getTitle().replaceAll("\\{USER_NAME\\}", member.getUserName());
            String contents = e.getContents().replaceAll("\\{BOARD_TITLE\\}", board.getTitle())
                    .replaceAll("\\{BOARD_CONTENTS\\}", board.getContents());

            mailComponent.send(fromEmail, fromUserName, member.getEmail(), member.getUserName(), title, contents);

        });

        return ServiceResult.success();

    }

    @Override
    public ServiceResult replyBoard(Long id, BoardReplyInput boardReplyInput) {

        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (!optionalBoard.isPresent()){
            return ServiceResult.fail("게시글이 존재하지 않습니다.");
        }
        Board board = optionalBoard.get();

        board.setReplyContents(boardReplyInput.getReplyContents());
        boardRepository.save(board);

        //메일전송
        Optional<MailTemplate> optionalMailTemplate = mailTemplateRepository.findByTemplateId("BOARD_REPLY");
        optionalMailTemplate.ifPresent((e) -> {
            String fromEmail = e.getSendEmail();
            String fromUserName = e.getSendUserName();
            String title = e.getTitle().replaceAll("\\{USER_NAME\\}", board.getMember().getUserName());
            String contents = e.getContents().replaceAll("\\{BOARD_TITLE\\}", board.getTitle())
                    .replaceAll("\\{BOARD_CONTENTS\\}", board.getContents())
                    .replaceAll("\\{BOARD_REPLY_CONTENTS\\}", board.getReplyContents());

            mailComponent.send(fromEmail, fromUserName
                    , board.getMember().getEmail(), board.getMember().getUserName(), title, contents);

        });
        return ServiceResult.success();

    }
}
