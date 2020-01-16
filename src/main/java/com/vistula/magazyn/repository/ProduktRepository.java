package com.vistula.magazyn.repository;
import com.vistula.magazyn.domain.Produkt;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Produkt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduktRepository extends JpaRepository<Produkt, Long> {
    List<Produkt> findAll();
}
