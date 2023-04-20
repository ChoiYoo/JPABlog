package com.example.jpablog.board.service;

import ch.qos.logback.core.util.OptionHelper;
import com.example.jpablog.board.entity.Board;
import com.example.jpablog.board.entity.BoardHits;
import com.example.jpablog.board.entity.BoardLikes;
import com.example.jpablog.board.entity.BoardType;
import com.example.jpablog.board.model.*;
import com.example.jpablog.board.repository.*;
import com.example.jpablog.user.entity.Member;
import com.example.jpablog.user.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

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
}
