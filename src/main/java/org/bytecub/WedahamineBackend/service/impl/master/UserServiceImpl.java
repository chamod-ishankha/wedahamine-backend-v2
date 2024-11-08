package org.bytecub.WedahamineBackend.service.impl.master;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.WedahamineBackend.dao.master.WHMUserDao;
import org.bytecub.WedahamineBackend.dto.common.*;
import org.bytecub.WedahamineBackend.dto.master.WHMUserDto;
import org.bytecub.WedahamineBackend.error.BadRequestAlertException;
import org.bytecub.WedahamineBackend.mappers.master.WHMUserMapper;
import org.bytecub.WedahamineBackend.model.master.WHMUser;
import org.bytecub.WedahamineBackend.service.master.UserService;
import org.bytecub.WedahamineBackend.utils.JWTUtils;
import org.bytecub.WedahamineBackend.utils.Services.EmailService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.bytecub.WedahamineBackend.constants.Common.TOKEN_TYPE;
import static org.bytecub.WedahamineBackend.constants.EmailTemplates.USER_CREATION_EMAIL_CONTENT;
import static org.bytecub.WedahamineBackend.constants.EmailTemplates.USER_CREATION_EMAIL_SUBJECT;
import static org.bytecub.WedahamineBackend.utils.JWTUtils.EXPIRATION_TIME;
import static org.bytecub.WedahamineBackend.utils.Utils.uniqKeyGenerator;
import static org.hibernate.cfg.JdbcSettings.USER;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final WHMUserDao userDao;
    private final WedahamineUserDetailsService userDetailsService;
    private final JWTUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final WHMUserMapper userMapper;
    private final EmailService emailService;

    public UserServiceImpl(WHMUserDao userDao, WedahamineUserDetailsService userDetailsService, JWTUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, WHMUserMapper userMapper, EmailService emailService) {
        this.userDao = userDao;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.emailService = emailService;
    }


    @Override
    public AuthenticationDto login(LoginRequest loginRequest) {
        log.info("Inside user service login method");
        AuthenticationDto authenticationDto = new AuthenticationDto();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            WHMUser user = userDao.findByEmail(loginRequest.getEmail()).orElseThrow();
            String jwt = jwtUtils.generateToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            authenticationDto.setUserId(user.getUserId());
            authenticationDto.setFirstName(user.getFirstName());
            authenticationDto.setLastName(user.getLastName());
            authenticationDto.setEmail(user.getEmail());
            authenticationDto.setPhone(user.getPhone());
            authenticationDto.setRole(user.getRole());
            authenticationDto.setStatus(user.getStatus());
            authenticationDto.setUniqKey(user.getUniqKey());

            TokenDto tokenDto = new TokenDto();
            tokenDto.setTokenType(TOKEN_TYPE);
            tokenDto.setToken(jwt);
            tokenDto.setRefreshToken(refreshToken);
            tokenDto.setExpiresIn(EXPIRATION_TIME);

            authenticationDto.setTokenDto(tokenDto);

            return authenticationDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "User login failed");
        }
    }

    @Override
    public WHMUserDto register(RegisterRequest registerRequest) {
        log.info("Inside user service register method");
        WHMUserDto userDto = new WHMUserDto();
        try {
            // validation
            if (userDao.existsByEmail(registerRequest.getEmail())) {
                throw new BadRequestAlertException("Email already exists", "User", "User registration failed");
            }

            userDto.setFirstName(registerRequest.getFirstName());
            userDto.setLastName(registerRequest.getLastName());
            userDto.setEmail(registerRequest.getEmail());
            userDto.setPhone(registerRequest.getPhone());
            userDto.setRole(USER);
            userDto.setStatus("NEW");
            userDto.setUniqKey(uniqKeyGenerator());

            WHMUser user = userMapper.toEntity(userDto);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

            userDto = userMapper.toDto(userDao.save(user));

            // send verification & registration email
            String content = USER_CREATION_EMAIL_CONTENT;
            content = content.replace("{{firstName}}", userDto.getFirstName());
            content = content.replace("{{username}}", userDto.getEmail());
            content = content.replace("{{userId}}", userDto.getUniqKey());

            emailService.sendEmail(userDto.getEmail(), USER_CREATION_EMAIL_SUBJECT, content);

            return userDto;
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "User registration failed");
        }
    }

    @Override
    public WHMUserDto getUserById(Long userId) {
        log.info("Inside user service get user by id method");
        try {
            Optional<WHMUser> userOp = userDao.findById(userId);
            if (userOp.isEmpty()) {
                throw new BadRequestAlertException("User not found", "User", "User not found");
            } else {
                return userMapper.toDto(userOp.get());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public List<WHMUserDto> getAllUsers() {
        log.info("Inside user service get all users method");
        try {
            List<WHMUser> users = userDao.findAll();
            return userMapper.listToDto(users);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public WHMUserDto updateUser(Long userId, WHMUserDto userDto) {
        log.info("Inside user service update user method");
        try {
            Optional<WHMUser> userOp = userDao.findById(userId);
            if (userDto.getUserId() == null) {
                throw new BadRequestAlertException("User id is required", "User", "User update failed");
            } else if (!userId.equals(userDto.getUserId())) {
                throw new BadRequestAlertException("User id mismatch", "User", "User update failed");
            } else if (userOp.isEmpty()) {
                throw new BadRequestAlertException("User not found", "User", "User not found");
            } else if (userDto.getPhone() == null || userDto.getPhone().isEmpty()) {
                throw new BadRequestAlertException("Phone number is required", "User", "User update failed");
            } else if (userDto.getFirstName() == null || userDto.getFirstName().isEmpty()) {
                throw new BadRequestAlertException("First name is required", "User", "User update failed");
            } else if (userDto.getLastName() == null || userDto.getLastName().isEmpty()) {
                throw new BadRequestAlertException("Last name is required", "User", "User update failed");
            } else {
                WHMUser user = userOp.get();
                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setPhone(userDto.getPhone());
                user.setDob(userDto.getDob());
                return userMapper.toDto(userDao.save(user));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public ResponseDto deleteUser(Long userId) {
        log.info("Inside user service delete user method");
        try {
            Optional<WHMUser> userOp = userDao.findById(userId);
            if (userOp.isEmpty()) {
                throw new BadRequestAlertException("User not found", "User", "User not found");
            } else {
                userDao.deleteById(userId);
                ResponseDto responseDto = new ResponseDto();
                responseDto.setId(userId);
                responseDto.setMessage("User deleted successfully");
                return responseDto;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }
}
