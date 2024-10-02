package com.namta.ecom.services.auth;

import com.namta.ecom.dto.SignupRequest;
import com.namta.ecom.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    boolean hasUserWithEmail(String email);
}
