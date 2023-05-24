package com.example.ecommercial.domain.dto.request;

import com.example.ecommercial.domain.enums.UserAuthority;
import com.example.ecommercial.domain.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreateRequest {
    @Pattern(regexp = "[a-zA-Z0-9]{4,20}$",
            message = "name length should be at least 4 max 20 characters " +
                    "and it should only satisfy [a-zA-Z0-9] pattern")
    private String name;
    @Column(unique = true)
    @Pattern(regexp = "^[a-zA-Z0-9]{5,20}$",
            message = "username length should be at least 5 max 20 characters " +
                    "and it should only satisfy [a-zA-Z0-9] pattern")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,20}$",
            message = "password length should be at least 8 max 20 characters " +
                    "and it should only satisfy [a-zA-Z0-9] pattern")
    private String password;
    private List<UserRole> userRoles;
    private List<UserAuthority> userAuthorities;
}
