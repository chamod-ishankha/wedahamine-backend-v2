package org.bytecub.WedahamineBackend.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;
import org.bytecub.WedahamineBackend.dto.master.WHMUserDto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.bytecub.WedahamineBackend.model.transaction.WHTPasswordResetToken}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WHTPasswordResetTokenDto implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long resetTokenId;
    private String otp;
    private LocalDateTime expiresAt;
    private WHMUserDto whmUser;

    private Long userId;
}