package com.themore.moamoatown.word.mapper;

import com.themore.moamoatown.word.dto.WordInternalDTO;
import com.themore.moamoatown.word.dto.WordResponseDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 단어 매퍼 인터페이스
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

@Mapper
public interface WordMapper {
    WordResponseDTO findBySelectedWord(@Param("selectedWord") String selectedWord);
    int insertWord(WordInternalDTO wordInternalDTO);
}
