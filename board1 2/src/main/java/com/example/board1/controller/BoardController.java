package com.example.board1.controller;


import com.example.board1.config.UserDetailsImpl;
import com.example.board1.domain.Board;
import com.example.board1.domain.Comment;
import com.example.board1.dto.BoardDto;
import com.example.board1.repository.BoardRepository;
import com.example.board1.repository.CommentRepository;
import com.example.board1.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;
    private final BoardService boardService;
    private final CommentRepository commentRepository;

    // 전체 게시글 조회
    @GetMapping("/list")
    public String list(Model model) {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        model.addAttribute("postList", boardList);
        return "board/post-list";
    }

    //게시글 작성란 이동
    @GetMapping("/post")
    public String post(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
        String username = userDetails.getUser().getUsername();
        model.addAttribute("username",username);
        return "board/post-write";
    }

    //상세 페이지 조회
    @GetMapping("/post/{id}")
    public String detail(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable("id") Long id, Model model) {
        if (!(userDetails==null)) {
            String username = userDetails.getUser().getUsername();
            model.addAttribute("username",username);
        }
        Board board = boardRepository.findById(id).get();
        List<Comment> commentList = commentRepository.findAllByOrderByModifiedAtDesc();
        List<Comment> commentList1 = new ArrayList<>();
        for (int i=0; i<commentList.size(); i++) {
            if (commentList.get(i).getBoardId().equals(id)) {
                commentList1.add(commentList.get(i));
            }
        }
        model.addAttribute("commentList",commentList1);
        model.addAttribute("post", board);
        //        Optional<Board> board = boardRepository.findById(id);
//        if(!board.isPresent()) {
//            throw new IllegalStateException();
//        }
//
//        model.addAttribute("post", board.get());
        return "board/detail";
    }

    //게시글 작성
    @PostMapping("/post")
    public String write(BoardDto boardDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        boardService.savePost(boardDto, userId);
        return "redirect:/list";
    }

    //게시글 수정페이지 이동
    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        Board board = boardRepository.findById(id).get();
        model.addAttribute("post",board);
        return "board/edit";
    }

    //게시글 수정 내가 쓴 글만 수정가능
    @PutMapping("/post/edit/{id}")
    public String update(@PathVariable("id") Long id, BoardDto boardDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        Long boarduserId = boardDto.getUserId();
        if( boarduserId.equals(userId)) {
            boardService.update(id, boardDto);
        }
        return "redirect:/post/{id}";
    }


    //게시물 삭제 내가 쓴 글만 삭제가능
    @DeleteMapping("/post/{id}")
    public String delete(@PathVariable("id") Long id, BoardDto boardDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Long userId = userDetails.getUser().getId();
        Long boarduserId = boardDto.getUserId();
        if( boarduserId.equals(userId)) {
            boardRepository.deleteById(id);
        }

        return "redirect:/list";
    }

}
