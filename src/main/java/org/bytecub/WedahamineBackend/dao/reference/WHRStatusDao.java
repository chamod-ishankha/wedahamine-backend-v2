package org.bytecub.WedahamineBackend.dao.reference;

import org.bytecub.WedahamineBackend.model.reference.WHRStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WHRStatusDao extends JpaRepository<WHRStatus, Long> {
}
