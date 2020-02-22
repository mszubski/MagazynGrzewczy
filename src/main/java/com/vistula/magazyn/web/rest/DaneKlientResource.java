package com.vistula.magazyn.web.rest;

import com.vistula.magazyn.domain.DaneKlient;
import com.vistula.magazyn.service.DaneKlientService;
import com.vistula.magazyn.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vistula.magazyn.domain.DaneKlient}.
 */
@RestController
@RequestMapping("/api")
public class DaneKlientResource {

    private final Logger log = LoggerFactory.getLogger(DaneKlientResource.class);

    private static final String ENTITY_NAME = "daneKlient";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DaneKlientService daneKlientService;

    public DaneKlientResource(DaneKlientService daneKlientService) {
        this.daneKlientService = daneKlientService;
    }

    /**
     * {@code POST  /dane-klients} : Create a new daneKlient.
     *
     * @param daneKlient the daneKlient to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new daneKlient, or with status {@code 400 (Bad Request)} if the daneKlient has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dane-klients")
    public ResponseEntity<DaneKlient> createDaneKlient(@Valid @RequestBody DaneKlient daneKlient) throws URISyntaxException {
        log.debug("REST request to save DaneKlient : {}", daneKlient);
        if (daneKlient.getId() != null) {
            throw new BadRequestAlertException("A new daneKlient cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DaneKlient result = daneKlientService.save(daneKlient);
        return ResponseEntity.created(new URI("/api/dane-klients/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dane-klients} : Updates an existing daneKlient.
     *
     * @param daneKlient the daneKlient to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated daneKlient,
     * or with status {@code 400 (Bad Request)} if the daneKlient is not valid,
     * or with status {@code 500 (Internal Server Error)} if the daneKlient couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dane-klients")
    public ResponseEntity<DaneKlient> updateDaneKlient(@Valid @RequestBody DaneKlient daneKlient) throws URISyntaxException {
        log.debug("REST request to update DaneKlient : {}", daneKlient);
        if (daneKlient.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DaneKlient result = daneKlientService.save(daneKlient);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, daneKlient.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dane-klients} : get all the daneKlients.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of daneKlients in body.
     */
    @GetMapping("/dane-klients")
    public ResponseEntity<List<DaneKlient>> getAllDaneKlients(Pageable pageable) {
        log.debug("REST request to get a page of DaneKlients");
        Page<DaneKlient> page = daneKlientService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dane-klients/:id} : get the "id" daneKlient.
     *
     * @param id the id of the daneKlient to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the daneKlient, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dane-klients/{id}")
    public ResponseEntity<DaneKlient> getDaneKlient(@PathVariable Long id) {
        log.debug("REST request to get DaneKlient : {}", id);
        Optional<DaneKlient> daneKlient = daneKlientService.findOne(id);
        return ResponseUtil.wrapOrNotFound(daneKlient);
    }

    /**
     * {@code DELETE  /dane-klients/:id} : delete the "id" daneKlient.
     *
     * @param id the id of the daneKlient to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dane-klients/{id}")
    public ResponseEntity<Void> deleteDaneKlient(@PathVariable Long id) {
        log.debug("REST request to delete DaneKlient : {}", id);
        daneKlientService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
