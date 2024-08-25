package com.themore.moamoatown.word.service;

import com.themore.moamoatown.word.dto.WordResponseDTO;

/**
 * 단어 서비스 인터페이스
 * @author 이주현
 * @since 2024.08.25
 * @version 1.0
 *
 * <pre>
 * 수정일        수정자        수정내용
 * ----------  --------    ---------------------------
 * 2024.08.25  이주현        최초 생성
 * </pre>
 */

public interface WordService {
    // 회원 가입
    WordResponseDTO searchWordDescription(String selectedWord);
}
