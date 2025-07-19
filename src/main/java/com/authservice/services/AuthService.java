package com.authservice.services;

import com.authservice.model.AuthModel;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {
    AuthModel findUserByEmail(String email);
    AuthModel signup(AuthModel authModel);
}
