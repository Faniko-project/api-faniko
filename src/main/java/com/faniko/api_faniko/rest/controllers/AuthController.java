package com.faniko.api_faniko.rest.controllers;

import com.faniko.api_faniko.models.User;
import com.faniko.api_faniko.services.UserService;
import com.faniko.api_faniko.services.jwt.JwtService;
import com.faniko.api_faniko.utils.dto.auth.LoginDto;
import com.faniko.api_faniko.utils.dto.auth.LoginResponseDto;
import com.faniko.api_faniko.utils.dto.auth.RegisterDto;
import com.faniko.api_faniko.utils.dto.user.UserDto;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.faniko.api_faniko.utils.constants.AppConstant.API_BASE_URL_V1;

@RestController
@RequestMapping(API_BASE_URL_V1 + "/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping(value = "/signup", consumes = {"application/json;charset=UTF-8"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterDto registerDto) {
        return ResponseEntity.ok(userService.register(registerDto));
    }

    @PostMapping(value = "/login", consumes = {"application/json;charset=UTF-8"}, produces = {"application/json;charset=UTF-8"})
    public ResponseEntity<LoginResponseDto> authenticate(@RequestBody @Valid LoginDto loginDto) {
        User user = userService.authenticate(loginDto);

        String jwtToken = jwtService.generateToken(user);

        LoginResponseDto loginResponseDto = LoginResponseDto.from(
                UserDto.from(user),
                jwtToken,
                jwtService.getExpirationTime()
        );

        return ResponseEntity
                .ok()
                .header("Authorization", String.format("Bearer %s", jwtToken))
                .body(loginResponseDto);
    }
}
