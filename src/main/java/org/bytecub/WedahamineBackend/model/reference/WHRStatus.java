package org.bytecub.WedahamineBackend.model.reference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static org.bytecub.WedahamineBackend.constants.TableNames.STATUS_TABLE;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = STATUS_TABLE)
public class WHRStatus {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "STATUS_ID")
    private Long statusId;
    @Column(name = "STATUS_NAME")
    private String statusName;
    @Column(name = "STATUS_DESCRIPTION")
    private String statusDescription;
    @Column(name = "IS_ACTIVE")
    private Boolean isActive;
}
