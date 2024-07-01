package com.demo.demo.ServiceImpl;

import com.demo.demo.Service.EmailService;
import com.demo.demo.entity.Test;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;

@Service
public class EmailServiceImpl  implements EmailService {

    private final JavaMailSender javaMailSender;

    public EmailServiceImpl(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
@Override
    public void sendEmail(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        javaMailSender.send(message);
    }
@Override
    public void sendTestByEmail(Test test, String recipientEmail, Map<String, Object> tempData) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(recipientEmail);
        message.setSubject("Your Test Details");
        message.setText("Dear " + tempData.get("candidatefirstname") +
                ",\n\nYou have been invited to take a test. Please find the details below:\n\n" +
                "Test ID: " + tempData.get("id") +
                "\n\nFrom: " + tempData.get("fromname") + " " + tempData.get("fromlastname") +
                "\n\nSignature: " + tempData.get("signature") +
                "\n\n\nBest regards,\nThe Test Team");
        javaMailSender.send(message);
    }
@Override
    public void sendEmailWithAttachment(String to, String subject, String body, File attachment) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            helper.addAttachment(attachment.getName(), attachment);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
