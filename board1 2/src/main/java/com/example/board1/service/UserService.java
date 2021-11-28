package com.example.board1.service;

import com.example.board1.domain.User;
import com.example.board1.dto.SignupRequestDto;
import com.example.board1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Service
public class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
//    private static final String ADMIN_TOKEN = "AAABnv/xRVklrnYxKZ0aHgTBcXukeZygoC";

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

//    public Map<String, String> validateHandling(Errors errors) {
//        Map<String, String> validatorResult = new HashMap<>();
//
//        for (FieldError error: errors.getFieldErrors()) {
//            String validKeyName = String.format("valid_%s", error.getField());
//            validatorResult.put(validKeyName, error.getDefaultMessage());
//        }
//        return validatorResult;
//
//    }
    //회원가입 메소드
    public void registerUser(SignupRequestDto requestDto) {
    // 회원 ID 중복 확인
        String username = requestDto.getUsername();
//        User find = userRepository.findByUsername(username).orElseThrow(()->new IllegalArgumentException("중복된 사용자 ID가 존재합니다."));
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }
        //닉네임에 같은 값이 포함되어있으면 에러내기, indexof가 -1 이면 안에 포함이 안돼있는것
        if(requestDto.getPassword().indexOf(requestDto.getUsername())!=-1) {
            throw new IllegalArgumentException("비밀번호에 닉네임과 같은 값을 포함할 수 없습니다.");
        }
        // 입력된 비밀 번호 값이 같지 않으면 회원가입 불가
        if(!(requestDto.getPassword().equals(requestDto.getPasswordcheck()))) {
            throw new IllegalArgumentException("비밀번호가 같지 않습니다.");
        }



    // 패스워드 암호화
        String password = passwordEncoder.encode(requestDto.getPassword());
        String email = requestDto.getEmail();

// 사용자 ROLE 확인
//        UserRoleEnum role = UserRoleEnum.USER;
//        if (requestDto.isAdmin()) {
//            if (!requestDto.getAdminToken().equals(ADMIN_TOKEN)) {
//                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
//            }
//            role = UserRoleEnum.ADMIN;
//        }

        User user = new User().builder()
                .username(requestDto.getUsername())
                .password(password)
                .build();
        userRepository.save(user);
    }


}