package com.example.ecommercial.domain.dto.response;

import com.example.ecommercial.domain.enums.UserAuthority;
import com.example.ecommercial.domain.enums.UserRole;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserGetResponse {

    protected Long id;

    private String name;

    private String username;

    private String password;

    private List<UserRole> userRoles;

    private List<UserAuthority> userAuthorities;

    protected LocalDateTime createdDate;

    protected LocalDateTime updatedDate;
}
