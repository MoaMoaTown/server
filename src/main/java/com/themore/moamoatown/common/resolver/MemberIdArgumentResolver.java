package com.themore.moamoatown.common.resolver;

import com.themore.moamoatown.common.annotation.MemberId;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 파라미터가 @MemberId 어노테이션으로 마크되어 있는지 확인
        return parameter.getParameterAnnotation(MemberId.class) != null;
    }
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        HttpSession session = request.getSession(false);

        if (session != null) {

            // 현재 세션에서 memberId를 추출
            return (Long) session.getAttribute("memberId");
        }
        return null; // 세션이 null인 경우 null 반환
    }
}