package com.foxminded.university.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@Log4j2
public class UserCredentialGenerator {

    private final PasswordEncoder passwordEncoder;

    public UserCredentialGenerator(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public static String generateUsername(String firstName, String lastName) {
        String username = firstName.toLowerCase() + "." + lastName.toLowerCase();
        log.info("Generated username - {}", username);
        return username;
    }

    public static String generatePassword() {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedPassword = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        log.info("Generated password - {}", generatedPassword);
        return generatedPassword;
    }

    public String encodePassword(String password) {
        String encodedPassword = passwordEncoder.encode(password);
        log.info("Password - {}, encoded password - {}",password, encodedPassword);
        return encodedPassword;
    }
}
