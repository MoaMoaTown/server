package com.themore.moamoatown.member.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.member.dto.SignUpRequestDTO;
import com.themore.moamoatown.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
 * 2024.06.18  	이주현        회원 가입 기능 추가
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
}
