package org.bytecub.WedahamineBackend.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResetPasswordDto {
    private String email;
    private String otp;
    private String newPassword;
}
