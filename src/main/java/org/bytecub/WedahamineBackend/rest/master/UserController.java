package org.bytecub.WedahamineBackend.rest.master;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.WedahamineBackend.dto.common.ResponseDto;
import org.bytecub.WedahamineBackend.dto.master.WHMUserDto;
import org.bytecub.WedahamineBackend.service.master.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // get user by id
    @GetMapping("/{userId}")
    public ResponseEntity<WHMUserDto> getUserById(@PathVariable Long userId) {
        log.info("Inside user controller get user by id method");
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    // get all users
    @GetMapping("/all")
    public ResponseEntity<List<WHMUserDto>> getAllUsers() {
        log.info("Inside user controller get all users method");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    // update user
    @PutMapping("/{userId}")
    public ResponseEntity<WHMUserDto> updateUser(@PathVariable Long userId, @RequestBody WHMUserDto userDto) {
        log.info("Inside user controller update user method");
        return new ResponseEntity<>(userService.updateUser(userId, userDto), HttpStatus.OK);
    }

    // delete user
    @DeleteMapping("/{userId}")
    public ResponseEntity<ResponseDto> deleteUser(@PathVariable Long userId) {
        log.info("Inside user controller delete user method");
        return new ResponseEntity<>(userService.deleteUser(userId), HttpStatus.OK);
    }
}
