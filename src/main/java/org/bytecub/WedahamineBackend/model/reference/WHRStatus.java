package org.bytecub.WedahamineBackend.model.reference;

import jakarta.persistence.*;
import lombok.*;
import org.bytecub.WedahamineBackend.config.audit.AuditModel;

import static org.bytecub.WedahamineBackend.constants.TableNames.STATUS_TABLE;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = STATUS_TABLE)
public class WHRStatus extends AuditModel {
    @Id
    @SequenceGenerator(name = STATUS_TABLE, allocationSize = 1, sequenceName = STATUS_TABLE + "_SEQ")
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = STATUS_TABLE)
    @Column(name = "STATUS_ID")
    private Long statusId;
    @Column(name = "STATUS_NAME")
    private String statusName;
    @Column(name = "STATUS_DESCRIPTION")
    private String statusDescription;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
}
