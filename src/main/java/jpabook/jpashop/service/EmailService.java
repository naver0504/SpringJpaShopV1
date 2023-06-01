package jpabook.jpashop.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

import static jpabook.jpashop.ConstString.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender javaMailSender;
    private String authNum;
    private final SpringTemplateEngine templateEngine;

    public void createCode() {
        String uuid = UUID.randomUUID().toString();
        authNum = uuid.substring(0, 5);
    }

    public MimeMessage createEmailForm(String email) throws MessagingException {
        createCode();
        String toEmail = email;
        String title = "email 연습";
        log.info("toEmail = {}", email);
        log.info("auth = {}", authNum);
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setSubject(title);
        message.setFrom(EMAIL_SENDER);
        message.addRecipients(MimeMessage.RecipientType.TO, email);
        message.setText(setContext(authNum));

        return message;
    }

    public String sendEmail(String toEmail) throws MessagingException {
        MimeMessage emailForm = createEmailForm(toEmail);
        javaMailSender.send(emailForm);
        return authNum;

    }

    public String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context);
    }
}
