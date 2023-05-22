package com.example.ecommercial.dao;

import com.example.ecommercial.domain.enums.UserAuthority;
import com.example.ecommercial.domain.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGetResponse {

    protected UUID id;

    private String name;

    private String username;

    private String password;

    private List<UserRole> userRoles;

    private List<UserAuthority> userAuthorities;

    protected LocalDateTime createdDate;

    protected LocalDateTime updatedDate;
}
