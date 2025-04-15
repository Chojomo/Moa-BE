package com.moa.global.email.controller;

import com.moa.global.dto.ApiResponse;
import com.moa.global.email.dto.EmailDto;
import com.moa.global.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class EmailController {

    private final EmailService emailService;

    @PostMapping("/temp-password")
    public ResponseEntity<?> sendTempPasswordMail(@RequestBody EmailDto.TempPasswordRequest dto) {
        emailService.sendTempPasswordMail(dto);

        return ResponseEntity.ok(ApiResponse.ok());
    }

}
