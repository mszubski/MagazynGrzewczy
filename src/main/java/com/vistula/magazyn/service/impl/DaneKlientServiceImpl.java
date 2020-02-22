package com.vistula.magazyn.service.impl;

import com.vistula.magazyn.service.DaneKlientService;
import com.vistula.magazyn.domain.DaneKlient;
import com.vistula.magazyn.repository.DaneKlientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DaneKlient}.
 */
@Service
@Transactional
public class DaneKlientServiceImpl implements DaneKlientService {

    private final Logger log = LoggerFactory.getLogger(DaneKlientServiceImpl.class);

    private final DaneKlientRepository daneKlientRepository;

    public DaneKlientServiceImpl(DaneKlientRepository daneKlientRepository) {
        this.daneKlientRepository = daneKlientRepository;
    }

    /**
     * Save a daneKlient.
     *
     * @param daneKlient the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DaneKlient save(DaneKlient daneKlient) {
        log.debug("Request to save DaneKlient : {}", daneKlient);
        return daneKlientRepository.save(daneKlient);
    }

    /**
     * Get all the daneKlients.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DaneKlient> findAll(Pageable pageable) {
        log.debug("Request to get all DaneKlients");
        return daneKlientRepository.findAll(pageable);
    }


    /**
     * Get one daneKlient by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DaneKlient> findOne(Long id) {
        log.debug("Request to get DaneKlient : {}", id);
        return daneKlientRepository.findById(id);
    }

    /**
     * Delete the daneKlient by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DaneKlient : {}", id);
        daneKlientRepository.deleteById(id);
    }
}
