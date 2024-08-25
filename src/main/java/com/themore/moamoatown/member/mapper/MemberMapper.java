package com.themore.moamoatown.member.mapper;

import com.themore.moamoatown.member.dto.LoginInternalDTO;
import com.themore.moamoatown.member.dto.MemberInvestmentDTO;
import com.themore.moamoatown.member.dto.SignUpRequestDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 멤버 매퍼 인터페이스
 * @author 이주현
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	이주현        최초 생성
 * 2024.08.24   이주현        로그인 기능 추가
 * 2024.08.25   이주현        타운 참가 기능 추가
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
}
