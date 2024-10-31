package com.faniko.api_faniko.services;

import com.faniko.api_faniko.exceptions.NotFoundException;
import com.faniko.api_faniko.exceptions.UnauthorizedException;
import com.faniko.api_faniko.models.User;
import com.faniko.api_faniko.repositories.UserRepository;
import com.faniko.api_faniko.services.interfaces.ICrudService;
import com.faniko.api_faniko.utils.dto.auth.LoginDto;
import com.faniko.api_faniko.utils.dto.auth.RegisterDto;
import com.faniko.api_faniko.utils.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements ICrudService<User, UserRepository> {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public UserRepository getRepository() {
        return userRepository;
    }

    @Override
    public User formatEntityToUpdate(User entityToUpdate, User entity) {
        return entityToUpdate;
    }

    public UserDto register(RegisterDto registerDto) {
        User user = User.builder()
                .login(registerDto.getLogin())
                .password(passwordEncoder.encode(registerDto.getPassword()))
                .isActive(true)
                .roles(roleService.defaultRoles())
                .build();

        user = create(user);

        return UserDto.from(user);
    }

    public User authenticate(LoginDto loginDto) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginDto.getLogin(),
                            loginDto.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(e.getMessage());
        }

        return userRepository.findByLogin(loginDto.getLogin())
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}
