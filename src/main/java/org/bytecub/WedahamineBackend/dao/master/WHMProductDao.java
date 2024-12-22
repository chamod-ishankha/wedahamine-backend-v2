package org.bytecub.WedahamineBackend.dao.master;

import org.bytecub.WedahamineBackend.model.master.WHMProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WHMProductDao extends JpaRepository<WHMProduct, Long>, JpaSpecificationExecutor<WHMProduct> {
    Optional<WHMProduct> findByProductIdAndIsActive(Long productId, boolean b);
}