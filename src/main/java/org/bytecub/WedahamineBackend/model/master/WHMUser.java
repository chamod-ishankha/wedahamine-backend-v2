package org.bytecub.WedahamineBackend.model.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.WedahamineBackend.config.aduit.AuditModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import static org.bytecub.WedahamineBackend.constants.TableNames.USER_TABLE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = USER_TABLE)
public class WHMUser extends AuditModel implements UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String role;
    private String status;
    private String uniqKey;
    private LocalDate dob;

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
