package com.themore.moamoatown.member.service;

import com.themore.moamoatown.member.dto.SignUpRequestDTO;

/**
 * 멤버 서비스 인터페이스
 * @author 이주현
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	이주현        최초 생성
 * </pre>
 */

public interface MemberService {
    // 회원 가입
    void signUp(SignUpRequestDTO signUpRequestDTO);
}
