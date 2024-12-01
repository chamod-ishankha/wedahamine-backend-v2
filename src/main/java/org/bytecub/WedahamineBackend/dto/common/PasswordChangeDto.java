package org.bytecub.WedahamineBackend.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PasswordChangeDto {

    private Long userId;
    private String oldPassword;
    private String newPassword;

}
