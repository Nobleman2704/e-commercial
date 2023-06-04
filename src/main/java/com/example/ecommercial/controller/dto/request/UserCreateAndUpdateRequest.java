package com.example.ecommercial.controller.dto.request;

import com.example.ecommercial.domain.enums.UserAuthority;
import com.example.ecommercial.domain.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCreateAndUpdateRequest {

    private Long id;

    @Pattern(regexp = "^[a-zA-Z]{4,15}$",
            message = "name length should be at least 4 max 20 characters " +
                    "and it should only contains letters")
    private String name;
    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9]{5,15}$",
            message = "username length should be at least 5 max 15 characters " +
                    "and it should only satisfy [a-zA-Z0-9] pattern")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$",
            message = "password length should be at least 8 max 20 characters " +
                    "and it should only satisfy [a-zA-Z0-9] pattern")
    private String password;
    private List<UserRole> userRoles;
    private List<UserAuthority> userAuthorities;
}
