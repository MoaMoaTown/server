package com.themore.moamoatown.common.interceptor;

import com.themore.moamoatown.common.annotation.Auth;
import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.member.service.MemberService;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.themore.moamoatown.common.exception.ErrorCode.UNAUTHORIZED_ERROR;

/**
 * 권한 체크 인터셉터
 * @author 임원정
 * @since 2024.08.26
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.26  	임원정        최초 생성
 * </pre>
 */

@Component
@NoArgsConstructor
@Log4j
public class AuthInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private MemberService memberService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        HttpSession session = request.getSession(false);

        // 메소드 어노테이션 확인
        Auth auth = handlerMethod.getMethodAnnotation(Auth.class);

        // 클래스 수준의 Auth 어노테이션 확인
        if (auth == null) {
            auth = handlerMethod.getBeanType().getAnnotation(Auth.class);
        }

        // Auth 어노테이션 안 달렸을 경우
        if (auth == null) {
            return true;
        }

        if (session != null) {
            Long memberId =  (Long) session.getAttribute("memberId");
            Long role =  (Long) session.getAttribute("role");

            if (memberId != null) {
                Auth.Role userRole = mapRole(role);
                // 요구되는 권한이 있는지 체크
                if (userRole != auth.role()) {
                    log.info("%%%%%%%%%%%%%%%%%%%%%%%%");
                    log.info("권한이 부족합니다");
                    log.info("필요 권한: " + auth.role());
                    log.info("%%%%%%%%%%%%%%%%%%%%%%%%");

                    throw new CustomException(UNAUTHORIZED_ERROR);
                }
                return true;
            }
        }
        return false;
    }

    private Auth.Role mapRole(Long role) {
        if (role == 1L) {
            return Auth.Role.MAYER;
        } else {
            return Auth.Role.CITIZEN;
        }
    }
}
