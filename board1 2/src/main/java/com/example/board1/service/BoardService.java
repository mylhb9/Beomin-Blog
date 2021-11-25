package com.example.board1.service;

import com.example.board1.domain.Board;
import com.example.board1.dto.BoardDto;
import com.example.board1.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service

public class BoardService {

    private final BoardRepository boardRepository;

    @Autowired
    public BoardService(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Transactional
    public Board update(Long id, BoardDto boardDto) {
        Board board = boardRepository.findById(id).get();
        board.update(boardDto);
        return board;
    }

    @Transactional
    public Board savePost(BoardDto boardDto, Long userId ) {
        Board board = new Board(boardDto, userId);
        boardRepository.save(board);
        return board;
    }



}
