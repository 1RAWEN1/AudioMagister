package com.app.audiomagister.service;

import com.app.audiomagister.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService implements EmailService {

    @Autowired
    public JavaMailSender emailSender;

    @Override
    public void sendPasswordEmail(User user) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(user.getUsername());
        simpleMailMessage.setSubject("Changing password");
        simpleMailMessage.setText("Your current password: " + user.getPassword());
        emailSender.send(simpleMailMessage);
    }
}
