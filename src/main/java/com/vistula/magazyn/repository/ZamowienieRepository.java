package com.vistula.magazyn.repository;
import com.vistula.magazyn.domain.Zamowienie;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data  repository for the Zamowienie entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZamowienieRepository extends JpaRepository<Zamowienie, Long> {

    @Query("select zamowienie from Zamowienie zamowienie where zamowienie.user.login = ?#{principal.username}")
    List<Zamowienie> findByUserIsCurrentUser();

}
