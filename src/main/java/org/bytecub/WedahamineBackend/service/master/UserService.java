package org.bytecub.WedahamineBackend.service.master;

import org.bytecub.WedahamineBackend.dto.common.*;
import org.bytecub.WedahamineBackend.dto.master.WHMUserDto;
import org.bytecub.WedahamineBackend.model.master.WHMUser;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    AuthenticationDto login(LoginRequest loginRequest);

    WHMUserDto register(RegisterRequest registerRequest);

    WHMUserDto getUserById(Long userId);

    List<WHMUserDto> getAllUsers();

    WHMUserDto updateUser(Long userId, WHMUserDto userDto);

    ResponseDto deleteUser(Long userId);

    String updateVerifyStatus(String token);

    ResponseEntity<ResponseDto> forgotPassword(String email);

    ResponseEntity<ResponseDto> verifyOtp(OtpVerificationDto otpVerificationDto);

    ResponseEntity<ResponseDto> resetPassword(ResetPasswordDto resetPasswordDto);

    ResponseEntity<ResponseDto> changePassword(PasswordChangeDto passwordChangeDto);
}
