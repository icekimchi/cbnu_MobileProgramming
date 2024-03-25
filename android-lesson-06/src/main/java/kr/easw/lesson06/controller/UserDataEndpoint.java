package kr.easw.lesson06.controller;

import kr.easw.lesson06.model.dto.UserDataEntity;
import kr.easw.lesson06.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @RestController 어노테이션을 사용하여 이 클래스가 REST 컨트롤러임을 선언합니다.
@RestController
// @RequestMapping 어노테이션을 사용하여 이 클래스의 기반 엔드포인트를 /api/v1/data로 설정합니다.
@RequestMapping("/api/v1/user")
// final로 지정된 모든 필드를 파라미터로 가지는 생성자를 생성합니다.
@RequiredArgsConstructor
public class UserDataEndpoint {
    // 원래대로라면 리스트를 통해 JSON에서 사용할 수 있는 형태로 변환해야 하지만, 이번 실습에서는 건너뜁니다.
    // 회원 목록을 반환하는 메서드

    private final UserDataService userDataService;
    @GetMapping("/list")
    public List<String> listUsers() {
        return userDataService.listUsers();
    }

    // 원래대로라면 리스트를 통해 JSON에서 사용할 수 있는 형태로 변환해야 하지만, 이번 실습에서는 건너뜁니다.
    @DeleteMapping("/remove/{userId}")
    public ResponseEntity<String> removeUser(@PathVariable String userId) {
        try {
            userDataService.removeUser(userId);
            return ResponseEntity.ok("User removed successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to remove user: " + e.getMessage());
        }
    }}
