package com.example.service;

import com.example.domain.Administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PasswordHashingService {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    public void hashPasswords() {
        List<Administrator> administrators = administratorService.getAllAdministrators();
        for (Administrator administrator : administrators) {
            String hashedPassword = passwordEncoder.encode(administrator.getPassword());
            administrator.setPassword(hashedPassword);
            administratorService.updateAdministrator(administrator);
        }
    }
}
