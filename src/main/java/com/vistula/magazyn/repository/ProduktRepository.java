package com.vistula.magazyn.repository;

import com.vistula.magazyn.domain.Produkt;
import com.vistula.magazyn.domain.enumeration.ProduktKategoriaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Produkt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProduktRepository extends JpaRepository<Produkt, Long> {
    List<Produkt> findAll();

    Optional<List<Produkt>> findAllByKategoria(ProduktKategoriaEnum produktKategoriaEnum);

    Page<Produkt> findAllByKategoria(ProduktKategoriaEnum produktKategoriaEnum, Pageable pageable);
}
