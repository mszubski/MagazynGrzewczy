package com.vistula.magazyn.service.impl;

import com.vistula.magazyn.domain.enumeration.ProduktKategoriaEnum;
import com.vistula.magazyn.service.ProduktService;
import com.vistula.magazyn.domain.Produkt;
import com.vistula.magazyn.repository.ProduktRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Produkt}.
 */
@Service
@Transactional
public class ProduktServiceImpl implements ProduktService {

    private final Logger log = LoggerFactory.getLogger(ProduktServiceImpl.class);

    private final ProduktRepository produktRepository;

    public ProduktServiceImpl(ProduktRepository produktRepository) {
        this.produktRepository = produktRepository;
    }

    /**
     * Save a produkt.
     *
     * @param produkt the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Produkt save(Produkt produkt) {
        log.debug("Request to save Produkt : {}", produkt);
        return produktRepository.save(produkt);
    }

    /**
     * Get all the produkts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Produkt> findAll(Pageable pageable) {
        log.debug("Request to get all Produkts");
        return produktRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Produkt> findAllByKategoria(Pageable pageable, ProduktKategoriaEnum produktKategoriaEnum) {
        return produktRepository.findAllByKategoria(pageable, produktKategoriaEnum);
    }


    /**
     * Get one produkt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Produkt> findOne(Long id) {
        log.debug("Request to get Produkt : {}", id);
        return produktRepository.findById(id);
    }

    /**
     * Delete the produkt by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produkt : {}", id);
        produktRepository.deleteById(id);
    }
}
