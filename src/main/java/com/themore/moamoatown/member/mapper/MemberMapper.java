package com.themore.moamoatown.member.mapper;

import com.themore.moamoatown.member.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 멤버 매퍼 인터페이스
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
 * 2024.08.26  이주현        타운 참가 시 기본 모아 제공 기능 추가
 * 2024.08.26  이주현        멤버 역할 조회
 * 2024.08.26  이주현        멤버 타운 조회
 * 2024.08.26  이주현        멤버 계좌 조회
 * </pre>
 */

@Mapper
public interface MemberMapper {

    // 중복 닉네임 방지하기 위해 닉네임으로 멤버 수 찾아오기
    int countMembersByNickname(String username);

    // 중복 아이디 방지하기 위해 로그인 아이디로 멤버 수 찾아오기
    int countMembersByLoginId(String username);

    // 회원 가입
    int insertMember(SignUpRequestDTO signUpRequestDTO);

    // 로그인
    LoginInternalDTO findMemberByLoginId(String loginId);

    // 타운 코드로 타운 ID 조회
    Long findTownIdByTownCode(String townCode);

    // 타운 ID 업데이트
    int updateMemberTownId(@Param("memberId") Long memberId, @Param("townId") Long townId);

    // 멤버의 기본 투자 데이터를 삽입
    int insertDefaultMemberInvestment(MemberInvestmentDTO investmentData);

    // 재산 조회
    Long findBalanceByMemberId(Long memberId);

    // 순위 조회
    List<MemberRankInternalDTO> getMemberRanks(@Param("currentUserId") Long currentUserId, @Param("townId") Long townId);

    // 멤버의 기본 계좌 데이터를 삽입
    int insertDefaultMemberAccount(Long memberId);

    // 멤버 역할 조회
    MemberJobResponseDTO findApprovedJobByMemberId(Long memberId);

    // 멤버 타운 조회
    MemberTownResponseDTO findTownByMemberId(Long memberId);

    // 멤버 계좌 조회
    List<MemberAccountResponseDTO> findAccountsByMemberIdWithPaging(MemberAccountInternalDTO internalDTO);
}
