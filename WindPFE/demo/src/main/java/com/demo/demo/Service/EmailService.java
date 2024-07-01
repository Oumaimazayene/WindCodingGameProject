package com.demo.demo.Service;

import com.demo.demo.entity.Test;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Map;
public interface EmailService {
    void sendTestByEmail(Test test, String recipientEmail, Map<String, Object> tempData);
    void sendEmail(String to, String subject, String body);
     void sendEmailWithAttachment(String to, String subject, String body, File attachment);
}
