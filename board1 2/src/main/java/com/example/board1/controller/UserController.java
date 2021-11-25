package com.example.board1.controller;

import com.example.board1.config.UserDetailsImpl;
import com.example.board1.dto.SignupRequestDto;
import com.example.board1.service.KakaoUserService;
import com.example.board1.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Map;


@Controller
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    @Autowired
    public UserController(UserService userService, KakaoUserService kakaoUserService) {
        this.userService = userService;
        this.kakaoUserService = kakaoUserService;
    }

    // 회원 로그인 페이지
    @GetMapping("/user/login")
    public String login(@AuthenticationPrincipal UserDetailsImpl userDetails ) {
        if(!(userDetails==null)) {
            throw new IllegalArgumentException("이미 로그인이 되어있습니다");
        }
        return "sign/login";
    }

    // 회원 가입 페이지
    @GetMapping("/user/signup")
    public String signup(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if(!(userDetails==null)) {
            throw new IllegalArgumentException("이미 로그인이 되어있습니다");
        }
        return "sign/signup";
    }

    // 회원 가입 요청 처리
    @PostMapping("/user/signup")
    public String registerUser(@Valid SignupRequestDto requestDto, Errors errors, Model model) {
        if (errors.hasErrors()) {
            Map<String, String> validatorResult = userService.validateHandling(errors);
            for (String key : validatorResult.keySet()) {
                model.addAttribute(key,validatorResult.get(key));
            }
            return "sign/signup";
        }


        userService.registerUser(requestDto);

        return "redirect:/user/login";
    }

    //카카오 회원가입 및 로그인 요청 처리
    @GetMapping("/user/kakao/callback")
    public String kakaoLogin(@RequestParam String code) throws JsonProcessingException {
        kakaoUserService.kakaoLogin(code);
        return "redirect:/list";
    }

}