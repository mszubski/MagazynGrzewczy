package com.vistula.magazyn.web.rest;

import com.vistula.magazyn.domain.User;
import com.vistula.magazyn.domain.Zamowienie;
import com.vistula.magazyn.domain.ZamowienieWpis;
import com.vistula.magazyn.domain.enumeration.StatusEnum;
import com.vistula.magazyn.domain.enumeration.StatusZamowieniaEnum;
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
import java.util.ArrayList;
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
    private final ZamowienieWpisResource zamowienieWpisResource;
    private UserService userService;

    public ZamowienieResource(ZamowienieService zamowienieService,
                              ZamowienieWpisRepository zamowienieWpisRepository,
                              ZamowienieWpisResource zamowienieWpisResource,
                              UserService userService) {
        this.zamowienieService = zamowienieService;
        this.zamowienieWpisRepository = zamowienieWpisRepository;
        this.zamowienieWpisResource = zamowienieWpisResource;
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
        //ustawiam użytkownika, który robi wpis
        Optional<User> user = userService.getUserWithAuthoritiesByLogin(principal.getName());
        if(user.isPresent()){
            zamowienie.setUser(user.get());
        }
        //inicjalna wartosc dla ceny
        double cenaCalkowita = 0.0;
        //lista produków z koszyka dla usera po statusie zamowienia i loginie
        List<ZamowienieWpis> zamowienieWpisList = zamowienieWpisRepository.findAllByUserAndStatusZamowienia(user, StatusZamowieniaEnum.UTWORZONE);

        //iteracja po liście, aby uzyskać cenę całkowitą z koszyka
        for (ZamowienieWpis zamowienieWpis: zamowienieWpisList){
            ZamowienieWpis zamowienieWpisItem = new ZamowienieWpis();
            zamowienieWpisItem.setCena(zamowienieWpis.getCena());
            cenaCalkowita = cenaCalkowita + zamowienieWpis.getCena();
            log.info("cenaCalkowita.toString()");
            log.info(Double.toString(cenaCalkowita));
        }
        //ustawienie ceny dla zamowienia
        zamowienie.setCena(cenaCalkowita);

        //zapis wpisu dla Zamowienie
        Zamowienie result = zamowienieService.save(zamowienie);
        //wyciagnięcie Id z wpisu Zamowienie
        Long idZamowienia = zamowienie.getId();

        //stworzenie listy po zmianach dla ZamowienieWpis - Koszyk
        List<ZamowienieWpis> zamowienieWpisListUpdate = new ArrayList<>();

        //dla każdego z wpisów z koszyka iteracja
        for (ZamowienieWpis zamowienieWpis: zamowienieWpisList){
            ZamowienieWpis zamowienieWpisItem = new ZamowienieWpis();

            zamowienieWpisItem.setUser(zamowienieWpis.getUser());
            zamowienieWpisItem.setCena(zamowienieWpis.getCena());
            zamowienieWpisItem.setIlosc(zamowienieWpis.getIlosc());
            zamowienieWpisItem.setProdukt(zamowienieWpis.getProdukt());
            zamowienieWpisItem.setStatus(StatusEnum.ZAMOWIENIE);
            zamowienieWpisItem.setStatusZamowienia(StatusZamowieniaEnum.ZATWIERDZONE);
            zamowienieWpisItem.setZamowienieId(idZamowienia);
            //dodanie do listy każdego elementu
            zamowienieWpisListUpdate.add(zamowienieWpisItem);

            //tworze nowy wpis dla każdego z elementow już update-owanej listy
            zamowienieWpisResource.createZamowienieWpis(zamowienieWpisItem);
            //usuwam stary wpis
            zamowienieWpisResource.deleteZamowienieWpis(zamowienieWpis.getId());
        }
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
