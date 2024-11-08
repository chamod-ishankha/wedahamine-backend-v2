package org.bytecub.WedahamineBackend.dto.common;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @NotEmpty(message = "First name is required")
    @NotNull(message = "First name is required")
    private String firstName;
    @NotEmpty(message = "Last name is required")
    @NotNull(message = "Last name is required")
    private String lastName;
    @NotEmpty(message = "Email is required")
    @NotNull(message = "Email is required")
    private String email;
    @NotEmpty(message = "Password is required")
    @NotNull(message = "Password is required")
    private String password;
    @NotEmpty(message = "Phone is required")
    @NotNull(message = "Phone is required")
    private String phone;
    private String role;
    private String status;
}
