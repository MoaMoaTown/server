package com.themore.moamoatown.town.service;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 타운 코드 생성 클래스
 * @author 임원정
 * @since 2024.08.23
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.23  	임원정        최초 생성
 * 2024.08.24   임원정        문자 추가(대문자 + 숫자)
 * </pre>
 */

public class TownCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;
    private static final Random RANDOM = new SecureRandom();

    public static String generateTownCode() {
        StringBuilder townCode = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            townCode.append(CHARACTERS.charAt(RANDOM.nextInt(CHARACTERS.length())));
        }
        return townCode.toString();
    }
}
