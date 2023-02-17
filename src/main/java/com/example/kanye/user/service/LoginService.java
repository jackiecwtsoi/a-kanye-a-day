package com.example.kanye.user.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    // Verify user information
    public void verifyUser(String email, String password) {
        LOGGER.info("Verified user.");
    }
}
