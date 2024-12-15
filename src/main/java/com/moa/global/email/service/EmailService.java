package com.moa.global.email.service;

import com.moa.global.email.dto.EmailDto;

public interface EmailService {

    void sendTempPasswordMail(EmailDto.TempPasswordRequest dto);

}
