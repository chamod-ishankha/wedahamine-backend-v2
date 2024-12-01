package org.bytecub.WedahamineBackend.model.transaction;

import jakarta.persistence.*;
import lombok.*;
import org.bytecub.WedahamineBackend.config.audit.AuditModel;
import org.bytecub.WedahamineBackend.model.master.WHMUser;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.bytecub.WedahamineBackend.constants.TableNames.VERIFICATION_TOKEN_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = VERIFICATION_TOKEN_TABLE, indexes = {
        @Index(name = "fk_WH_T_VERIFICATION_TOKEN_WH_M_USERS1_idx", columnList = "USER_ID")
})
public class WHTVerificationToken extends AuditModel {
    @Id
    @SequenceGenerator(name = VERIFICATION_TOKEN_TABLE, allocationSize = 1, sequenceName = VERIFICATION_TOKEN_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = VERIFICATION_TOKEN_TABLE)
    @Column(name = "VERIFICATION_ID")
    private Long verificationId;

    @Column(name = "VERIFICATION_TOKEN")
    private String verificationToken;

    @Column(name = "EXPIRY_DATE")
    private LocalDateTime expiryDate;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = false)
    private WHMUser whmUser;

    public WHTVerificationToken(WHMUser whmUser) {
        this.whmUser = whmUser;
        this.verificationToken = UUID.randomUUID().toString();
        this.expiryDate = LocalDateTime.now().plusHours(12); // Token expires in 12 hours
    }
}
