package org.example.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity {
    
    @Column(name = "full_name", nullable = false)
    private String fullName;
    
    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;
    
    @Column(name = "snils", unique = true)
    private String snils;
    
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    // Constructors
    public UserEntity() {}
    
    public UserEntity(String fullName, LocalDate dateOfBirth, String snils, String login, String password) {
        this.fullName = fullName;
        this.dateOfBirth = dateOfBirth;
        this.snils = snils;
        this.login = login;
        this.password = password;
    }
    
    // Getters and Setters
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getSnils() {
        return snils;
    }
    
    public void setSnils(String snils) {
        this.snils = snils;
    }
    
    public String getLogin() {
        return login;
    }
    
    public void setLogin(String login) {
        this.login = login;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
} 