package org.bytecub.WedahamineBackend.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String status;
    private String uniqKey;
    private TokenDto tokenDto;
}
