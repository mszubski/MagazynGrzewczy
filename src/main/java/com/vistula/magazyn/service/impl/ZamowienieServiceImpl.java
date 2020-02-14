package com.vistula.magazyn.service.impl;

import com.vistula.magazyn.service.ZamowienieService;
import com.vistula.magazyn.domain.Zamowienie;
import com.vistula.magazyn.repository.ZamowienieRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Zamowienie}.
 */
@Service
@Transactional
public class ZamowienieServiceImpl implements ZamowienieService {

    private final Logger log = LoggerFactory.getLogger(ZamowienieServiceImpl.class);

    private final ZamowienieRepository zamowienieRepository;

    public ZamowienieServiceImpl(ZamowienieRepository zamowienieRepository) {
        this.zamowienieRepository = zamowienieRepository;
    }

    /**
     * Save a zamowienie.
     *
     * @param zamowienie the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Zamowienie save(Zamowienie zamowienie) {
        log.debug("Request to save Zamowienie : {}", zamowienie);
        return zamowienieRepository.save(zamowienie);
    }

    /**
     * Get all the zamowienies.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Zamowienie> findAll(Pageable pageable) {
        log.debug("Request to get all Zamowienies");
        return zamowienieRepository.findAll(pageable);
    }


    /**
     * Get one zamowienie by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Zamowienie> findOne(Long id) {
        log.debug("Request to get Zamowienie : {}", id);
        return zamowienieRepository.findById(id);
    }

    /**
     * Delete the zamowienie by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Zamowienie : {}", id);
        zamowienieRepository.deleteById(id);
    }
}
