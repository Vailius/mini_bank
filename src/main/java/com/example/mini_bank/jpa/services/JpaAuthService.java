package com.example.mini_bank.jpa.services;

import org.springframework.stereotype.Service;

import com.example.mini_bank.domain.PasswordEncoder;
import com.example.mini_bank.domain.models.User;
import com.example.mini_bank.domain.services.AuthService;
import com.example.mini_bank.jpa.entities.UserEntity;
import com.example.mini_bank.jpa.repositories.JpaUserRepository;
import com.example.mini_bank.jpa.repositories.UserRepositoryBinding;

import jakarta.transaction.Transactional;

@Service
public class JpaAuthService {
    private final AuthService domainService;
    private final JpaUserRepository userRepository;

    public JpaAuthService(JpaUserRepository userRepository, PasswordEncoder encoder) {
        domainService = new AuthService(new UserRepositoryBinding(userRepository), encoder);
        this.userRepository = userRepository;
    }

    public UserEntity login(String email, String password) {
        User user = domainService.login(email, password);
        if (user == null) {
            return null;
        }
        return userRepository.findById(user.id()).get();
    }

    @Transactional
    public UserEntity register(String email, String password) {
        User user = domainService.register(email, password);
        if (user == null) {
            return null;
        }
        return userRepository.findById(user.id()).get();
    }
}