package org.bytecub.WedahamineBackend.service.impl.master;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.WedahamineBackend.dao.master.WHMUserDao;
import org.bytecub.WedahamineBackend.dto.common.*;
import org.bytecub.WedahamineBackend.dto.master.WHMUserDto;
import org.bytecub.WedahamineBackend.error.BadRequestAlertException;
import org.bytecub.WedahamineBackend.mappers.master.WHMUserMapper;
import org.bytecub.WedahamineBackend.mappers.reference.WHRStatusMapper;
import org.bytecub.WedahamineBackend.model.master.WHMUser;
import org.bytecub.WedahamineBackend.model.transaction.WHTVerificationToken;
import org.bytecub.WedahamineBackend.service.master.UserService;
import org.bytecub.WedahamineBackend.service.transaction.VerificationTokenService;
import org.bytecub.WedahamineBackend.utils.JWTUtils;
import org.bytecub.WedahamineBackend.utils.Services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.bytecub.WedahamineBackend.constants.Common.TOKEN_TYPE;
import static org.bytecub.WedahamineBackend.constants.EmailTemplates.*;
import static org.bytecub.WedahamineBackend.constants.SystemStatus.NEW;
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
    private final WHRStatusMapper statusMapper;
    private final VerificationTokenService verificationTokenService;

    @Value("${app.base-url}")
    private String appBaseUrl;

    public UserServiceImpl(WHMUserDao userDao, WedahamineUserDetailsService userDetailsService, JWTUtils jwtUtils, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, WHMUserMapper userMapper, EmailService emailService, WHRStatusMapper statusMapper, VerificationTokenService verificationTokenService) {
        this.userDao = userDao;
        this.userDetailsService = userDetailsService;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.userMapper = userMapper;
        this.emailService = emailService;
        this.statusMapper = statusMapper;
        this.verificationTokenService = verificationTokenService;
    }


    @Override
    public AuthenticationDto login(LoginRequest loginRequest) {
        log.info("Inside user service login method");
        AuthenticationDto authenticationDto = new AuthenticationDto();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            WHMUser user = userDao.findByEmail(loginRequest.getEmail()).orElseThrow();
            if (!user.getIsVerified()) {
                log.error("User is not verified! Please follow verification link sent to your email");

                // create email verification link
                String newVerificationLink = appBaseUrl + "/auth/verify?token=" + verificationTokenService.createToken(user).getVerificationToken();
                // send verification & registration email
                String content = EXPIRED_LINK_EMAIL_CONTENT;
                content = content.replace("{{firstName}}", user.getFirstName());
                content = content.replace("{{newVerificationLink}}", newVerificationLink);

                emailService.sendEmail(user.getEmail(), EXPIRED_LINK_EMAIL_SUBJECT, content);

                throw new BadRequestAlertException("User is not verified! Please follow verification link sent to your email", "User", "User login failed");
            }
            String jwt = jwtUtils.generateToken(user);
            String refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);

            authenticationDto.setUserId(user.getUserId());
            authenticationDto.setFirstName(user.getFirstName());
            authenticationDto.setLastName(user.getLastName());
            authenticationDto.setEmail(user.getEmail());
            authenticationDto.setPhone(user.getPhone());
            authenticationDto.setRole(user.getRole());
            authenticationDto.setWhrStatus(statusMapper.toDto(user.getWhrStatus()));
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
            userDto.setStatusId(NEW);
            userDto.setUniqKey(uniqKeyGenerator());
            userDto.setIsActive(true);
            userDto.setIsVerified(false);

            WHMUser user = userMapper.toEntity(userDto);
            user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

            userDto = userMapper.toDto(userDao.save(user));

            // create email verification link
            String verificationLink = appBaseUrl + "/auth/verify?token=" + verificationTokenService.createToken(user).getVerificationToken();
            // send verification & registration email
            String content = USER_CREATION_EMAIL_CONTENT;
            content = content.replace("{{firstName}}", userDto.getFirstName());
            content = content.replace("{{username}}", userDto.getEmail());
            content = content.replace("{{userId}}", userDto.getUniqKey());
            content = content.replace("{{verificationLink}}", verificationLink);

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

    @Override
    public String updateVerifyStatus(String token) {
        log.info("Inside user service update verify status method");
        try {
            Optional<WHTVerificationToken> verificationToken = verificationTokenService.getToken(token);

            if (verificationToken.isEmpty() || verificationToken.get().getExpiryDate().isBefore(LocalDateTime.now())) {
                log.error("Token is invalid or expired!");
                return "Token is invalid or expired!";
            }

            WHMUser user = verificationToken.get().getWhmUser();
            user.setIsVerified(true); // Set a flag in User entity to mark as verified
            userDao.save(user);
            log.info("Account verified successfully!");
            return "Account verified successfully!";
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public ResponseEntity<ResponseDto> forgotPassword(String email) {
        log.info("Inside user service forgot password method");
        try {
            // validation
            if (!userDao.existsByEmail(email)) {
                log.error("Email not registered");
                throw new BadRequestAlertException("Email not registered", "User", "User not found");
            }
            // generate OTP
            String otp = verificationTokenService.generateOTP();
            verificationTokenService.saveOtp(otp, userDao.findByEmail(email).get().getUserId());
            // send verification & registration email
            String content = PASSWORD_RESET_EMAIL_CONTENT;
            content = content.replace("{{otp}}", otp);

            emailService.sendEmail(email, PASSWORD_RESET_EMAIL_SUBJECT, content);

            ResponseDto response = new ResponseDto();
            response.setMessage("OTP sent to your email");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public ResponseEntity<ResponseDto> verifyOtp(OtpVerificationDto otpVerificationDto) {
        log.info("Inside user service verify otp method");
        try {
            // validate
            if (otpVerificationDto.getEmail() == null || otpVerificationDto.getEmail().isEmpty()) {
                log.error("Email is required");
                throw new BadRequestAlertException("Email is required", "User", "error");
            }
            if (otpVerificationDto.getOtp() == null || otpVerificationDto.getOtp().isEmpty()) {
                log.error("OTP is required");
                throw new BadRequestAlertException("OTP is required", "User", "error");
            }

            if (verificationTokenService.validateOtp(otpVerificationDto.getEmail(), otpVerificationDto.getOtp())) {
                ResponseDto response = new ResponseDto();
                response.setMessage("OTP verified. You can now reset your password.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                log.error("Invalid or expired OTP");
                throw new BadRequestAlertException("Invalid or expired OTP", "User", "error");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public ResponseEntity<ResponseDto> resetPassword(ResetPasswordDto resetPasswordDto) {
        log.info("Inside user service reset password method");
        try {
            // validate
            if (resetPasswordDto.getEmail() == null || resetPasswordDto.getEmail().isEmpty()) {
                log.error("Email is required");
                throw new BadRequestAlertException("Email is required", "User", "error");
            }
            if (resetPasswordDto.getNewPassword() == null || resetPasswordDto.getNewPassword().isEmpty()) {
                log.error("New password is required");
                throw new BadRequestAlertException("New password is required", "User", "error");
            }
            if (resetPasswordDto.getOtp() == null || resetPasswordDto.getOtp().isEmpty()) {
                log.error("OTP is required");
                throw new BadRequestAlertException("OTP is required", "User", "error");
            }

            // verify otp
            if (!verificationTokenService.validateOtp(resetPasswordDto.getEmail(), resetPasswordDto.getOtp())) {
                log.error("Invalid or expired OTP");
                throw new BadRequestAlertException("Invalid or expired OTP", "User", "error");
            }

            Optional<WHMUser> userOpt = userDao.findByEmail(resetPasswordDto.getEmail());
            if (userOpt.isEmpty()) throw new BadRequestAlertException("User not found", "User", "error");

            WHMUser user = userOpt.get();
            user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword())); // Encrypt the password
            userDao.save(user);

            verificationTokenService.cleanUpOtp(user.getUserId()); // Clean up OTP

            // send password reset success email
            String content = PASSWORD_RESET_SUCCESS_EMAIL_CONTENT;
            content = content.replace("{{firstName}}", user.getFirstName());

            emailService.sendEmail(user.getEmail(), PASSWORD_RESET_SUCCESS_EMAIL_SUBJECT, content);

            ResponseDto response = new ResponseDto();
            response.setMessage("Password reset successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }

    @Override
    public ResponseEntity<ResponseDto> changePassword(PasswordChangeDto passwordChangeDto) {
        log.info("Inside user service change password method");
        try {
            // validate
            if (passwordChangeDto.getUserId() == null) {
                log.error("User id is required");
                throw new BadRequestAlertException("User id is required", "User", "error");
            }
            if (passwordChangeDto.getOldPassword() == null || passwordChangeDto.getOldPassword().isEmpty()) {
                log.error("Old password is required");
                throw new BadRequestAlertException("Old password is required", "User", "error");
            }
            if (passwordChangeDto.getNewPassword() == null || passwordChangeDto.getNewPassword().isEmpty()) {
                log.error("New password is required");
                throw new BadRequestAlertException("New password is required", "User", "error");
            }

            // Fetch the user by userId
            Optional<WHMUser> userOpt = userDao.findById(passwordChangeDto.getUserId());
            if (userOpt.isEmpty()) {
                throw new BadRequestAlertException("User not found", "User", "error");
            }

            WHMUser user = userOpt.get();

            // Verify the old password
            if (!passwordEncoder.matches(passwordChangeDto.getOldPassword(), user.getPassword())) {
                throw new BadRequestAlertException("Old password is incorrect", "User", "error");
            }

            // Update the password
            user.setPassword(passwordEncoder.encode(passwordChangeDto.getNewPassword())); // Encrypt new password
            userDao.save(user);

            // send password change success email
            String content = CHANGE_PASSWORD_SUCCESS_EMAIL_CONTENT;
            content = content.replace("{{firstName}}", user.getFirstName());

            emailService.sendEmail(user.getEmail(), CHANGE_PASSWORD_SUCCESS_EMAIL_SUBJECT, content);

            ResponseDto response = new ResponseDto();
            response.setMessage("Password changed successfully");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            throw new BadRequestAlertException(e.getMessage(), "User", "error");
        }
    }
}
