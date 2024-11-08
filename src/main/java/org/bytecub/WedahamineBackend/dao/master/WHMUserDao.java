package org.bytecub.WedahamineBackend.dao.master;

import org.bytecub.WedahamineBackend.model.master.WHMUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WHMUserDao extends JpaRepository<WHMUser, Long> {
    Optional<WHMUser> findByEmail(String username);

    boolean existsByEmail(String email);

    boolean existsByUniqKey(String uniqKey);
}
