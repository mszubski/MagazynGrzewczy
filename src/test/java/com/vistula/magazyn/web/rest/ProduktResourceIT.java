package com.vistula.magazyn.web.rest;

import com.vistula.magazyn.MagazynGrzewczyApp;
import com.vistula.magazyn.domain.Produkt;
import com.vistula.magazyn.repository.ProduktRepository;
import com.vistula.magazyn.service.ProduktService;
import com.vistula.magazyn.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static com.vistula.magazyn.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vistula.magazyn.domain.enumeration.StatusProdukt;
/**
 * Integration tests for the {@link ProduktResource} REST controller.
 */
@SpringBootTest(classes = MagazynGrzewczyApp.class)
public class ProduktResourceIT {

    private static final String DEFAULT_NAZWA = "AAAAAAAAAA";
    private static final String UPDATED_NAZWA = "BBBBBBBBBB";

    private static final Double DEFAULT_CENA = 1D;
    private static final Double UPDATED_CENA = 2D;

    private static final String DEFAULT_OPIS = "AAAAAAAAAA";
    private static final String UPDATED_OPIS = "BBBBBBBBBB";

    private static final StatusProdukt DEFAULT_STATUS = StatusProdukt.DOSTEPNY;
    private static final StatusProdukt UPDATED_STATUS = StatusProdukt.NIEDOSTEPNY;

    private static final String DEFAULT_ZDJECIE = "AAAAAAAAAA";
    private static final String UPDATED_ZDJECIE = "BBBBBBBBBB";

    private static final Integer DEFAULT_STAN = 1;
    private static final Integer UPDATED_STAN = 2;

    @Autowired
    private ProduktRepository produktRepository;

    @Autowired
    private ProduktService produktService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restProduktMockMvc;

