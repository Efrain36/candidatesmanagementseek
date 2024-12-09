package com.seek.candidates.domain.repository;

import com.seek.candidates.domain.model.User;

import java.util.Optional;

public interface IUserRepository {

    User save(User user);
    boolean existsByUsername(String username);

}
