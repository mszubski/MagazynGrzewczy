package com.vistula.magazyn.repository;
import com.vistula.magazyn.domain.DaneKlient;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DaneKlient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DaneKlientRepository extends JpaRepository<DaneKlient, Long> {

}
