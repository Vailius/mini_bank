package com.example.mini_bank.domain.services;
import com.example.mini_bank.domain.exceptions.NonExistentUserWithEmail;
import com.example.mini_bank.domain.exceptions.EmailAlreadyRegistered;
import com.example.mini_bank.domain.PasswordEncoder;
import com.example.mini_bank.domain.models.User;
import com.example.mini_bank.domain.repositories.UserRepository;
import java.util.UUID;
import java.util.Optional;

public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NonExistentUserWithEmail(email));
        if (passwordEncoder.matches(password, user.password())) {
            return user;
        }
        throw new NonExistentUserWithEmail(email);
    }

    public User register(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyRegistered(email);
        }
        User user = new User(email, passwordEncoder.encode(password));
        return userRepository.save(user);
    }
}