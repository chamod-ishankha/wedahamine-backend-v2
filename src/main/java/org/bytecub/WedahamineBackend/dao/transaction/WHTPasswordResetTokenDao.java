package org.bytecub.WedahamineBackend.dao.transaction;

import org.bytecub.WedahamineBackend.model.transaction.WHTPasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WHTPasswordResetTokenDao extends JpaRepository<WHTPasswordResetToken, Long>, JpaSpecificationExecutor<WHTPasswordResetToken> {
  void deleteByWhmUserUserId(Long userId);

  Optional<WHTPasswordResetToken> findByWhmUserEmail(String email);

  void deleteByWhmUserEmail(String email);

  Optional<WHTPasswordResetToken> findByWhmUserUserId(Long userId);
}