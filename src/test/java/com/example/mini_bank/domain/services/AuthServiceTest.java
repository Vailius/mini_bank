package com.example.mini_bank.domain.services;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.example.mini_bank.domain.PasswordEncoderMock;
import com.example.mini_bank.domain.exceptions.EmailAlreadyRegistered;
import com.example.mini_bank.domain.exceptions.NonExistentUser;
import com.example.mini_bank.domain.models.User;
import com.example.mini_bank.domain.repositories.InMemoryUserRepository;

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
    void test_create_with_null() {
        InMemoryUserRepository urepo = new InMemoryUserRepository();
        assertThrows(IllegalArgumentException.class, () -> new AuthService(null, null));
        assertThrows(IllegalArgumentException.class, () -> new AuthService(urepo, null));
        assertThrows(IllegalArgumentException.class, () -> new AuthService(null, passwordEncoder));
    }

    @Test
    void test_login_null() {
        assertThrows(IllegalArgumentException.class, () -> authService.login(null, null));
        assertThrows(IllegalArgumentException.class, () -> authService.login(null, "some-password"));
        assertThrows(IllegalArgumentException.class, () -> authService.login("sobaka@mail.col", null));
    }

    @Test
    void test_register_null() {
        assertThrows(IllegalArgumentException.class, () -> authService.register(null, null));
        assertThrows(IllegalArgumentException.class, () -> authService.register(null, "some-password"));
        assertThrows(IllegalArgumentException.class, () -> authService.register("sobaka@mail.com", null));
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
        assertThrows(NonExistentUser.class, () -> authService.login("petrov228@mail.ru", "bandit16-2022"));
    }

    @Test
    void test_not_true_password() {
        assertThrows(NonExistentUser.class, () -> authService.login(testMail, "aura+++"));
    }

    @Test
    void test_already_registered_register() {
        assertThrows(EmailAlreadyRegistered.class, () -> authService.register(testMail, testPassword));
    }
}