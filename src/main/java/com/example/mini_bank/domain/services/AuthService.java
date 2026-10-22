package com.example.mini_bank.domain.services;
import com.example.mini_bank.domain.PasswordEncoder;
import com.example.mini_bank.domain.exceptions.EmailAlreadyRegistered;
import com.example.mini_bank.domain.exceptions.NonExistentUser;
import com.example.mini_bank.domain.exceptions.NullHelper;
import com.example.mini_bank.domain.models.User;
import com.example.mini_bank.domain.repositories.UserRepository;

public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        NullHelper.throwIfNull(userRepository, "userRepository");
        NullHelper.throwIfNull(passwordEncoder, "passwordEncoder");

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User login(String email, String password) {
        NullHelper.throwIfNull(email, "email");
        NullHelper.throwIfNull(password, password);
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NonExistentUser(email));
        if (passwordEncoder.matches(password, user.password())) {
            return user;
        }
        throw new NonExistentUser(email);
    }

    public User register(String email, String password) {
        NullHelper.throwIfNull(email, "email");
        NullHelper.throwIfNull(password, "password");
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyRegistered(email);
        }
        User user = new User(email, passwordEncoder.encode(password));
        return userRepository.save(user);
    }
}