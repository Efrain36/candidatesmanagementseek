package com.seek.candidates.application.service.impl;

import com.seek.candidates.application.service.IUserService;
import com.seek.candidates.domain.dto.UserDTO;
import com.seek.candidates.domain.dto.mapper.UserMapper;
import com.seek.candidates.domain.dto.request.RegisterRequest;
import com.seek.candidates.domain.exception.BadRequestException;
import com.seek.candidates.domain.model.User;
import com.seek.candidates.infrastructure.adapters.persistence.UserPersistenceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    private final UserPersistenceAdapter userPersistenceAdapter;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserPersistenceAdapter userPersistenceAdapter, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userPersistenceAdapter = userPersistenceAdapter;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO saveUser(RegisterRequest registerRequest) {
        if (userPersistenceAdapter.existsByUsername(registerRequest.getUsername())) {
            throw new BadRequestException("Username already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setName(registerRequest.getName());
        user.setEnabled(true);

        return userMapper.toDTO(userPersistenceAdapter.save(user));
    }

}
