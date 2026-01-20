package org.banana.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

@Service
public class DeepSeekOCRService {

    @Autowired
    private WebClient webClient;

    public Mono<String> extractText(MultipartFile imageFile) {
        return webClient.post()
                .uri("/ocr/v1/extract")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData("image", imageFile.getResource()))
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<OCRResult> extractTextToObject(MultipartFile imageFile) {
        return extractText(imageFile)
                .map(this::parseOCRResult);
    }

    private OCRResult parseOCRResult(String jsonResponse) {
        // 解析API返回的JSON结果
        return new ObjectMapper().readValue(jsonResponse, OCRResult.class);
    }
}