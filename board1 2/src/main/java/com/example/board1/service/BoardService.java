package com.example.board1.service;

import com.example.board1.domain.Board;
import com.example.board1.dto.BoardDto;
import com.example.board1.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service

public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Board update(Long id, BoardDto boardDto) {
        Board board = boardRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("저장된 게시물이 아닙니다."));
        board.update(boardDto);
        return board;
    }

    @Transactional
    public Board savePost(BoardDto boardDto, Long userId ) {
        Board board = new Board(boardDto, userId);
        boardRepository.save(board);
        return board;
    }

    // 전체게시물 조회
    public List<Board> getAllBoard() {
        return boardRepository.findAllByOrderByModifiedAtDesc();
    }

    //id와 같은 게시물 조회 (상세페이지 조회에서 활용)
    public Board getBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("게시글이 존재하지 않습니다"));
    }

    //id에 해당하는 게시물 삭제

    public void deleteBoard(Long id) {
        boardRepository.deleteById(id);
    }



}
