package com.themore.moamoatown.member.controller;

import com.themore.moamoatown.member.dto.SignUpRequestDTO;
import com.themore.moamoatown.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 멤버 컨트롤러
 * @author 이주현
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	이주현        최초 생성
 * 2024.08.23  	이주현        회원 가입 기능 추가
 * </pre>
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Slf4j
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원 가입
     * @param signUpRequestDTO
     * @return
     */
    @PostMapping("/sign-up")
    public ResponseEntity<String> signup(@RequestBody SignUpRequestDTO signUpRequestDTO) {
        memberService.signUp(signUpRequestDTO);
        return ResponseEntity.ok("회원 가입에 성공했습니다.");
    }
}
