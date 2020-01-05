package com.vistula.magazyn.service.impl;

import com.vistula.magazyn.service.ZamowienieWpisService;
import com.vistula.magazyn.domain.ZamowienieWpis;
import com.vistula.magazyn.repository.ZamowienieWpisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ZamowienieWpis}.
 */
@Service
@Transactional
public class ZamowienieWpisServiceImpl implements ZamowienieWpisService {

    private final Logger log = LoggerFactory.getLogger(ZamowienieWpisServiceImpl.class);

    private final ZamowienieWpisRepository zamowienieWpisRepository;

    public ZamowienieWpisServiceImpl(ZamowienieWpisRepository zamowienieWpisRepository) {
        this.zamowienieWpisRepository = zamowienieWpisRepository;
    }

    /**
     * Save a zamowienieWpis.
     *
     * @param zamowienieWpis the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ZamowienieWpis save(ZamowienieWpis zamowienieWpis) {
        log.debug("Request to save ZamowienieWpis : {}", zamowienieWpis);
        return zamowienieWpisRepository.save(zamowienieWpis);
    }

    /**
     * Get all the zamowienieWpis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ZamowienieWpis> findAll(Pageable pageable) {
        log.debug("Request to get all ZamowienieWpis");
        return zamowienieWpisRepository.findAll(pageable);
    }


    /**
     * Get one zamowienieWpis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ZamowienieWpis> findOne(Long id) {
        log.debug("Request to get ZamowienieWpis : {}", id);
        return zamowienieWpisRepository.findById(id);
    }

    /**
     * Delete the zamowienieWpis by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ZamowienieWpis : {}", id);
        zamowienieWpisRepository.deleteById(id);
    }
}
