package com.themore.moamoatown.member.controller;

import com.themore.moamoatown.common.annotation.Auth;
import com.themore.moamoatown.common.annotation.MemberId;
import com.themore.moamoatown.common.annotation.TownId;
import com.themore.moamoatown.member.dto.*;
import com.themore.moamoatown.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 멤버 컨트롤러
 * @author 이주현
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  이주현        최초 생성
 * 2024.08.23  이주현        회원 가입 기능 추가
 * 2024.08.24  이주현        로그인 기능 추가
 * 2024.08.25  이주현        타운 참가 기능 추가
 * 2024.08.25  이주현        재산 조회 기능 추가
 * 2024.08.25  이주현        타운 내 순위 리스트 조회 기능 추가
 * 2024.08.26  이주현        멤버 역할 조회
 * 2024.08.26  이주현        멤버 타운 조회
 * 2024.08.26  이주현        멤버 계좌 조회
 * </pre>
 */

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/member", produces = "application/json; charset=UTF-8")
@Slf4j
public class MemberController {
    private final MemberService memberService;

    /**
     * 회원 가입
     * @param signUpRequestDTO
     * @return ResponseEntity
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
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequestDTO, HttpSession session) {
        LoginInternalDTO memberInfo = memberService.login(loginRequestDTO);

        // 세션에 memberId와 townId와 role 저장
        session.setAttribute("memberId", memberInfo.getMemberId());
        session.setAttribute("townId", memberInfo.getTownId());
        session.setAttribute("role", memberInfo.getRole());

        // 클라이언트에게 반환할 DTO
        LoginResponseDTO response = LoginResponseDTO.builder()
                .nickname(memberInfo.getNickname())
                .role(memberInfo.getRole())
                .hasTownId(memberInfo.getTownId() != -1)
                .build();

        return ResponseEntity.ok(response);
    }

    /**
     * 타운 참가
     * @param joinTownRequestDTO
     * @param memberId 세션에서 가져온 멤버 아이디
     * @param session
     * @return ResponseEntity
     */
    @PostMapping("/join-town")
    public ResponseEntity<String> joinTown(@RequestBody JoinTownRequestDTO joinTownRequestDTO, @MemberId Long memberId, HttpSession session) {
        // 타운 참가하고 타운 ID 반환
        Long townId = memberService.joinTown(joinTownRequestDTO, memberId);
        // 세션에 townId 저장
        session.setAttribute("townId", townId);
        return ResponseEntity.ok("타운 참가에 성공했습니다.");
    }

    /**
     * 멤버 재산 조회
     * @param memberId 세션에서 가져온 멤버 아이디
     * @return ResponseEntity
     */
    @Auth(role = Auth.Role.CITIZEN)
    @GetMapping("/balance")
    public ResponseEntity<MemberBalanceResponseDTO> getMemberBalance(@MemberId Long memberId) {
        MemberBalanceResponseDTO response = memberService.getMemberBalance(memberId);
        return ResponseEntity.ok(response);
    }

    /**
     * 타운 내 순위 리스트 조회
     * @param currentUserId
     * @param townId
     * @return ResponseEntity
     */
    @Auth(role = Auth.Role.CITIZEN)
    @GetMapping("/ranks")
    public ResponseEntity<List<MemberRankResponseDTO>> getMemberRanks(@MemberId Long currentUserId, @TownId Long townId) {
        List<MemberRankResponseDTO> response = memberService.getMemberRanks(currentUserId, townId);
        return ResponseEntity.ok(response);
    }

    /**
     * 멤버 역할 조회
     * @param memberId 세션에서 가져온 멤버 아이디
     * @return ResponseEntity
     */
    @Auth(role = Auth.Role.CITIZEN)
    @GetMapping("/job")
    public ResponseEntity<MemberJobResponseDTO> getMemberJob(@MemberId Long memberId) {
        MemberJobResponseDTO response = memberService.getMemberJob(memberId);
        return ResponseEntity.ok(response);
    }

    /**
     * 멤버 타운 조회
     * @param memberId 세션에서 가져온 멤버 아이디
     * @return ResponseEntity
     */
    @Auth(role = Auth.Role.CITIZEN)
    @GetMapping("/town")
    public ResponseEntity<MemberTownResponseDTO> getMemberTown(@MemberId Long memberId) {
        MemberTownResponseDTO response = memberService.getMemberTown(memberId);
        return ResponseEntity.ok(response);
    }

    /**
     * 멤버 계좌 조회
     * @param memberId
     * @param page 요청한 페이지 번호
     * @param size 페이지 당 데이터 수
     * @return ResponseEntity<List<AccountResponseDTO>>
     */
    @Auth(role = Auth.Role.CITIZEN)
    @GetMapping("/account")
    public ResponseEntity<List<MemberAccountResponseDTO>> getMemberAccounts(
            @MemberId Long memberId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<MemberAccountResponseDTO> response = memberService.getAccountsByMemberId(memberId, page, size);
        return ResponseEntity.ok(response);
    }
}
