package com.example.ecommercial.dto.request;

import com.example.ecommercial.domain.enums.UserAuthority;
import com.example.ecommercial.domain.enums.UserRole;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreatePostRequest {
    private String name;
    @Column(unique = true)
    private String username;
    private String password;
    private List<UserRole> roles;
    private List<UserAuthority> authorities;

}
