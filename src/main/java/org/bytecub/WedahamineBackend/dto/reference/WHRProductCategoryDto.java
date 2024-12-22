package org.bytecub.WedahamineBackend.dto.reference;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link org.bytecub.WedahamineBackend.model.reference.WHRProductCategory}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WHRProductCategoryDto implements Serializable {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    private Long categoryId;
    private String categoryName;
    private String categoryDescription;
    private Boolean isActive;
}