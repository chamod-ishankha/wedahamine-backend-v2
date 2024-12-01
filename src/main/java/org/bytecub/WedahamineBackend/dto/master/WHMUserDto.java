package org.bytecub.WedahamineBackend.dto.master;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bytecub.WedahamineBackend.dto.reference.WHRStatusDto;
import org.bytecub.WedahamineBackend.model.reference.WHRStatus;

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
    private String uniqKey;
    private LocalDate dob;
    private Boolean isActive;
    private Boolean isVerified;
    private WHRStatusDto whrStatus;

    private Long statusId;
}
