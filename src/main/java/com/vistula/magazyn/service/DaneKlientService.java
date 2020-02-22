package com.vistula.magazyn.service;

import com.vistula.magazyn.domain.DaneKlient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DaneKlient}.
 */
public interface DaneKlientService {

    /**
     * Save a daneKlient.
     *
     * @param daneKlient the entity to save.
     * @return the persisted entity.
     */
    DaneKlient save(DaneKlient daneKlient);

    /**
     * Get all the daneKlients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DaneKlient> findAll(Pageable pageable);


    /**
     * Get the "id" daneKlient.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DaneKlient> findOne(Long id);

    /**
     * Delete the "id" daneKlient.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
