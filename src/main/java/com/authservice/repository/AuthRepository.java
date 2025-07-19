package com.authservice.repository;

import com.authservice.model.AuthModel;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AuthRepository extends JpaRepository<AuthModel, Long> {
    public AuthModel findByEmail(String email);
}
