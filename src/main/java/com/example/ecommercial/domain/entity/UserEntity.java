package com.example.ecommercial.domain.entity;

import com.example.ecommercial.domain.enums.UserAuthority;
import com.example.ecommercial.domain.enums.UserRole;
import com.example.ecommercial.domain.enums.UserState;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Builder
public class UserEntity extends BaseEntity implements UserDetails {
    private String name;

    @Override
    public String toString() {
        return "UserEntity{" +
                "name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                '}';
    }

    @Column(unique = true)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserState userState;

    private Long chatId;

    private double balance;

    @Enumerated(EnumType.STRING)
    private List<UserRole> userRoles;

    @Enumerated(EnumType.STRING)
    private List<UserAuthority> userAuthorities;

    @OneToMany(mappedBy = "users")
    private List<OrderEntity> orderEntities;

    @OneToMany(mappedBy = "users")
    private List<HistoryEntity> historyEntities;

    @OneToMany(mappedBy = "users")
    private List<BasketEntity> basketEntities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> userRoleAuthorities = new LinkedList<>(userRoles
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
