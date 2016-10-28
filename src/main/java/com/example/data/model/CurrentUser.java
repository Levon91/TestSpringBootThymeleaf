package com.example.data.model;

import com.example.enumeration.Role;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

    private User user;

    public CurrentUser(User user) {
        super(user.getEmail(), user.getPassword(),
                user.getRole() == Role.ADMIN
                        ? Collections.singletonList(new SimpleGrantedAuthority(Role.ADMIN.name()))
                        : Collections.singletonList(new SimpleGrantedAuthority(Role.USER.name()))
        );
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return user.getId();
    }

    public String getFullName() {
        return user.getFullName();
    }

}