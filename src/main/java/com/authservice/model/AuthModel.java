package com.authservice.model;

import jakarta.persistence.*;
import lombok.Data;
//import org.springframework.data.annotation.Id;

//MongoDB
//import org.springframework.data.mongodb.core.mapping.Document;

@Data

// MongoDB
//@Document(collection = "users")

// SQL
@Entity
@Table(name = "auth_model")
public class AuthModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String email;
    private String password;

    public AuthModel() {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
