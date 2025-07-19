package com.authservice.controller;

import com.authservice.config.JwtProvider;
import com.authservice.model.AuthModel;
import com.authservice.repository.AuthRepository;
import com.authservice.services.AuthService;
import com.authservice.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthRepository authRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody AuthModel authModel){
        try {
            AuthModel isExist = authRepository.findByEmail(authModel.getEmail());

            if (isExist!=null) {
                throw new Exception("This email is already exist");
            }

            AuthModel newUser = new AuthModel();
            newUser.setUsername(authModel.getUsername());
            newUser.setEmail(authModel.getEmail());
            newUser.setPassword(passwordEncoder.encode(authModel.getPassword()));

            AuthModel savedUser = authRepository.save(newUser);

            Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
            String token = JwtProvider.generateToken(authentication);

            return new ResponseEntity<>(savedUser,HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> response = new HashMap<>();
            response.put("message",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthModel authModel) {
        try {
            Authentication authentication = authentication(authModel.getEmail(),authModel.getPassword());
            String token = JwtProvider.generateToken(authentication);

            return new ResponseEntity<>(token,HttpStatus.OK);
        } catch (Exception e){
            Map<String, String> response = new HashMap<>();
            response.put("message",e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    private Authentication authentication(String email, String password) {
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(email);

        if (userDetails==null){
            throw new BadCredentialsException("Invalid username");
        }

        if (!passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("Password doesn't match");
        }

        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
