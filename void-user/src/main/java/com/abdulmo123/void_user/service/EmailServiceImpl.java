package com.abdulmo123.void_user.service;

import com.abdulmo123.void_user.config.AppProperties;
import com.abdulmo123.void_user.model.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private final AppProperties appProperties;

    @Override
    public void   sendWelcomeEmail(User user) {
        Context context = new Context();
        context.setVariable("username", user.getUsername());
        context.setVariable("email", user.getEmail());
        context.setVariable("appUrl", appProperties.getAppUrl());

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy", Locale.ENGLISH);
        String date = user.getCrtTs().format(dateTimeFormatter);
        context.setVariable("joinedDate", date);


        String welcomeHtml = templateEngine.process("welcome", context);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, "UTF-8");
            helper.setFrom(appProperties.getDummyEmail());
            helper.setTo(appProperties.getDummyEmail());
            helper.setSubject("Welcome to Void!");
            helper.setText(welcomeHtml, true);

            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send welcome email!", e);
        } catch (MailSendException e) {
            log.error("Mail sending exception!");
        }

        log.info("Message FROM: {} sent successfully TO: {}", appProperties.getDummyEmail(), appProperties.getDummyEmail());
    }
}
