package com.example.AddressBookApp.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class EmailSenderService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.sender}")
    private String fromEmail;

    public void sendEmail(String toEmail, String subject, String body) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(fromEmail);
        simpleMailMessage.setTo(toEmail);
        simpleMailMessage.setText(body);
        simpleMailMessage.setSubject(subject);

        mailSender.send(simpleMailMessage);
        System.out.println("Mail sent to the user!!");
    }
}