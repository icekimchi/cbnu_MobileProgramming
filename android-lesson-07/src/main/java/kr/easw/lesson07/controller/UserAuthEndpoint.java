package kr.easw.lesson07.controller;

import kr.easw.lesson07.model.dto.ExceptionalResultDto;
import kr.easw.lesson07.model.dto.UserAuthenticationDto;
import kr.easw.lesson07.model.dto.UserDataEntity;
import kr.easw.lesson07.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserAuthEndpoint {
    private final UserDataService userDataService;


    // JWT 인증을 위해 사용되는 엔드포인트입니다.
    @PostMapping("/login")
    public ResponseEntity<Object> login() {
        try {
            // 로그인한 사용자의 정보를 가져옵니다.
            UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String username = userDetails.getUsername();
            return ResponseEntity.ok("Logged in as: " + username);
        } catch (Exception ex) {
            // 로그인에 실패하면 예외를 처리합니다.
            return ResponseEntity.badRequest().body(new ExceptionalResultDto(ex.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserDataEntity entity) {
        try {
            // 회원가입 로직을 구현합니다.
            userDataService.createUser(entity);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception ex) {
            // 회원가입에 실패하면 예외를 처리합니다.
            return ResponseEntity.badRequest().body(new ExceptionalResultDto(ex.getMessage()));
        }
    }
}