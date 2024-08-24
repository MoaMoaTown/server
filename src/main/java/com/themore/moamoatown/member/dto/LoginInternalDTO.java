package com.themore.moamoatown.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 회원 가입 Request DTO
 * @author 이주현
 * @since 2024.08.24
 * @version 1.0
 *
 * <pre>
 * 수정일        	수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.24  	이주현        최초 생성
 * </pre>
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class LoginInternalDTO {
    private Long memberId;
    private Long townId;
    private String nickname;
    private Long role;
    private String password;
}
