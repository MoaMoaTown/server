package com.themore.moamoatown.member.service;

import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.member.dto.*;
import com.themore.moamoatown.member.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.themore.moamoatown.common.exception.ErrorCode.*;

/**
 * 멤버 서비스 구현체
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
 * 2024.08.26  이주현        타운 참가 시 기본 모아 제공 기능 추가
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
     * @return loginInternalDTO
     */
    @Override
    @Transactional(readOnly = true)
    public LoginInternalDTO login(LoginRequestDTO loginRequestDTO) {
        LoginInternalDTO memberInfo = memberMapper.findMemberByLoginId(loginRequestDTO.getLoginId());

        if (memberInfo == null || !passwordEncoder.matches(loginRequestDTO.getPassword(), memberInfo.getPassword())) {
            throw new CustomException(LOGIN_FAILED);
        }

        return memberInfo;
    }

    /**
     * 타운 참가
     * @param joinTownRequestDTO 타운 참가 요청 DTO
     * @param memberId 세션에서 가져온 멤버 아이디
     */
    @Override
    @Transactional
    public Long joinTown(JoinTownRequestDTO joinTownRequestDTO, Long memberId) {
        // 타운 코드로 타운 아이디 조회
        Long townId = memberMapper.findTownIdByTownCode(joinTownRequestDTO.getTownCode());
        if (townId == null) {
            throw new CustomException(TOWN_NOT_FOUND);
        }

        // 회원의 타운 아이디 업데이트
        memberMapper.updateMemberTownId(memberId, townId);

        // 기본 투자 데이터 삽입 (type_id 0, 1)
        memberMapper.insertDefaultMemberInvestment(
                MemberInvestmentDTO.builder()
                        .typeId(0L)
                        .memberId(memberId)
                        .build()
        );

        memberMapper.insertDefaultMemberInvestment(
                MemberInvestmentDTO.builder()
                        .typeId(1L)
                        .memberId(memberId)
                        .build()
        );

        // 타운 참가 시 50모아 제공
        memberMapper.insertDefaultMemberAccount(memberId);

        return townId;
    }

    /**
     * 내 재산 조회
     * @param memberId
     * @return balance
     */
    @Override
    @Transactional(readOnly = true)
    public MemberBalanceResponseDTO getMemberBalance(Long memberId) {
        Long balance = memberMapper.findBalanceByMemberId(memberId);
        if (balance == null) {
            throw new CustomException(BALANCE_NOT_FOUND);
        }
        return MemberBalanceResponseDTO.builder()
                .balance(balance)
                .build();
    }

    /**
     * 타운 내 순위 리스트 조회
     * @param currentUserId
     * @param townId
     * @return MemberRankResponseDTO List
     */
    @Override
    @Transactional(readOnly = true)
    public List<MemberRankResponseDTO> getMemberRanks(Long currentUserId, Long townId) {
        return memberMapper.getMemberRanks(currentUserId, townId)
                .stream()
                .map(member -> MemberRankResponseDTO.builder()
                        .profile(member.getProfile())
                        .nickname(member.getNickname())
                        .balance(member.getBalance())
                        .isCurrentUser(member.getIsCurrentUser())
                        .build())
                .collect(Collectors.toList());
    }
}
