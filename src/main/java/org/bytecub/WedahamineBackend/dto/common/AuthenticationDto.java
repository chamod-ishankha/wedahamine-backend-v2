package org.bytecub.WedahamineBackend.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.WedahamineBackend.dto.reference.WHRStatusDto;
import org.bytecub.WedahamineBackend.model.reference.WHRStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private String uniqKey;
    private TokenDto tokenDto;
    private WHRStatusDto whrStatus;
}
