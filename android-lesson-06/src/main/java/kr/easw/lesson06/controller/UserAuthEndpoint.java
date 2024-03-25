package kr.easw.lesson06.controller;

import kr.easw.lesson06.model.dto.ExceptionalResultDto;
import kr.easw.lesson06.model.dto.UserAuthenticationDto;
import kr.easw.lesson06.model.dto.UserDataEntity;
import kr.easw.lesson06.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class UserAuthEndpoint {
    private final UserDataService userDataService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserDataEntity entity) {
        try {
            return ResponseEntity.ok(userDataService.createTokenWith(entity));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(new ExceptionalResultDto(ex.getMessage()));
        }
    }

    @PostMapping("/register")
    public void register(@RequestBody UserDataEntity entity) {
        // 유저 회원가입을 구현하십시오.
        // 해당 메서드를 작성하기 위해서는, UserDataService와 admin_dashboard.html을 참고하십시오.
        // 해당 메서드는 register.html에서 사용됩니다.
        throw new IllegalStateException("Not implemented yet");
    }
}
