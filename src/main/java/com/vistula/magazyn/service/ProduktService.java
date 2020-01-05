package com.vistula.magazyn.service;

import com.vistula.magazyn.domain.Produkt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Produkt}.
 */
public interface ProduktService {

    /**
     * Save a produkt.
     *
     * @param produkt the entity to save.
     * @return the persisted entity.
     */
    Produkt save(Produkt produkt);

    /**
     * Get all the produkts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Produkt> findAll(Pageable pageable);


    /**
     * Get the "id" produkt.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Produkt> findOne(Long id);

    /**
     * Delete the "id" produkt.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
