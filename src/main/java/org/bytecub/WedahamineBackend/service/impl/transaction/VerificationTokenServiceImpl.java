package org.bytecub.WedahamineBackend.service.impl.transaction;

import lombok.extern.slf4j.Slf4j;
import org.bytecub.WedahamineBackend.dao.master.WHMUserDao;
import org.bytecub.WedahamineBackend.dao.transaction.WHTPasswordResetTokenDao;
import org.bytecub.WedahamineBackend.dao.transaction.WHTVerificationTokenDao;
import org.bytecub.WedahamineBackend.dto.transaction.WHTPasswordResetTokenDto;
import org.bytecub.WedahamineBackend.mappers.transaction.WHTPasswordResetTokenMapper;
import org.bytecub.WedahamineBackend.model.master.WHMUser;
import org.bytecub.WedahamineBackend.model.transaction.WHTPasswordResetToken;
import org.bytecub.WedahamineBackend.model.transaction.WHTVerificationToken;
import org.bytecub.WedahamineBackend.service.transaction.VerificationTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
public class VerificationTokenServiceImpl implements VerificationTokenService {

    private final WHTVerificationTokenDao verificationTokenDao;
    private final WHTPasswordResetTokenDao passwordResetTokenDao;
    private final WHTPasswordResetTokenMapper passwordResetTokenMapper;
    private final WHMUserDao userDao;

    public VerificationTokenServiceImpl(WHTVerificationTokenDao verificationTokenDao, WHTPasswordResetTokenDao passwordResetTokenDao, WHTPasswordResetTokenMapper passwordResetTokenMapper, WHMUserDao userDao) {
        this.verificationTokenDao = verificationTokenDao;
        this.passwordResetTokenDao = passwordResetTokenDao;
        this.passwordResetTokenMapper = passwordResetTokenMapper;
        this.userDao = userDao;
    }

    @Override
    public WHTVerificationToken createToken(WHMUser user) {
        Optional<WHTVerificationToken> existingToken = verificationTokenDao.findByWhmUserUserId(user.getUserId());
        if (existingToken.isPresent()) {
            if (existingToken.get().getExpiryDate().isBefore(LocalDateTime.now())) {
                verificationTokenDao.delete(existingToken.get());
            } else {
                return existingToken.get();
            }
        }
        WHTVerificationToken token = new WHTVerificationToken(user);
        return verificationTokenDao.save(token);
    }

    @Override
    public Optional<WHTVerificationToken> getToken(String token) {
        return verificationTokenDao.findByVerificationToken(token);
    }

    @Override
    public String generateOTP() {
        return String.valueOf(100000 + new Random().nextInt(900000)); // Generates a 6-digit OTP
    }

    @Override
    public void saveOtp(String otp, Long userId) {
        WHTPasswordResetTokenDto token = new WHTPasswordResetTokenDto();
        token.setUserId(userId);
        token.setOtp(otp);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(10)); // OTP expires in 10 minutes
        cleanUpOtp(userId);
        passwordResetTokenDao.save(passwordResetTokenMapper.toEntity(token));
    }

    @Override
    public boolean validateOtp(String email, String otp) {
        Optional<WHTPasswordResetToken> tokenOpt = passwordResetTokenDao.findByWhmUserEmail(email);
        if (tokenOpt.isEmpty()) return false;

        WHTPasswordResetToken token = tokenOpt.get();
        if (token.getExpiresAt().isBefore(LocalDateTime.now())) return false; // Expired
        return token.getOtp().equals(otp); // Check OTP match
    }

    @Override
    public void cleanUpOtp(Long userId) {
        passwordResetTokenDao.findByWhmUserUserId(userId).ifPresent(passwordResetTokenDao::delete);
    }
}
