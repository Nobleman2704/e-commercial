package com.example.ecommercial.controller.dto.response;

import com.example.ecommercial.domain.enums.UserAuthority;
import com.example.ecommercial.domain.enums.UserRole;
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
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
    private String lastModifiedBy;
    private String name;
    private String username;
    private String password;
    private double balance;
    private Long chatId;
    private List<UserRole> userRoles;
    private List<UserAuthority> userAuthorities;
}
