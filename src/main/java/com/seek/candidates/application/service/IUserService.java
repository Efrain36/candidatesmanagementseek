package com.seek.candidates.application.service;

import com.seek.candidates.domain.dto.UserDTO;
import com.seek.candidates.domain.dto.request.RegisterRequest;

public interface IUserService {

    UserDTO saveUser(RegisterRequest registerRequest);

}
