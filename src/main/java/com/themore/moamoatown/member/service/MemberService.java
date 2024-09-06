package com.themore.moamoatown.member.service;

import com.themore.moamoatown.member.dto.*;

import java.util.List;

/**
 * 멤버 서비스 인터페이스
 * @author 이주현
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  이주현        최초 생성
 * 2024.08.24  이주현        로그인 기능 추가
 * 2024.08.25  이주현        타운 참가 기능 추가
 * 2024.08.25  이주현        재산 조회 기능 추가
 * 2024.08.25  이주현        타운 내 순위 리스트 조회 기능 추가
 * 2024.08.26  이주현        멤버 역할 조회
 * 2024.08.26  이주현        멤버 타운 조회
 * 2024.08.26  이주현        멤버 계좌 조회
 * 2024.09.06  이주현        이자 지급 프로세스 추가
 * </pre>
 */

public interface MemberService {
    // 회원 가입
    void signUp(SignUpRequestDTO signUpRequestDTO);

    // 로그인
    LoginInternalDTO login(LoginRequestDTO loginRequestDTO);

    // 타운 참가
    Long joinTown(JoinTownRequestDTO joinTownRequestDTO, Long memberId);

    // 재산 조회
    MemberBalanceResponseDTO getMemberBalance(Long memberId);

    // 타운 내 순위 리스트 조회
    List<MemberRankResponseDTO> getMemberRanks(Long currentUserId, Long townId);

    // 멤버 역할 조회
    MemberJobResponseDTO getMemberJob(Long memberId);

    // 멤버 타운 조회
    MemberTownResponseDTO getMemberTown(Long memberId);

    // 멤버 계좌 조회
    List<MemberAccountResponseDTO> getAccountsByMemberId(Long memberId, int page, int size);

    // 이자 지급 프로세스
    void processInterestPayment();
}
