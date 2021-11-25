package com.example.board1.service;

import com.example.board1.domain.Comment;
import com.example.board1.dto.CommentDto;
import com.example.board1.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service

public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment update(Long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id).get();
        System.out.println(comment);
        comment.update(commentDto);
        return comment;
    }

    @Transactional
    public Comment saveComment(CommentDto commentDto, Long userId, Long boardId) {
        Comment comment = new Comment(commentDto, userId, boardId);
        commentRepository.save(comment);
        return comment;
    }



}
