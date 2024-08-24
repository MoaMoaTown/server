package com.themore.moamoatown.member.controller;

import com.themore.moamoatown.member.dto.LoginInternalDTO;
import com.themore.moamoatown.member.dto.LoginRequestDTO;
import com.themore.moamoatown.member.dto.LoginResponseDTO;
import com.themore.moamoatown.member.dto.SignUpRequestDTO;
import com.themore.moamoatown.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

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
 * 2024.08.24   이주현        로그인 기능 추가
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

    /**
     * 로그인
     * @param loginRequestDTO
     * @param session
     * @return 로그인된 사용자의 정보와 함께 ResponseEntity 반환
     */
    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session) {
        LoginResponseDTO response = memberService.login(loginRequestDTO, session);
        return ResponseEntity.ok(response);
    }
}
