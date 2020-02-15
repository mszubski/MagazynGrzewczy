package com.vistula.magazyn.service;

import com.vistula.magazyn.domain.User;
import com.vistula.magazyn.domain.ZamowienieWpis;

import com.vistula.magazyn.domain.enumeration.StatusEnum;
import com.vistula.magazyn.domain.enumeration.StatusZamowieniaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link ZamowienieWpis}.
 */
public interface ZamowienieWpisService {

    /**
     * Save a zamowienieWpis.
     *
     * @param zamowienieWpis the entity to save.
     * @return the persisted entity.
     */
    ZamowienieWpis save(ZamowienieWpis zamowienieWpis);

    /**
     * Get all the zamowienieWpis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ZamowienieWpis> findAll(Pageable pageable);

    /**
     * Get all the zamowienieWpisByUser.
     *
     * @param pageable the pagination information.
     * @param user
     * @return the list of entities.
     */
    Page<ZamowienieWpis> findAllByUserLogin(Pageable pageable, Optional<User> user);

    /**
     * Get all the zamowienieWpisByUser.
     *
     * @param pageable the pagination information.
     * @param user
     * @return the list of entities.
     */
    Page<ZamowienieWpis> findAllByUserAndStatusAndStatusZamowienia(Pageable pageable, Optional<User> user,
                                                                        StatusEnum statusEnum, StatusZamowieniaEnum statusZamowieniaEnum);


    /**
     * Get the "id" zamowienieWpis.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ZamowienieWpis> findOne(Long id);

    /**
     * Delete the "id" zamowienieWpis.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
