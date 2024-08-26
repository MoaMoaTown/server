package com.themore.moamoatown.word.controller;

import com.themore.moamoatown.common.annotation.Auth;
import com.themore.moamoatown.word.dto.WordResponseDTO;
import com.themore.moamoatown.word.service.WordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 단어 컨트롤러
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

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/word", produces = "application/json; charset=UTF-8")
@Slf4j
@Auth(role = Auth.Role.CITIZEN)
public class WordController {
    private final WordService wordService;

    /**
     * 단어에 대한 설명 조회
     * @author 이주현
     * @param selectedWord 의미를 검색할 단어
     * @return ResponseEntity
     */
    @GetMapping("/{selectedWord}")
    public ResponseEntity<WordResponseDTO> searchWordDescription(@PathVariable("selectedWord") String selectedWord) {
        WordResponseDTO response = wordService.searchWordDescription(selectedWord);
        return ResponseEntity.ok(response);
    }
}
