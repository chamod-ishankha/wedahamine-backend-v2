package org.bytecub.WedahamineBackend.dao.master;

import org.bytecub.WedahamineBackend.model.master.WHMProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WHMProductDao extends JpaRepository<WHMProduct, Long> {
}