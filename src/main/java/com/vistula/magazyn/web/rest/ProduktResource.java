package com.vistula.magazyn.web.rest;

import com.vistula.magazyn.domain.Produkt;
import com.vistula.magazyn.service.ProduktService;
import com.vistula.magazyn.service.ProduktXlsxService;
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

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vistula.magazyn.domain.Produkt}.
 */
@RestController
@RequestMapping("/api")
public class ProduktResource {

    private final Logger log = LoggerFactory.getLogger(ProduktResource.class);

    private static final String ENTITY_NAME = "produkt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ProduktService produktService;
    private final ProduktXlsxService produktXlsxService;

    public ProduktResource(ProduktService produktService, ProduktXlsxService produktXlsxService) {
        this.produktService = produktService;
        this.produktXlsxService = produktXlsxService;
    }

    /**
     * {@code POST  /produkts} : Create a new produkt.
     *
     * @param produkt the produkt to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new produkt, or with status {@code 400 (Bad Request)} if the produkt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/produkts")
    public ResponseEntity<Produkt> createProdukt(@RequestBody Produkt produkt) throws URISyntaxException {
        log.debug("REST request to save Produkt : {}", produkt);
        if (produkt.getId() != null) {
            throw new BadRequestAlertException("A new produkt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Produkt result = produktService.save(produkt);
        return ResponseEntity.created(new URI("/api/produkts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /produkts} : Updates an existing produkt.
     *
     * @param produkt the produkt to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated produkt,
     * or with status {@code 400 (Bad Request)} if the produkt is not valid,
     * or with status {@code 500 (Internal Server Error)} if the produkt couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/produkts")
    public ResponseEntity<Produkt> updateProdukt(@RequestBody Produkt produkt) throws URISyntaxException {
        log.debug("REST request to update Produkt : {}", produkt);
        if (produkt.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Produkt result = produktService.save(produkt);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, produkt.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /produkts} : get all the produkts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of produkts in body.
     */
    @GetMapping("/produkts")
    public ResponseEntity<List<Produkt>> getAllProdukts(Pageable pageable) {
        log.debug("REST request to get a page of Produkts");
        Page<Produkt> page = produktService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /produkts/:id} : get the "id" produkt.
     *
     * @param id the id of the produkt to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the produkt, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/produkts/{id}")
    public ResponseEntity<Produkt> getProdukt(@PathVariable Long id) {
        log.debug("REST request to get Produkt : {}", id);
        Optional<Produkt> produkt = produktService.findOne(id);
        return ResponseUtil.wrapOrNotFound(produkt);
    }

    /**
     * {@code DELETE  /produkts/:id} : delete the "id" produkt.
     *
     * @param id the id of the produkt to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/produkts/{id}")
    public ResponseEntity<Void> deleteProdukt(@PathVariable Long id) {
        log.debug("REST request to delete Produkt : {}", id);
        produktService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    @GetMapping("/produkts/xlsx/{path}")
    public ResponseEntity<Void> getPodpisyXlsx(@PathVariable String path) throws IOException, NoSuchFieldException {
        log.debug("REST request to get PodpisyXlsx");
        produktXlsxService.getProduktyXlsxFile(path);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, path)).build();
    }
}
