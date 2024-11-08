package org.bytecub.WedahamineBackend.dto.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {
    private String tokenType;
    private long expiresIn;
    private String token;
    private String refreshToken;
}
