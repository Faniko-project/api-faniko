package com.faniko.api_faniko.rest.controllers;

import com.faniko.api_faniko.models.User;
import com.faniko.api_faniko.services.UserService;
import com.faniko.api_faniko.services.jwt.JwtService;
import com.faniko.api_faniko.services.jwt.RefreshTokenService;
import com.faniko.api_faniko.utils.dto.auth.LoginDto;
import com.faniko.api_faniko.utils.dto.auth.LoginResponseDto;
import com.faniko.api_faniko.utils.dto.auth.RegisterDto;
import com.faniko.api_faniko.utils.dto.user.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.faniko.api_faniko.utils.constants.AppConstants.API_BASE_URL_V1;

@RestController
@RequestMapping(API_BASE_URL_V1 + "/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RefreshTokenService refreshTokenService;

    @Operation(summary = "Register a new user")
    @PostMapping(value = "/signup", consumes = {"application/json;charset=UTF-8"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterDto registerDto) {
        return ResponseEntity.ok(userService.register(registerDto));
    }

    @Operation(summary = "Authenticate a user")
    @PostMapping(value = "/login", consumes = {"application/json;charset=UTF-8"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody @Valid LoginDto loginDto) {
        User user = userService.authenticate(loginDto);

        String jwtToken = jwtService.generateToken(user);
        String jwtRefreshToken = refreshTokenService.generateRefreshToken(user);

        LoginResponseDto loginResponseDto = LoginResponseDto.from(
                UserDto.from(user),
                jwtToken,
                jwtRefreshToken,
                jwtService.getExpirationTime()
        );

        return ResponseEntity
                .ok()
                .header("Authorization", String.format("Bearer %s", jwtToken))
                .body(loginResponseDto);
    }

    @Operation(summary = "Refresh a user's token")
    @GetMapping(value = "/refresh/{token}", produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<LoginResponseDto> refresh(@PathVariable String token) {
        User user = refreshTokenService.getUserFromRefreshToken(token);

        String jwtToken = jwtService.generateToken(user);
        String jwtRefreshToken = refreshTokenService.generateRefreshToken(user);

        LoginResponseDto loginResponseDto = LoginResponseDto.from(
                UserDto.from(user),
                jwtToken,
                jwtRefreshToken,
                jwtService.getExpirationTime()
        );

        return ResponseEntity
                .ok()
                .header("Authorization", String.format("Bearer %s", jwtToken))
                .body(loginResponseDto);
    }
}
