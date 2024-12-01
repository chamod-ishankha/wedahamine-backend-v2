package org.bytecub.WedahamineBackend.service.transaction;

import org.bytecub.WedahamineBackend.model.master.WHMUser;
import org.bytecub.WedahamineBackend.model.transaction.WHTVerificationToken;

import java.util.Optional;

public interface VerificationTokenService {

    WHTVerificationToken createToken(WHMUser user);

    Optional<WHTVerificationToken> getToken(String token);

    String generateOTP();

    void saveOtp(String otp, Long userId);

    boolean validateOtp(String email, String otp);

    void cleanUpOtp(Long userId);
}