    private Produkt produkt;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProduktResource produktResource = new ProduktResource(produktService);
        this.restProduktMockMvc = MockMvcBuilders.standaloneSetup(produktResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produkt createEntity(EntityManager em) {
        Produkt produkt = new Produkt()
            .nazwa(DEFAULT_NAZWA)
            .cena(DEFAULT_CENA)
            .opis(DEFAULT_OPIS)
            .status(DEFAULT_STATUS)
            .zdjecie(DEFAULT_ZDJECIE)
            .stan(DEFAULT_STAN);
        return produkt;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Produkt createUpdatedEntity(EntityManager em) {
        Produkt produkt = new Produkt()
            .nazwa(UPDATED_NAZWA)
            .cena(UPDATED_CENA)
            .opis(UPDATED_OPIS)
            .status(UPDATED_STATUS)
            .zdjecie(UPDATED_ZDJECIE)
            .stan(UPDATED_STAN);
        return produkt;
    }

    @BeforeEach
    public void initTest() {
        produkt = createEntity(em);
    }

    @Test
    @Transactional
    public void createProdukt() throws Exception {
        int databaseSizeBeforeCreate = produktRepository.findAll().size();

        // Create the Produkt
        restProduktMockMvc.perform(post("/api/produkts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produkt)))
            .andExpect(status().isCreated());

        // Validate the Produkt in the database
        List<Produkt> produktList = produktRepository.findAll();
        assertThat(produktList).hasSize(databaseSizeBeforeCreate + 1);
        Produkt testProdukt = produktList.get(produktList.size() - 1);
        assertThat(testProdukt.getNazwa()).isEqualTo(DEFAULT_NAZWA);
        assertThat(testProdukt.getCena()).isEqualTo(DEFAULT_CENA);
        assertThat(testProdukt.getOpis()).isEqualTo(DEFAULT_OPIS);
        assertThat(testProdukt.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProdukt.getZdjecie()).isEqualTo(DEFAULT_ZDJECIE);
        assertThat(testProdukt.getStan()).isEqualTo(DEFAULT_STAN);
    }

    @Test
    @Transactional
    public void createProduktWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = produktRepository.findAll().size();

        // Create the Produkt with an existing ID
        produkt.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProduktMockMvc.perform(post("/api/produkts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produkt)))
            .andExpect(status().isBadRequest());

        // Validate the Produkt in the database
        List<Produkt> produktList = produktRepository.findAll();
        assertThat(produktList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProdukts() throws Exception {
        // Initialize the database
        produktRepository.saveAndFlush(produkt);

        // Get all the produktList
        restProduktMockMvc.perform(get("/api/produkts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(produkt.getId().intValue())))
            .andExpect(jsonPath("$.[*].nazwa").value(hasItem(DEFAULT_NAZWA)))
            .andExpect(jsonPath("$.[*].cena").value(hasItem(DEFAULT_CENA.doubleValue())))
            .andExpect(jsonPath("$.[*].opis").value(hasItem(DEFAULT_OPIS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].zdjecie").value(hasItem(DEFAULT_ZDJECIE)))
            .andExpect(jsonPath("$.[*].stan").value(hasItem(DEFAULT_STAN)));
    }
    
    @Test
    @Transactional
    public void getProdukt() throws Exception {
        // Initialize the database
        produktRepository.saveAndFlush(produkt);

        // Get the produkt
        restProduktMockMvc.perform(get("/api/produkts/{id}", produkt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(produkt.getId().intValue()))
            .andExpect(jsonPath("$.nazwa").value(DEFAULT_NAZWA))
            .andExpect(jsonPath("$.cena").value(DEFAULT_CENA.doubleValue()))
            .andExpect(jsonPath("$.opis").value(DEFAULT_OPIS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.zdjecie").value(DEFAULT_ZDJECIE))
            .andExpect(jsonPath("$.stan").value(DEFAULT_STAN));
    }

    @Test
    @Transactional
    public void getNonExistingProdukt() throws Exception {
        // Get the produkt
        restProduktMockMvc.perform(get("/api/produkts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProdukt() throws Exception {
        // Initialize the database
        produktService.save(produkt);

        int databaseSizeBeforeUpdate = produktRepository.findAll().size();

        // Update the produkt
        Produkt updatedProdukt = produktRepository.findById(produkt.getId()).get();
        // Disconnect from session so that the updates on updatedProdukt are not directly saved in db
        em.detach(updatedProdukt);
        updatedProdukt
            .nazwa(UPDATED_NAZWA)
            .cena(UPDATED_CENA)
            .opis(UPDATED_OPIS)
            .status(UPDATED_STATUS)
            .zdjecie(UPDATED_ZDJECIE)
            .stan(UPDATED_STAN);

        restProduktMockMvc.perform(put("/api/produkts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProdukt)))
            .andExpect(status().isOk());

        // Validate the Produkt in the database
        List<Produkt> produktList = produktRepository.findAll();
        assertThat(produktList).hasSize(databaseSizeBeforeUpdate);
        Produkt testProdukt = produktList.get(produktList.size() - 1);
        assertThat(testProdukt.getNazwa()).isEqualTo(UPDATED_NAZWA);
        assertThat(testProdukt.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testProdukt.getOpis()).isEqualTo(UPDATED_OPIS);
        assertThat(testProdukt.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProdukt.getZdjecie()).isEqualTo(UPDATED_ZDJECIE);
        assertThat(testProdukt.getStan()).isEqualTo(UPDATED_STAN);
    }

    @Test
    @Transactional
    public void updateNonExistingProdukt() throws Exception {
        int databaseSizeBeforeUpdate = produktRepository.findAll().size();

        // Create the Produkt

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProduktMockMvc.perform(put("/api/produkts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(produkt)))
            .andExpect(status().isBadRequest());

        // Validate the Produkt in the database
        List<Produkt> produktList = produktRepository.findAll();
        assertThat(produktList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProdukt() throws Exception {
        // Initialize the database
        produktService.save(produkt);

        int databaseSizeBeforeDelete = produktRepository.findAll().size();

        // Delete the produkt
        restProduktMockMvc.perform(delete("/api/produkts/{id}", produkt.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Produkt> produktList = produktRepository.findAll();
        assertThat(produktList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
