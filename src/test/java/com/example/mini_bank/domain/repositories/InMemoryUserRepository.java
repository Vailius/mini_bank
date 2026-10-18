package com.example.mini_bank.domain.repositories;
import java.util.Optional;
import com.example.mini_bank.domain.models.User;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryUserRepository implements UserRepository {
    private final ConcurrentHashMap<UUID, User> usersById;
    private final ConcurrentHashMap<String, User> usersByEmail;

    public InMemoryUserRepository() {
        usersById = new ConcurrentHashMap<UUID, User>();
        usersByEmail = new ConcurrentHashMap<String, User>();
    }

    public User save(User user) {
        usersById.put(user.id(), user);
        usersByEmail.put(user.email(), user);
        return user;
    }

    public Optional<User> findById(UUID id) {
        return Optional.ofNullable(usersById.get(id));
    }

    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(usersByEmail.get(email));
    }

    public boolean existsByEmail(String email) {
        return usersByEmail.containsKey(email);
    }
}