package org.bytecub.WedahamineBackend.dao.reference;

import org.bytecub.WedahamineBackend.model.reference.WHRProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface WHRProductCategoryDao extends JpaRepository<WHRProductCategory, Long>, JpaSpecificationExecutor<WHRProductCategory> {
    Optional<WHRProductCategory> findByCategoryIdAndIsActive(Long categoryId, boolean b);
}