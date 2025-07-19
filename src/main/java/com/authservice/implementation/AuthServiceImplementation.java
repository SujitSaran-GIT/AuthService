package com.authservice.implementation;

import com.authservice.model.AuthModel;
import com.authservice.repository.AuthRepository;
import com.authservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImplementation implements AuthService {

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public AuthModel findUserByEmail(String email) {
        return authRepository.findByEmail(email);
    }

    @Override
    public AuthModel signup(AuthModel authModel) {
//        Check if the user exists
        AuthModel newAuth = new AuthModel();
        newAuth.setUsername(authModel.getUsername());
        newAuth.setEmail(authModel.getEmail());
        newAuth.setPassword(passwordEncoder.encode(authModel.getPassword()));

        AuthModel savedUser = authRepository.save(newAuth);
        return savedUser;
    }

}
