package com.faniko.api_faniko.models;

import com.faniko.api_faniko.models.base.BaseEntity;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "users")
public class User extends BaseEntity implements UserDetails {
    @Field(name = "login", targetType = FieldType.STRING)
    @Indexed(unique = true)
    private String login;

    @Field(name = "password", targetType = FieldType.STRING)
    private String password;

    @Field(value = "roles", targetType = FieldType.ARRAY)
    private List<String> roles;

    @Field(name = "is_active", targetType = FieldType.BOOLEAN)
    private Boolean isActive;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(role -> (GrantedAuthority) () -> role).toList();
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Boolean.TRUE;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
