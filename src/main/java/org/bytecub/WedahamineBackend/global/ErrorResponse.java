package org.bytecub.WedahamineBackend.global;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private String timestamp;
    private String message;
    private String error;
}
