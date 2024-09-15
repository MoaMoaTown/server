package com.themore.moamoatown.word.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.themore.moamoatown.common.exception.CustomException;
import com.themore.moamoatown.word.dto.WordInternalDTO;
import com.themore.moamoatown.word.dto.WordResponseDTO;
import com.themore.moamoatown.word.mapper.WordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.themore.moamoatown.common.exception.ErrorCode.*;

/**
 * 단어 서비스 구현체
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

@Service
@RequiredArgsConstructor
public class WordServiceImpl implements WordService {
    private final WordMapper wordMapper;

    @Value("${openai.api-key}")
    private String OPENAI_API_KEY;
    private final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    /**
     * 단어 설명 조회
     * @param selectedWord
     * @return WordResponseDTO
     */
    @Override
    @Transactional(readOnly = true)
    public WordResponseDTO searchWordDescription(String selectedWord) {
        // DB에 단어가 존재하는지 확인
        WordResponseDTO word = wordMapper.findBySelectedWord(selectedWord);

        // 단어가 존재하면 설명만 반환
        if (word != null) {
            return WordResponseDTO.builder().description(word.getDescription()).build();
        }

        // 단어가 존재하지 않으면 ChatGPT로부터 설명을 가져옴
        String description = getWordDefinitionFromGPT(selectedWord);
        if (description == null) {
            throw new CustomException(DESCRIPTION_NOT_FOUND);
        } else {
            WordInternalDTO newWord = WordInternalDTO.builder()
                    .selectedWord(selectedWord)
                    .description(description)
                    .build();
            int insertResult = wordMapper.insertWord(newWord);
            if (insertResult <= 0) {
                throw new CustomException(WORD_INSERT_FAILED);
            }
            return WordResponseDTO.builder().description(description).build();
        }
    }

    /**
     * ChatGPT로부터 단어 정의를 가져옴
     * @param selectedWord
     * @return description
     */
    private String getWordDefinitionFromGPT(String selectedWord) {
        String[] messages = {
                "{\"role\": \"system\", \"content\": \"You are a helpful assistant.\"}",
                "{\"role\": \"user\", \"content\": \"" + selectedWord + "에 대한 설명을 어린이들도 이해하도록 쉽게 답변해줘. 동음이의어가 있을 때는 금융, 경제에 관련된 의미로 알려줘. 답변 길이는 무조건 100자 이내로 해줘. 딱 단어에 대한 설명만 알려주면 돼. 100자 이내로\"}"
        };

        String model = "gpt-3.5-turbo";
        double temperature = 0.5;
        int n = 1;

        try {
            URL url = new URL(OPENAI_API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + OPENAI_API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String data = "{\"model\": \"" + model + "\", \"temperature\": " + temperature + ", \"n\": " + n
                    + ", \"messages\": [" + String.join(",", messages) + "]}";

            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = data.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = bufferedReader.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                String content = null;
                if (response.toString() != null) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(response.toString());
                    content = rootNode.get("choices").get(0).get("message").get("content").asText();
                }

                return content;
            }

        } catch (IOException e) {
            e.printStackTrace();
            throw new CustomException(GPT_REQUEST_FAILED);
        }
    }
}
