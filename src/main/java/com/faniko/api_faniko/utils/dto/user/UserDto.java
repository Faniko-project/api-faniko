package com.faniko.api_faniko.utils.dto.user;

import com.faniko.api_faniko.models.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
    private String login;
    private Boolean isActive;
    private List<String> roles;

    public static UserDto from(User user) {
        return UserDto.builder()
                .login(user.getLogin())
                .isActive(user.getIsActive())
                .roles(user.getRoles())
                .build();
    }
}
