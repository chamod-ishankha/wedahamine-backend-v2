package org.bytecub.WedahamineBackend.model.master;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bytecub.WedahamineBackend.model.reference.WHRProductCategory;
import org.bytecub.WedahamineBackend.model.reference.WHRStatus;

import java.math.BigDecimal;

import static org.bytecub.WedahamineBackend.constants.TableNames.PRODUCT_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = PRODUCT_TABLE)
public class WHMProduct {
    @Id
    @SequenceGenerator(name = PRODUCT_TABLE, allocationSize = 1, sequenceName = PRODUCT_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = PRODUCT_TABLE)
    @Column(name = "PRODUCT_ID")
    private Long productId;
    @Column(name = "ITEM")
    private String item;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "UNIT_PRICE", precision = 10, scale = 2)
    private BigDecimal unitPrice;
    @Column(name = "DISCOUNT", precision = 10, scale = 2)
    private BigDecimal discount;
    @Column(name = "QUANTITY")
    private Double qty;
    @Column(name = "UNIT")
    private String unit;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID", nullable=false)
    private WHRProductCategory whrProductCategory;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATUS_ID", referencedColumnName = "STATUS_ID", nullable=false)
    private WHRStatus whrStatus;

    /**
     * to do:
     * 1. Add Supplier Details
      */

}
