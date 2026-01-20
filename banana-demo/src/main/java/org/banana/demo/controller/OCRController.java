package org.banana.demo.controller;

import org.banana.common.annotation.Function;
import org.banana.common.annotation.Functions;
import org.banana.demo.service.DeepSeekOCRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Functions
public class OCRController {

    @Autowired
    private DeepSeekOCRService ocrService;

    @Function
    public Mono<ResponseEntity<OCRResult>> extractText(
            @RequestParam("file") MultipartFile file) {
        return ocrService.extractTextToObject(file)
                .map(result -> ResponseEntity.ok(result))
                .onErrorReturn(ResponseEntity.badRequest().build());
    }
}