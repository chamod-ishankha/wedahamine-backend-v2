package org.bytecub.WedahamineBackend.model.reference;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bytecub.WedahamineBackend.config.audit.AuditModel;

import static org.bytecub.WedahamineBackend.constants.TableNames.CATEGORY_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = CATEGORY_TABLE)
public class WHRProductCategory extends AuditModel {
    @Id
    @SequenceGenerator(name = CATEGORY_TABLE, allocationSize = 1, sequenceName = CATEGORY_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = CATEGORY_TABLE)
    @Column(name = "CATEGORY_ID")
    private Long categoryId;
    @Column(name = "CATEGORY_NAME")
    private String categoryName;
    @Column(name = "CATEGORY_DESCRIPTION")
    private String categoryDescription;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
}
