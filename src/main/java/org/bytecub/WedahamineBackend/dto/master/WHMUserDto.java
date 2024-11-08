package org.bytecub.WedahamineBackend.dto.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WHMUserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String role;
    private String status;
    private String uniqKey;
    private LocalDate dob;
}
