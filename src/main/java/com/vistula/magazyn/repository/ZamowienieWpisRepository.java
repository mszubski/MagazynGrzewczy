package com.vistula.magazyn.repository;
import com.vistula.magazyn.domain.ZamowienieWpis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the ZamowienieWpis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZamowienieWpisRepository extends JpaRepository<ZamowienieWpis, Long> {

    @Query("select zamowienieWpis from ZamowienieWpis zamowienieWpis where zamowienieWpis.user.login = ?#{principal.username}")
    List<ZamowienieWpis> findByUserIsCurrentUser();

}
