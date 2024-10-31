package com.faniko.api_faniko.utils.dto.auth;

import com.faniko.api_faniko.utils.dto.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
public class LoginResponseDto {
    private String login;
    private Boolean isActive;
    private List<String> roles;
    private String token;
    private Long expirationTime;

    public static LoginResponseDto from(UserDto userDto, String token, Long expirationTime) {
        return LoginResponseDto.builder()
                .login(userDto.getLogin())
                .isActive(userDto.getIsActive())
                .roles(userDto.getRoles())
                .token(token)
                .expirationTime(expirationTime)
                .build();
    }
}
