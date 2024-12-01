package org.bytecub.WedahamineBackend.rest.common;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.bytecub.WedahamineBackend.dto.common.*;
import org.bytecub.WedahamineBackend.dto.master.WHMUserDto;
import org.bytecub.WedahamineBackend.model.master.WHMUser;
import org.bytecub.WedahamineBackend.model.transaction.WHTVerificationToken;
import org.bytecub.WedahamineBackend.service.master.UserService;
import org.bytecub.WedahamineBackend.service.transaction.VerificationTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

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

    // verify email
    @GetMapping("/verify")
    public String verifyAccount(@RequestParam("token") String token) {
        log.info("Inside authentication controller verifyAccount method");
        return userService.updateVerifyStatus(token); // Save the updated user
    }

    // forgot password
    @PostMapping("/forgot-password")
    public ResponseEntity<ResponseDto> forgotPassword(@RequestParam String email) {
        log.info("Inside authentication controller forgotPassword method");
        return userService.forgotPassword(email);
    }

    // otp verification
    @PostMapping("/verify-otp")
    public ResponseEntity<ResponseDto> verifyOtp(@RequestBody OtpVerificationDto otpVerificationDto) {
        log.info("Inside authentication controller verifyOtp method");
        return userService.verifyOtp(otpVerificationDto);
    }

    // reset password
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseDto> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
        log.info("Inside authentication controller resetPassword method");
        return userService.resetPassword(resetPasswordDto);
    }

    // change password
    @PostMapping("/change-password")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody PasswordChangeDto passwordChangeDto) {
        log.info("Inside authentication controller changePassword method");
        return userService.changePassword(passwordChangeDto);
    }

}

