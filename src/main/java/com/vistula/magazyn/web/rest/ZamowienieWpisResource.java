package com.vistula.magazyn.web.rest;

import com.vistula.magazyn.domain.User;
import com.vistula.magazyn.domain.ZamowienieWpis;
import com.vistula.magazyn.service.UserService;
import com.vistula.magazyn.service.ZamowienieWpisService;
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

import java.net.URI;
import java.net.URISyntaxException;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vistula.magazyn.domain.ZamowienieWpis}.
 */
@RestController
@RequestMapping("/api")
public class ZamowienieWpisResource {

    private final Logger log = LoggerFactory.getLogger(ZamowienieWpisResource.class);

    private static final String ENTITY_NAME = "zamowienieWpis";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZamowienieWpisService zamowienieWpisService;
    private UserService userService;

    public ZamowienieWpisResource(ZamowienieWpisService zamowienieWpisService,UserService userService) {
        this.zamowienieWpisService = zamowienieWpisService;
        this.userService = userService;
    }

    /**
     * {@code POST  /zamowienie-wpis} : Create a new zamowienieWpis.
     *
     * @param zamowienieWpis the zamowienieWpis to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zamowienieWpis, or with status {@code 400 (Bad Request)} if the zamowienieWpis has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/zamowienie-wpis")
    public ResponseEntity<ZamowienieWpis> createZamowienieWpis(@RequestBody ZamowienieWpis zamowienieWpis) throws URISyntaxException {
        log.debug("REST request to save ZamowienieWpis : {}", zamowienieWpis);
        if (zamowienieWpis.getId() != null) {
            throw new BadRequestAlertException("A new zamowienieWpis cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ZamowienieWpis result = zamowienieWpisService.save(zamowienieWpis);
        return ResponseEntity.created(new URI("/api/zamowienie-wpis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    @PostMapping("/zamowienie-wpis-principal")
    public ResponseEntity<ZamowienieWpis> createZamowienieWpisPrincipal(Principal principal, @RequestBody ZamowienieWpis zamowienieWpis) throws URISyntaxException {
        log.debug("REST request to save ZamowienieWpis : {}", zamowienieWpis);


        if (zamowienieWpis.getId() != null) {
            throw new BadRequestAlertException("A new zamowienieWpis cannot already have an ID", ENTITY_NAME, "idexists");
        }

        Optional<User> user = userService.getUserWithAuthoritiesByLogin(principal.getName());
        if(user.isPresent()){
            zamowienieWpis.setUser(user.get());
        }
        ZamowienieWpis result = zamowienieWpisService.save(zamowienieWpis);
        return ResponseEntity.created(new URI("/api/zamowienie-wpis/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /zamowienie-wpis} : Updates an existing zamowienieWpis.
     *
     * @param zamowienieWpis the zamowienieWpis to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zamowienieWpis,
     * or with status {@code 400 (Bad Request)} if the zamowienieWpis is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zamowienieWpis couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/zamowienie-wpis")
    public ResponseEntity<ZamowienieWpis> updateZamowienieWpis(@RequestBody ZamowienieWpis zamowienieWpis) throws URISyntaxException {
        log.debug("REST request to update ZamowienieWpis : {}", zamowienieWpis);
        if (zamowienieWpis.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ZamowienieWpis result = zamowienieWpisService.save(zamowienieWpis);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zamowienieWpis.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /zamowienie-wpis} : get all the zamowienieWpis.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zamowienieWpis in body.
     */
    @GetMapping("/zamowienie-wpis")
    public ResponseEntity<List<ZamowienieWpis>> getAllZamowienieWpis(Pageable pageable) {
        log.debug("REST request to get a page of ZamowienieWpis");
        Page<ZamowienieWpis> page = zamowienieWpisService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /zamowienie-wpis/:id} : get the "id" zamowienieWpis.
     *
     * @param id the id of the zamowienieWpis to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zamowienieWpis, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/zamowienie-wpis/{id}")
    public ResponseEntity<ZamowienieWpis> getZamowienieWpis(@PathVariable Long id) {
        log.debug("REST request to get ZamowienieWpis : {}", id);
        Optional<ZamowienieWpis> zamowienieWpis = zamowienieWpisService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zamowienieWpis);
    }

    /**
     * {@code DELETE  /zamowienie-wpis/:id} : delete the "id" zamowienieWpis.
     *
     * @param id the id of the zamowienieWpis to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/zamowienie-wpis/{id}")
    public ResponseEntity<Void> deleteZamowienieWpis(@PathVariable Long id) {
        log.debug("REST request to delete ZamowienieWpis : {}", id);
        zamowienieWpisService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
