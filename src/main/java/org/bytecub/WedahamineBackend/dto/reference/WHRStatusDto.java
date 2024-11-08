package org.bytecub.WedahamineBackend.dto.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link org.bytecub.WedahamineBackend.model.reference.WHRStatus}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WHRStatusDto implements Serializable {
    private Long statusId;
    private String statusName;
    private String statusDescription;
    private Boolean isActive;
}