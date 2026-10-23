package com.example.mini_bank.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import com.example.mini_bank.domain.models.User;
import com.example.mini_bank.domain.repositories.UserRepository;
import com.example.mini_bank.jpa.entities.UserEntity;

public class UserRepositoryBinding implements UserRepository {

    private final JpaUserRepository jpaUserRepository;

    public UserRepositoryBinding(JpaUserRepository jpaUserRepository) {
        this.jpaUserRepository = jpaUserRepository;
    }

    private static Optional<User> optionalToDomain(Optional<UserEntity> entity) {
        if (entity.isPresent()) {
            return Optional.of(entity.get().user());
        }
        return Optional.ofNullable((User)null);
    }

    @Override
    public User save(User user) {
        UserEntity entity = jpaUserRepository.save(new UserEntity(0, user));
        if (entity == null) {
            return null;
        }
        return entity.user();
    }

    @Override
    public Optional<User> findById(UUID id) {
        return optionalToDomain(jpaUserRepository.findById(id));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return optionalToDomain(jpaUserRepository.findByEmail(email));
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }
}