package org.bytecub.WedahamineBackend.dto.master;

import lombok.*;
import org.bytecub.WedahamineBackend.dto.reference.WHRProductCategoryDto;
import org.bytecub.WedahamineBackend.dto.reference.WHRStatusDto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * DTO for {@link org.bytecub.WedahamineBackend.model.master.WHMProduct}
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class WHMProductDto implements Serializable {
    private Long productId;
    private String item;
    private String description;
    private BigDecimal unitPrice;
    private BigDecimal discount;
    private Double qty;
    private String unit;
    private Boolean isActive;
    private WHRProductCategoryDto whrProductCategory;
    private WHRStatusDto whrStatus;

    private Long categoryId;
    private Long statusId;
}