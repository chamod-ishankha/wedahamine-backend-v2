package org.bytecub.WedahamineBackend.service.master;

import org.bytecub.WedahamineBackend.dto.common.AuthenticationDto;
import org.bytecub.WedahamineBackend.dto.common.LoginRequest;
import org.bytecub.WedahamineBackend.dto.common.RegisterRequest;
import org.bytecub.WedahamineBackend.dto.common.ResponseDto;
import org.bytecub.WedahamineBackend.dto.master.WHMUserDto;

import java.util.List;

public interface UserService {
    AuthenticationDto login(LoginRequest loginRequest);

    WHMUserDto register(RegisterRequest registerRequest);

    WHMUserDto getUserById(Long userId);

    List<WHMUserDto> getAllUsers();

    WHMUserDto updateUser(Long userId, WHMUserDto userDto);

    ResponseDto deleteUser(Long userId);

//    ResponseDto uploadImage(Long userId, MultipartFile file);
//
//    ResponseEntity<byte[]> getImage(Long userId);
}
