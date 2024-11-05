package com.faniko.api_faniko.utils.dto.auth;

import com.faniko.api_faniko.utils.dto.user.UserDto;
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
public class LoginResponseDto {
    private String login;
    private Boolean isActive;
    private List<String> roles;
    private String token;
    private String refreshToken;
    private Long expirationTime;

    public static LoginResponseDto from(UserDto userDto, String token, String refreshToken, Long expirationTime) {
        return LoginResponseDto.builder()
                .login(userDto.getLogin())
                .isActive(userDto.getIsActive())
                .roles(userDto.getRoles())
                .token(token)
                .refreshToken(refreshToken)
                .expirationTime(expirationTime)
                .build();
    }
}
