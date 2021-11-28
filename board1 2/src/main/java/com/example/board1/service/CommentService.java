package com.example.board1.service;

import com.example.board1.domain.Comment;
import com.example.board1.dto.CommentDto;
import com.example.board1.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service

public class CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Transactional
    public Comment update(Long id, CommentDto commentDto) {
        Comment comment = commentRepository.findById(id).orElse(null);
        System.out.println(comment);
        comment.update(commentDto);
        return comment;
    }

    @Transactional
    public Comment saveComment(CommentDto commentDto, Long userId, Long boardId) {
        //빌더도 가능
        Comment comment = new Comment(commentDto, userId, boardId);
        commentRepository.save(comment);
        return comment;
    }

    public List<Comment> getAllComment() {
        return commentRepository.findAllByOrderByModifiedAtDesc();
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }



}
