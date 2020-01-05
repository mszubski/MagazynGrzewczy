package com.vistula.magazyn.repository;
import com.vistula.magazyn.domain.ZamowienieWpis;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ZamowienieWpis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZamowienieWpisRepository extends JpaRepository<ZamowienieWpis, Long> {

}
