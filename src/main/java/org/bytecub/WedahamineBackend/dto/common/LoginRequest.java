package org.bytecub.WedahamineBackend.dto.common;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "Email is required")
    @NotNull(message = "Email is required")
    private String email;
    @NotEmpty(message = "Password is required")
    @NotNull(message = "Password is required")
    private String password;
}
