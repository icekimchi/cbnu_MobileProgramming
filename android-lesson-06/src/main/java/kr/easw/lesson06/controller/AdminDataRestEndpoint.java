package kr.easw.lesson06.controller;

import kr.easw.lesson06.model.dto.TextDataDto;
import kr.easw.lesson06.service.TextDataService;
import kr.easw.lesson06.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

import java.util.Map;

// @RestController 어노테이션을 사용하여 이 클래스가 REST 컨트롤러임을 선언합니다.
@RestController
// @RequestMapping 어노테이션을 사용하여 이 클래스의 기반 엔드포인트를 /api/v1/data/admin으로 설정합니다.
@RequestMapping("/api/v1/data/admin")
// final로 지정된 모든 필드를 파라미터로 가지는 생성자를 생성합니다.
@RequiredArgsConstructor
public class AdminDataRestEndpoint {

    private final TextDataService textDataService;

    @PostMapping(value = "/add",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ModelAndView addText(@RequestParam MultiValueMap<String, String> data) {
        textDataService.addText(new TextDataDto(0L, data.getFirst("text")));
        return new ModelAndView("redirect:/admin?success=true");
    }

    @PostMapping(value = "/add",consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ModelAndView addText(@RequestBody Map<String, String> body) {
        textDataService.addText(new TextDataDto(0L, body.get("text")));
        return new ModelAndView("redirect:/admin?success=true");
    }
}
