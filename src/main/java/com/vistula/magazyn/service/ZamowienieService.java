package com.vistula.magazyn.service;

import com.vistula.magazyn.domain.Zamowienie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Zamowienie}.
 */
public interface ZamowienieService {

    /**
     * Save a zamowienie.
     *
     * @param zamowienie the entity to save.
     * @return the persisted entity.
     */
    Zamowienie save(Zamowienie zamowienie);

    /**
     * Get all the zamowienies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Zamowienie> findAll(Pageable pageable);


    /**
     * Get the "id" zamowienie.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Zamowienie> findOne(Long id);

    /**
     * Delete the "id" zamowienie.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
