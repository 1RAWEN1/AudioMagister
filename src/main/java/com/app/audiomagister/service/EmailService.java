package com.app.audiomagister.service;

import com.app.audiomagister.domain.User;

public interface EmailService {
    void sendPasswordEmail(User user);
}
