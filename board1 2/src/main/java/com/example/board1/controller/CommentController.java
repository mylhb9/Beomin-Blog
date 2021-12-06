package com.example.board1.controller;


import com.example.board1.config.UserDetailsImpl;
import com.example.board1.domain.Comment;
import com.example.board1.dto.CommentDto;
import com.example.board1.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping("/post/{id}/comment")
    public String commentwrite(@PathVariable("id") Long boardId ,CommentDto commentDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(commentDto.getCocontent()=="") {
            throw new IllegalArgumentException("댓글 내용을 입력해주세요");
        }
        Long userId = userDetails.getUser().getId();
        Comment comment= commentService.saveComment(commentDto, userId, boardId);
        System.out.println(comment);
        return "redirect:/post/{id}";
    }
//ㄴㄴㄴㅇㄹㅁㄴㅇㄹ
    //댓글 수정 내가 쓴 댓글만 수정가능
    @PutMapping("/post/{id}/comment/{coid}")
    public String update(@PathVariable("coid") Long commentId, CommentDto commentDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(commentDto.getCocontent()=="") {
            throw new IllegalArgumentException("댓글 내용을 입력해주세요");
        }
        Long userId = userDetails.getUser().getId();
        Long commentUserId = commentDto.getUserId();
        if( commentUserId.equals(userId)) {
            commentService.update(commentId, commentDto);
        }
        return "redirect:/post/{id}";
    }


//    댓글 삭제 내가 쓴 댓글만 삭제가능
    @DeleteMapping("/post/{id}/comment/{coid}")
    public String delete(@PathVariable("coid") Long commentId, CommentDto commentDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        Long commentUserId = commentDto.getUserId();
        if( commentUserId.equals(userId)) {
            commentService.deleteComment(commentId);
        }
        return "redirect:/post/{id}";
    }
}
