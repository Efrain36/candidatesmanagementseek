package com.seek.candidates.domain.dto.mapper;

import com.seek.candidates.domain.dto.UserDTO;
import com.seek.candidates.domain.model.User;
import com.seek.candidates.infrastructure.adapters.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(User user);
    User toDomain(UserDTO userDTO);
    UserEntity toEntity(User user);
    User toDomain(UserEntity userEntity);

}
