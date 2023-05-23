package com.example.ecommercial.domain.entity;

import com.example.ecommercial.domain.enums.UserAuthority;
import com.example.ecommercial.domain.enums.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Builder
public class UserEntity extends BaseEntity implements UserDetails {
    private String name;

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private List<UserRole> userRoles;

    @Enumerated(EnumType.STRING)

    private List<UserAuthority> userAuthorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> userRoleAuthorities = new LinkedList<>();
        userRoleAuthorities.addAll(userRoles
                .stream()
                .map(userRole -> new SimpleGrantedAuthority("ROLE_" + userRole.name()))
                .toList());
        if (userAuthorities != null) {
            userRoleAuthorities.addAll(
                    userAuthorities

                            .stream()
                            .map(userAuthority -> new SimpleGrantedAuthority(userAuthority.name()))
                            .toList()
            );
        }
        return userRoleAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
