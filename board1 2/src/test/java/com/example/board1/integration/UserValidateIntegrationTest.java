package com.example.board1.integration;

import com.example.board1.domain.User;
import com.example.board1.domain.UserRoleEnum;
import com.example.board1.dto.SignupRequestDto;

import com.example.board1.repository.UserRepository;
import com.example.board1.service.UserService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.Valid;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserValidateIntegrationTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    Long userId = null;


    @Test
    @Order(1)
    @DisplayName("회원 가입 비밀번호 일치1 성공케이스")
    void test1() {
        // given
        String username = "버민버민a3";
        String password = "beominonly";
        String passwordcheck = "beominonly";
        String email = "beomi23423n@naver.com";
        boolean admin = false;

        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setPassword(password);
        signupRequestDto.setPasswordcheck(passwordcheck);
        signupRequestDto.setEmail(email);
        signupRequestDto.setAdmin(admin);

        // when
        User user = userService.registerUser(signupRequestDto);

        // then
        assertNotNull(user.getId());
        assertEquals(username, user.getUsername());
        assertTrue(passwordEncoder.matches(password, user.getPassword()));
        assertTrue(passwordEncoder.matches(passwordcheck, user.getPassword()));
        assertEquals(email, user.getEmail());
        assertEquals(UserRoleEnum.USER, user.getRole());

        userId = user.getId();
    }

    @Test
    @Order(2)
    @DisplayName("회원 가입 비밀번호 일치2 실패케이스")
    void test2() {
        // given
        String username = "버민버민";
        String password = "beominonly";
        String passwordcheck = "beomino";
        String email = "beomin12@naver.com";
        boolean admin = false;

        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setPassword(password);
        signupRequestDto.setPasswordcheck(passwordcheck);
        signupRequestDto.setEmail(email);
        signupRequestDto.setAdmin(admin);

        // when

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(signupRequestDto);
        });
        if(exception.getMessage().equals("비밀번호가 같지 않습니다.")) {
            assertEquals(
                    "비밀번호가 같지 않습니다.",
                    exception.getMessage()
            );
        }

        else {
            User user = userService.registerUser(signupRequestDto);

            // then
            assertNotNull(user.getId());
            assertEquals(username, user.getUsername());
            assertTrue(passwordEncoder.matches(password, user.getPassword()));
            assertTrue(passwordEncoder.matches(passwordcheck, user.getPassword()));
            assertEquals(email, user.getEmail());
            assertEquals(UserRoleEnum.USER, user.getRole());

            userId = user.getId();
        }
    }


    @Test
    @Order(3)
    @DisplayName("회원 가입 중복된 아이디 존재1 실패케이스")
    void test3() {
        // given
        String username = "버민버민a1";
        String password = "beominonly";
        String passwordcheck = "beominonly";
        String email = "beomin@naver.com";
        boolean admin = false;

        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setPassword(password);
        signupRequestDto.setPasswordcheck(passwordcheck);
        signupRequestDto.setEmail(email);
        signupRequestDto.setAdmin(admin);



        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(signupRequestDto);
        });
        if(exception.getMessage().equals("중복된 사용자 ID 가 존재합니다.")) {
            assertEquals(
                    "중복된 사용자 ID 가 존재합니다.",
                    exception.getMessage()
            );
        }
        else {
            // when
            User user = userService.registerUser(signupRequestDto);

            // then
            assertNotNull(user.getId());
            assertEquals(username, user.getUsername());
            assertTrue(passwordEncoder.matches(password, user.getPassword()));
            assertTrue(passwordEncoder.matches(passwordcheck, user.getPassword()));
            assertEquals(email, user.getEmail());
            assertEquals(UserRoleEnum.USER, user.getRole());

            userId = user.getId();
        }
    }

    @Test
    @Order(4)
    @DisplayName("회원 가입 중복된 아이디 존재2 실패케이스")
    void test4() {
        // given
        String username = "버민버민a1";
        String password = "beominonly";
        String passwordcheck = "beominonly";
        String email = "beomi124124n@naver.com";
        boolean admin = false;

        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setPassword(password);
        signupRequestDto.setPasswordcheck(passwordcheck);
        signupRequestDto.setEmail(email);
        signupRequestDto.setAdmin(admin);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(signupRequestDto);
        });
        if(exception.getMessage().equals("중복된 사용자 ID 가 존재합니다.")) {
            assertEquals(
                    "중복된 사용자 ID 가 존재합니다.",
                    exception.getMessage()
            );
        }

        else {
            // when
            User user = userService.registerUser(signupRequestDto);

            // then
            assertNotNull(user.getId());
            assertEquals(username, user.getUsername());
            assertTrue(passwordEncoder.matches(password, user.getPassword()));
            assertTrue(passwordEncoder.matches(passwordcheck, user.getPassword()));
            assertEquals(email, user.getEmail());
            assertEquals(UserRoleEnum.USER, user.getRole());

            userId = user.getId();
        }
    }

    @Test
    @Order(5)
    @DisplayName("비밀번호에 닉네임과 같은 값이 포함된 경우1 실패 케이스")
    void test5() {
        // given
        String username = "버민버민버민";
        String password = "버민버민버민123";
        String passwordcheck = "버민버민버민123";
        String email = "beomi12412423n@naver.com";
        boolean admin = false;

        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setPassword(password);
        signupRequestDto.setPasswordcheck(passwordcheck);
        signupRequestDto.setEmail(email);
        signupRequestDto.setAdmin(admin);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(signupRequestDto);
        });
        if(exception.getMessage().equals("비밀번호에 닉네임과 같은 값을 포함할 수 없습니다.")) {
            assertEquals(
                    "비밀번호에 닉네임과 같은 값을 포함할 수 없습니다.",
                    exception.getMessage()
            );
        }

        else {
            // when
            User user = userService.registerUser(signupRequestDto);

            // then
            assertNotNull(user.getId());
            assertEquals(username, user.getUsername());
            assertTrue(passwordEncoder.matches(password, user.getPassword()));
            assertTrue(passwordEncoder.matches(passwordcheck, user.getPassword()));
            assertEquals(email, user.getEmail());
            assertEquals(UserRoleEnum.USER, user.getRole());

            userId = user.getId();
        }


    }

    @Test
    @Order(6)
    @DisplayName("비밀번호에 닉네임과 같은 값이 포함된 경우2 실패 케이스")
    void test6() {
        // given
        String username = "멍충이";
        String password = "멍충이123";
        String passwordcheck = "멍충이123";
        String email = "stupid@naver.com";
        boolean admin = false;

        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setPassword(password);
        signupRequestDto.setPasswordcheck(passwordcheck);
        signupRequestDto.setEmail(email);
        signupRequestDto.setAdmin(admin);


        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(signupRequestDto);
        });
        if(exception.getMessage().equals("비밀번호에 닉네임과 같은 값을 포함할 수 없습니다.")) {
            assertEquals(
                    "비밀번호에 닉네임과 같은 값을 포함할 수 없습니다.",
                    exception.getMessage()
            );
        }
        else {
            // when
            User user = userService.registerUser(signupRequestDto);

            // then
            assertNotNull(user.getId());
            assertEquals(username, user.getUsername());
            assertTrue(passwordEncoder.matches(password, user.getPassword()));
            assertTrue(passwordEncoder.matches(passwordcheck, user.getPassword()));
            assertEquals(email, user.getEmail());
            assertEquals(UserRoleEnum.USER, user.getRole());

            userId = user.getId();
        }
    }

    @Test
    @Order(7)
    @DisplayName("닉네임은 '최소 3자이상, 알파벳 대소문자(a~z, A~z), 숫자(0~9)' 입니다 1")
    void test7() {
        // given
        String username = "as";
        String password = "멍충이123";
        String passwordcheck = "멍충이123";
        String email = "stuspid@naver.com";
        boolean admin = false;

        @Valid
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setPassword(password);
        signupRequestDto.setPasswordcheck(passwordcheck);
        signupRequestDto.setEmail(email);
        signupRequestDto.setAdmin(admin);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
                    userService.registerUser(signupRequestDto);
        });

        if(exception.getMessage().equals("닉네임은 영문 대,소문자 또는 숫자가 1개 이상씩 포함된 3자이상이어야 합니다.")) {
            assertEquals(
                    "닉네임은 영문 대,소문자 또는 숫자가 1개 이상씩 포함된 3자이상이어야 합니다.",
                    exception.getMessage()
            );
        }
        else {
            // when
            User user = userService.registerUser(signupRequestDto);

            // then
            assertNotNull(user.getId());
            assertEquals(username, user.getUsername());
            assertTrue(passwordEncoder.matches(password, user.getPassword()));
            assertTrue(passwordEncoder.matches(passwordcheck, user.getPassword()));
            assertEquals(email, user.getEmail());
            assertEquals(UserRoleEnum.USER, user.getRole());

            userId = user.getId();
        }

    }

    @Test
    @Order(8)
    @DisplayName("닉네임은 '최소 3자이상, 알파벳 대소문자(a~z, A~z), 숫자(0~9)' 입니다 2")
    void test8() {
        // given
        String username = "ds";
        String password = "멍충이123";
        String passwordcheck = "멍충이123";
        String email = "stuspid@naver.com";
        boolean admin = false;

        @Valid
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setPassword(password);
        signupRequestDto.setPasswordcheck(passwordcheck);
        signupRequestDto.setEmail(email);
        signupRequestDto.setAdmin(admin);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(signupRequestDto);
        });

        if(exception.getMessage().equals("닉네임은 영문 대,소문자 또는 숫자가 1개 이상씩 포함된 3자이상이어야 합니다.")) {
            assertEquals(
                    "닉네임은 영문 대,소문자 또는 숫자가 1개 이상씩 포함된 3자이상이어야 합니다.",
                    exception.getMessage()
            );
        }
        else {
            // when
            User user = userService.registerUser(signupRequestDto);

            // then
            assertNotNull(user.getId());
            assertEquals(username, user.getUsername());
            assertTrue(passwordEncoder.matches(password, user.getPassword()));
            assertTrue(passwordEncoder.matches(passwordcheck, user.getPassword()));
            assertEquals(email, user.getEmail());
            assertEquals(UserRoleEnum.USER, user.getRole());

            userId = user.getId();
        }

    }
    @Test
    @Order(9)
    @DisplayName("비밀번호는 4자이상의 비밀번호여야 합니다 1")
    void test9() {
        // given
        String username = "dsqwf12";
        String password = "멍충";
        String passwordcheck = "멍충";
        String email = "stuspi222d@naver.com";
        boolean admin = false;

        @Valid
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setPassword(password);
        signupRequestDto.setPasswordcheck(passwordcheck);
        signupRequestDto.setEmail(email);
        signupRequestDto.setAdmin(admin);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(signupRequestDto);
        });

        if(exception.getMessage().equals("비밀번호는 4자이상의 비밀번호여야 합니다.")) {
            assertEquals(
                    "비밀번호는 4자이상의 비밀번호여야 합니다.",
                    exception.getMessage()
            );
        }
        else {
            // when
            User user = userService.registerUser(signupRequestDto);

            // then
            assertNotNull(user.getId());
            assertEquals(username, user.getUsername());
            assertTrue(passwordEncoder.matches(password, user.getPassword()));
            assertTrue(passwordEncoder.matches(passwordcheck, user.getPassword()));
            assertEquals(email, user.getEmail());
            assertEquals(UserRoleEnum.USER, user.getRole());

            userId = user.getId();
        }

    }
    @Test
    @Order(10)
    @DisplayName("비밀번호는 4자이상의 비밀번호여야 합니다 2")
    void test10() {
        // given
        String username = "dsqwf122";
        String password = "q";
        String passwordcheck = "q";
        String email = "stuspi2w22d@naver.com";
        boolean admin = false;

        @Valid
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername(username);
        signupRequestDto.setPassword(password);
        signupRequestDto.setPasswordcheck(passwordcheck);
        signupRequestDto.setEmail(email);
        signupRequestDto.setAdmin(admin);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerUser(signupRequestDto);
        });

        if(exception.getMessage().equals("비밀번호는 4자이상의 비밀번호여야 합니다.")) {
            assertEquals(
                    "비밀번호는 4자이상의 비밀번호여야 합니다.",
                    exception.getMessage()
            );
        }
        else {
            // when
            User user = userService.registerUser(signupRequestDto);

            // then
            assertNotNull(user.getId());
            assertEquals(username, user.getUsername());
            assertTrue(passwordEncoder.matches(password, user.getPassword()));
            assertTrue(passwordEncoder.matches(passwordcheck, user.getPassword()));
            assertEquals(email, user.getEmail());
            assertEquals(UserRoleEnum.USER, user.getRole());

            userId = user.getId();
        }

    }
}
