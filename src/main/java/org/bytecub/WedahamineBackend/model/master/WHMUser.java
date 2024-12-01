package org.bytecub.WedahamineBackend.model.master;

import jakarta.persistence.*;
import lombok.*;
import org.bytecub.WedahamineBackend.config.audit.AuditModel;
import org.bytecub.WedahamineBackend.model.reference.WHRStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.bytecub.WedahamineBackend.constants.TableNames.USER_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = USER_TABLE, indexes = {
        @Index(name = "UNIQUE_WH_M_USERS_UNIQ_KEY1_idx", columnList = "UNIQ_KEY", unique = true),
        @Index(name = "fk_WH_M_USERS_WH_R_STATUS1_idx", columnList = "STATUS_ID")
})
public class WHMUser extends AuditModel implements UserDetails {
    @Id
    @SequenceGenerator(name = USER_TABLE, allocationSize = 1, sequenceName = USER_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = USER_TABLE)
    @Column(name = "USER_ID")
    private Long userId;
    @Column(name = "FIRST_NAME")
    private String firstName;
    @Column(name = "LAST_NAME")
    private String lastName;
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "PASSWORD")
    private String password;
    @Column(name = "PHONE")
    private String phone;
    @Column(name = "ROLE")
    private String role;
    @Column(name = "UNIQ_KEY")
    private String uniqKey;
    @Column(name = "DOB")
    private LocalDate dob;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
    @Column(name = "IS_VERIFIED")
    private Boolean isVerified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID", nullable = false)
    private WHRStatus whrStatus;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
