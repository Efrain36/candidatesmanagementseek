package com.seek.candidates.infrastructure.adapters.persistence;

import com.seek.candidates.domain.dto.mapper.UserMapper;
import com.seek.candidates.domain.model.User;
import com.seek.candidates.domain.repository.IUserRepository;
import com.seek.candidates.infrastructure.adapters.persistence.entity.UserEntity;
import com.seek.candidates.infrastructure.adapters.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
public class UserPersistenceAdapter implements IUserRepository {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserPersistenceAdapter(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public User save(User user) {
        UserEntity userEntity = userMapper.toEntity(user);
        return userMapper.toDomain(userRepository.save(userEntity));
    }

    @Override
    public boolean existsByUsername(String username){
        return userRepository.existsByUsername(username);
    }

}
