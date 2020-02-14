package com.vistula.magazyn.web.rest;

import com.vistula.magazyn.domain.User;
import com.vistula.magazyn.domain.Zamowienie;
import com.vistula.magazyn.repository.ZamowienieWpisRepository;
import com.vistula.magazyn.service.UserService;
import com.vistula.magazyn.service.ZamowienieService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.vistula.magazyn.domain.Zamowienie}.
 */
@RestController
@RequestMapping("/api")
public class ZamowienieResource {

    private final Logger log = LoggerFactory.getLogger(ZamowienieResource.class);

    private static final String ENTITY_NAME = "zamowienie";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ZamowienieService zamowienieService;
    private final ZamowienieWpisRepository zamowienieWpisRepository;
    private UserService userService;

    public ZamowienieResource(ZamowienieService zamowienieService,
                              ZamowienieWpisRepository zamowienieWpisRepository,
                              UserService userService) {
        this.zamowienieService = zamowienieService;
        this.zamowienieWpisRepository = zamowienieWpisRepository;
        this.userService = userService;
    }

    /**
     * {@code POST  /zamowienies} : Create a new zamowienie.
     *
     * @param zamowienie the zamowienie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zamowienie, or with status {@code 400 (Bad Request)} if the zamowienie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/zamowienies")
    public ResponseEntity<Zamowienie> createZamowienie(@RequestBody Zamowienie zamowienie) throws URISyntaxException {
        log.debug("REST request to save Zamowienie : {}", zamowienie);
        if (zamowienie.getId() != null) {
            throw new BadRequestAlertException("A new zamowienie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Zamowienie result = zamowienieService.save(zamowienie);
        return ResponseEntity.created(new URI("/api/zamowienies/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code POST  /zamowienie} : Create a new zamowienie.
     *
     * @param zamowienie the zamowienie to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new zamowienie, or with status {@code 400 (Bad Request)} if the zamowienie has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/zamowienie")
    public ResponseEntity<Zamowienie> createZamowienieWithPrincipal(Principal principal, @RequestBody Zamowienie zamowienie) throws URISyntaxException {
        log.debug("REST request to save Zamowienie : {}", zamowienie);

        if (zamowienie.getId() != null) {
            throw new BadRequestAlertException("A new zamowienie cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(principal.getName());
        if(user.isPresent()){
            zamowienie.setUser(user.get());
        }
        Zamowienie result = zamowienieService.save(zamowienie);

        return ResponseEntity.created(new URI("/api/zamowienie/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /zamowienies} : Updates an existing zamowienie.
     *
     * @param zamowienie the zamowienie to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated zamowienie,
     * or with status {@code 400 (Bad Request)} if the zamowienie is not valid,
     * or with status {@code 500 (Internal Server Error)} if the zamowienie couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/zamowienies")
    public ResponseEntity<Zamowienie> updateZamowienie(@RequestBody Zamowienie zamowienie) throws URISyntaxException {
        log.debug("REST request to update Zamowienie : {}", zamowienie);
        if (zamowienie.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Zamowienie result = zamowienieService.save(zamowienie);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, zamowienie.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /zamowienies} : get all the zamowienies.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of zamowienies in body.
     */
    @GetMapping("/zamowienies")
    public ResponseEntity<List<Zamowienie>> getAllZamowienies(Pageable pageable) {
        log.debug("REST request to get a page of Zamowienies");
        Page<Zamowienie> page = zamowienieService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /zamowienies/:id} : get the "id" zamowienie.
     *
     * @param id the id of the zamowienie to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the zamowienie, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/zamowienies/{id}")
    public ResponseEntity<Zamowienie> getZamowienie(@PathVariable Long id) {
        log.debug("REST request to get Zamowienie : {}", id);
        Optional<Zamowienie> zamowienie = zamowienieService.findOne(id);
        return ResponseUtil.wrapOrNotFound(zamowienie);
    }

    /**
     * {@code DELETE  /zamowienies/:id} : delete the "id" zamowienie.
     *
     * @param id the id of the zamowienie to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/zamowienies/{id}")
    public ResponseEntity<Void> deleteZamowienie(@PathVariable Long id) {
        log.debug("REST request to delete Zamowienie : {}", id);
        zamowienieService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
