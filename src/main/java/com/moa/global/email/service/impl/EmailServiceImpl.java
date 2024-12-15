package com.moa.global.email.service.impl;

import com.moa.domain.member.entity.User;
import com.moa.domain.member.exception.UserException;
import com.moa.domain.member.exception.UserExceptionCode;
import com.moa.domain.member.repository.UserRepository;
import com.moa.global.email.dto.EmailDto;
import com.moa.global.email.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class EmailServiceImpl implements EmailService {

    private final UserRepository userRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void sendTempPasswordMail(EmailDto.TempPasswordRequest dto) {
        User user = userRepository.findUserByUserEmail(dto.getEmail())
                .orElseThrow(() -> new UserException(UserExceptionCode.EMAIL_NOT_EXISTS));

        String tempPassword = createTempPassword();
        String encode = passwordEncoder.encode(tempPassword);
        String emailContent = createEmailContent(tempPassword);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(user.getUserEmail());
            mimeMessageHelper.setSubject("[MOA] 임시 비밀번호 발급");
            mimeMessageHelper.setText(emailContent, true);
            javaMailSender.send(mimeMessage);

            user.setUserPassword(encode);
            userRepository.save(user);

        } catch (MessagingException e) {
            throw new RuntimeException("이메일 전송 중 오류가 발생했습니다.", e);
        }
    }

    public String createTempPassword() {
        Random random = new Random();
        StringBuffer key = new StringBuffer();

        for (int i = 0; i < 8; i++) {
            int index = random.nextInt(4);

            switch (index) {
                case 0: key.append((char) (random.nextInt(26) + 97)); break;
                case 1: key.append((char) (random.nextInt(26) + 65)); break;
                default: key.append(random.nextInt(9));
            }
        }
        return key.toString();
    }

    private String createEmailContent(String tempPassword) {
        return "<html>" +
                "<body>" +
                "<h1>MOA 임시 비밀번호 발급</h1>" +
                "<p>안녕하세요,</p>" +
                "<p>아래는 귀하의 임시 비밀번호입니다:</p>" +
                "<h3>" + tempPassword + "</h3>" +
                "<p>로그인 후 비밀번호를 변경해 주세요.</p>" +
                "<p>감사합니다.</p>" +
                "</body>" +
                "</html>";
    }

}
