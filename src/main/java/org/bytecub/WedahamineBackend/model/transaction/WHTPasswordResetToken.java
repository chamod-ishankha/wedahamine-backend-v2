package org.bytecub.WedahamineBackend.model.transaction;

import jakarta.persistence.*;
import lombok.*;
import org.bytecub.WedahamineBackend.config.audit.AuditModel;
import org.bytecub.WedahamineBackend.model.master.WHMUser;

import java.time.LocalDateTime;

import static org.bytecub.WedahamineBackend.constants.TableNames.PASSWORD_RESET_TOKEN_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = PASSWORD_RESET_TOKEN_TABLE, indexes = {
        @Index(name = "fk_WH_T_PASSWORD_RESET_TOKEN_WH_M_USERS1_idx", columnList = "USER_ID")
})
public class WHTPasswordResetToken extends AuditModel {

    @Id
    @SequenceGenerator(name = PASSWORD_RESET_TOKEN_TABLE, allocationSize = 1, sequenceName = PASSWORD_RESET_TOKEN_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = PASSWORD_RESET_TOKEN_TABLE)
    @Column(name = "RESET_TOKEN_ID")
    private Long resetTokenId;

    @Column(name = "OTP")
    private String otp;

    @Column(name = "EXPIRES_AT")
    private LocalDateTime expiresAt;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false)
    private WHMUser whmUser;

}
