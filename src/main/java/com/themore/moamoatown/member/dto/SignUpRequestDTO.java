package com.themore.moamoatown.member.dto;

import lombok.*;

/**
 * 회원 가입 Request DTO
 * @author 이주현
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  이주현        최초 생성
 * </pre>
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SignUpRequestDTO {
    private String nickname;
    private String loginId;
    private String password;
    private Long role;
}
