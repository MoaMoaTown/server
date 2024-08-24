package com.themore.moamoatown.member.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.member.dto.LoginInternalDTO;
import com.themore.moamoatown.member.dto.LoginRequestDTO;
import com.themore.moamoatown.member.dto.LoginResponseDTO;
import com.themore.moamoatown.member.dto.SignUpRequestDTO;
import com.themore.moamoatown.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static com.themore.moamoatown.common.exception.ErrorCode.*;

/**
 * 멤버 서비스 구현체
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

@RequiredArgsConstructor
@Service
public class MemberServiceImpl implements MemberService{
    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 회원 가입
     * @param signUpRequestDTO
     */
    @Override
    @Transactional
    public void signUp(SignUpRequestDTO signUpRequestDTO) {
        if (0 < memberMapper.countMembersByNickname(signUpRequestDTO.getNickname())) throw new CustomException(NICKNAME_ALREADY_EXISTS);
        if (0 < memberMapper.countMembersByLoginId(signUpRequestDTO.getLoginId())) throw new CustomException(LOGINID_ALREADY_EXISTS);

        String encodedPassword = passwordEncoder.encode(signUpRequestDTO.getPassword());

        SignUpRequestDTO encryptedSignUpRequestDTO = SignUpRequestDTO.builder()
                .loginId(signUpRequestDTO.getLoginId())
                .nickname(signUpRequestDTO.getNickname())
                .password(encodedPassword)
                .role(signUpRequestDTO.getRole())
                .build();

        long result = memberMapper.insertMember(encryptedSignUpRequestDTO);
        if (result < 0) throw new CustomException(SIGNUP_FAILED);
    }

    /**
     * 로그인
     * @param loginRequestDTO
     * @return loginResponseDTO
     */
    @Override
    @Transactional(readOnly = true)
    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO, HttpSession session) {
        LoginInternalDTO memberInfo = memberMapper.findMemberByLoginId(loginRequestDTO.getLoginId());

        if (memberInfo == null || !passwordEncoder.matches(loginRequestDTO.getPassword(), memberInfo.getPassword())) {
            throw new CustomException(LOGIN_FAILED);
        }

        // 세션에 memberId와 townId 저장
        session.setAttribute("memberId", memberInfo.getMemberId());
        session.setAttribute("townId", memberInfo.getTownId());

        // 타운 아이디가 -1이면 hasTownId를 false로 설정, 그렇지 않으면 true로 설정
        boolean hasTownId = memberInfo.getTownId() != -1;

        // LoginResponseDTO 빌드 및 반환
        return LoginResponseDTO.builder()
                .nickname(memberInfo.getNickname())
                .role(memberInfo.getRole())
                .hasTownId(hasTownId)
                .build();
    }
}
