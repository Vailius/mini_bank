package com.example.mini_bank.domain.repositories;
import com.example.mini_bank.domain.services.AuthService;
import com.example.mini_bank.domain.PasswordEncoderMock;
import com.example.mini_bank.domain.repositories.InMemoryUserRepository;
import com.example.mini_bank.domain.exceptions.EmailAlreadyRegistered;
import com.example.mini_bank.domain.exceptions.NonExistentUserWithEmail;
import com.example.mini_bank.domain.models.User;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AuthServiceTest {

    private AuthService authService;
    private PasswordEncoderMock passwordEncoder;
    private final String testMail = "inavov.ratniy@olga.org";
    private final String testPassword = "inan'ka2009--no-daemon";

    @BeforeEach
    void prepare_test() {
        passwordEncoder = new PasswordEncoderMock();
        authService = new AuthService(new InMemoryUserRepository(), passwordEncoder);
        authService.register(testMail, testPassword);
        passwordEncoder.clear();
    }

    @Test
    void test_login() {
        passwordEncoder.clear();
        User ivan = authService.login(testMail, testPassword);
        assertEquals(testMail, ivan.email());
        assertEquals(0, passwordEncoder.encodeInvokes());
        assertEquals(1, passwordEncoder.matchInvokes());
        assertEquals(passwordEncoder.encode(testPassword), ivan.password());
    }

    @Test
    void test_non_existent_user() {
        assertThrows(NonExistentUserWithEmail.class, () -> authService.login("petrov228@mail.ru", "bandit16-2022"));
    }

    @Test
    void test_not_true_password() {
        assertThrows(NonExistentUserWithEmail.class, () -> authService.login(testMail, "aura+++"));
    }

    @Test
    void test_already_registered_register() {
        assertThrows(EmailAlreadyRegistered.class, () -> authService.register(testMail, testPassword));
    }
}