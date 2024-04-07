package com.example.server.services;

import com.example.server.mail.MailBuilder;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

@Service
@AllArgsConstructor
public class MailService {
    private final MailBuilder mailBuilder;
    private final JavaMailSender mailSender;

    @Async
    public void send(String to, String subject, String message) {
        MimeMessagePreparator mailMessage = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("twoj-plan@agh.edu.pl");
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(mailBuilder.build(message));
        };

        try {
            mailSender.send(mailMessage);
            System.out.println("Email sent to " + to);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to send email to " + to);
        }

    }
}