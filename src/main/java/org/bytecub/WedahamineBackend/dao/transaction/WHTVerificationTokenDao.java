package org.bytecub.WedahamineBackend.dao.transaction;

import org.bytecub.WedahamineBackend.model.transaction.WHTVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WHTVerificationTokenDao extends JpaRepository<WHTVerificationToken, Long> {
  Optional<WHTVerificationToken> findByVerificationToken(String token);

    Optional<WHTVerificationToken> findByWhmUserUserId(Long userId);
}