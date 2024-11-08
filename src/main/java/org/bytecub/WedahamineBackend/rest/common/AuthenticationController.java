package org.bytecub.WedahamineBackend.rest.common;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bytecub.WedahamineBackend.dto.common.AuthenticationDto;
import org.bytecub.WedahamineBackend.dto.common.LoginRequest;
import org.bytecub.WedahamineBackend.dto.common.RegisterRequest;
import org.bytecub.WedahamineBackend.dto.master.WHMUserDto;
import org.bytecub.WedahamineBackend.service.master.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    // login
    @PostMapping("/login")
    public ResponseEntity<AuthenticationDto> login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("Inside authentication controller login method");
        return new ResponseEntity<>(userService.login(loginRequest), HttpStatus.OK);
    }

    // register
    @PostMapping("/register")
    public ResponseEntity<WHMUserDto> register(@RequestBody @Valid RegisterRequest registerRequest) {
        log.info("Inside authentication controller register method");
        return new ResponseEntity<>(userService.register(registerRequest), HttpStatus.CREATED);
    }

    // forgot password

    // change password

}

