package com.shifaa.Password.Manager.model;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
public class PasswordEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String siteName;
    private String username;
    private String password;

    private LocalDateTime createdAt;

    // 🔧 Default constructor (required by JPA)
    public PasswordEntry() {
        this.createdAt = LocalDateTime.now();
    }
    // 🔧 Custom constructor
    public PasswordEntry(String siteName, String username, String password) {
        this.siteName = siteName;
        this.username = username;
        this.password = password;
        this.createdAt = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    // 🔄 Getters and Setters

    public Long getId() {
        return id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

