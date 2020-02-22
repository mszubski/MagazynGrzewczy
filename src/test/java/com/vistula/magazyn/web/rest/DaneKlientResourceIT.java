package com.vistula.magazyn.web.rest;

import com.vistula.magazyn.MagazynGrzewczyApp;
import com.vistula.magazyn.domain.DaneKlient;
import com.vistula.magazyn.repository.DaneKlientRepository;
import com.vistula.magazyn.service.DaneKlientService;
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

/**
 * Integration tests for the {@link DaneKlientResource} REST controller.
 */
@SpringBootTest(classes = MagazynGrzewczyApp.class)
public class DaneKlientResourceIT {

    private static final String DEFAULT_IMIE = "AAAAAAAAAA";
    private static final String UPDATED_IMIE = "BBBBBBBBBB";

    private static final String DEFAULT_NAZWISKO = "AAAAAAAAAA";
    private static final String UPDATED_NAZWISKO = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMER_TELEFONU = 1;
    private static final Integer UPDATED_NUMER_TELEFONU = 2;

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_FIRMA = "AAAAAAAAAA";
    private static final String UPDATED_FIRMA = "BBBBBBBBBB";

    private static final Integer DEFAULT_NIP = 1;
    private static final Integer UPDATED_NIP = 2;

    private static final String DEFAULT_ULICA = "AAAAAAAAAA";
    private static final String UPDATED_ULICA = "BBBBBBBBBB";

    private static final String DEFAULT_MIEJSCOWOSC = "AAAAAAAAAA";
    private static final String UPDATED_MIEJSCOWOSC = "BBBBBBBBBB";

    private static final String DEFAULT_KOD_POCZTOWY = "AAAAAAAAAA";
    private static final String UPDATED_KOD_POCZTOWY = "BBBBBBBBBB";

    private static final String DEFAULT_KRAJ = "AAAAAAAAAA";
    private static final String UPDATED_KRAJ = "BBBBBBBBBB";

    @Autowired
    private DaneKlientRepository daneKlientRepository;

    @Autowired
    private DaneKlientService daneKlientService;

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

    private MockMvc restDaneKlientMockMvc;

    private DaneKlient daneKlient;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DaneKlientResource daneKlientResource = new DaneKlientResource(daneKlientService);
        this.restDaneKlientMockMvc = MockMvcBuilders.standaloneSetup(daneKlientResource)
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
    public static DaneKlient createEntity(EntityManager em) {
        DaneKlient daneKlient = new DaneKlient()
            .imie(DEFAULT_IMIE)
            .nazwisko(DEFAULT_NAZWISKO)
            .numerTelefonu(DEFAULT_NUMER_TELEFONU)
            .email(DEFAULT_EMAIL)
            .firma(DEFAULT_FIRMA)
            .nip(DEFAULT_NIP)
            .ulica(DEFAULT_ULICA)
            .miejscowosc(DEFAULT_MIEJSCOWOSC)
            .kodPocztowy(DEFAULT_KOD_POCZTOWY)
            .kraj(DEFAULT_KRAJ);
        return daneKlient;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DaneKlient createUpdatedEntity(EntityManager em) {
        DaneKlient daneKlient = new DaneKlient()
            .imie(UPDATED_IMIE)
            .nazwisko(UPDATED_NAZWISKO)
            .numerTelefonu(UPDATED_NUMER_TELEFONU)
            .email(UPDATED_EMAIL)
            .firma(UPDATED_FIRMA)
            .nip(UPDATED_NIP)
            .ulica(UPDATED_ULICA)
            .miejscowosc(UPDATED_MIEJSCOWOSC)
            .kodPocztowy(UPDATED_KOD_POCZTOWY)
            .kraj(UPDATED_KRAJ);
        return daneKlient;
    }

    @BeforeEach
    public void initTest() {
        daneKlient = createEntity(em);
    }

    @Test
    @Transactional
    public void createDaneKlient() throws Exception {
        int databaseSizeBeforeCreate = daneKlientRepository.findAll().size();

        // Create the DaneKlient
        restDaneKlientMockMvc.perform(post("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daneKlient)))
            .andExpect(status().isCreated());

        // Validate the DaneKlient in the database
        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeCreate + 1);
        DaneKlient testDaneKlient = daneKlientList.get(daneKlientList.size() - 1);
        assertThat(testDaneKlient.getImie()).isEqualTo(DEFAULT_IMIE);
        assertThat(testDaneKlient.getNazwisko()).isEqualTo(DEFAULT_NAZWISKO);
        assertThat(testDaneKlient.getNumerTelefonu()).isEqualTo(DEFAULT_NUMER_TELEFONU);
        assertThat(testDaneKlient.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testDaneKlient.getFirma()).isEqualTo(DEFAULT_FIRMA);
        assertThat(testDaneKlient.getNip()).isEqualTo(DEFAULT_NIP);
        assertThat(testDaneKlient.getUlica()).isEqualTo(DEFAULT_ULICA);
        assertThat(testDaneKlient.getMiejscowosc()).isEqualTo(DEFAULT_MIEJSCOWOSC);
        assertThat(testDaneKlient.getKodPocztowy()).isEqualTo(DEFAULT_KOD_POCZTOWY);
        assertThat(testDaneKlient.getKraj()).isEqualTo(DEFAULT_KRAJ);
    }

    @Test
    @Transactional
    public void createDaneKlientWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = daneKlientRepository.findAll().size();

        // Create the DaneKlient with an existing ID
        daneKlient.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDaneKlientMockMvc.perform(post("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daneKlient)))
            .andExpect(status().isBadRequest());

        // Validate the DaneKlient in the database
        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkImieIsRequired() throws Exception {
        int databaseSizeBeforeTest = daneKlientRepository.findAll().size();
        // set the field null
        daneKlient.setImie(null);

        // Create the DaneKlient, which fails.

        restDaneKlientMockMvc.perform(post("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daneKlient)))
            .andExpect(status().isBadRequest());

        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNazwiskoIsRequired() throws Exception {
        int databaseSizeBeforeTest = daneKlientRepository.findAll().size();
        // set the field null
        daneKlient.setNazwisko(null);

        // Create the DaneKlient, which fails.

        restDaneKlientMockMvc.perform(post("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daneKlient)))
            .andExpect(status().isBadRequest());

        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumerTelefonuIsRequired() throws Exception {
        int databaseSizeBeforeTest = daneKlientRepository.findAll().size();
        // set the field null
        daneKlient.setNumerTelefonu(null);

        // Create the DaneKlient, which fails.

        restDaneKlientMockMvc.perform(post("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daneKlient)))
            .andExpect(status().isBadRequest());

        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEmailIsRequired() throws Exception {
        int databaseSizeBeforeTest = daneKlientRepository.findAll().size();
        // set the field null
        daneKlient.setEmail(null);

        // Create the DaneKlient, which fails.

        restDaneKlientMockMvc.perform(post("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daneKlient)))
            .andExpect(status().isBadRequest());

        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFirmaIsRequired() throws Exception {
        int databaseSizeBeforeTest = daneKlientRepository.findAll().size();
        // set the field null
        daneKlient.setFirma(null);

        // Create the DaneKlient, which fails.

        restDaneKlientMockMvc.perform(post("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daneKlient)))
            .andExpect(status().isBadRequest());

        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNipIsRequired() throws Exception {
        int databaseSizeBeforeTest = daneKlientRepository.findAll().size();
        // set the field null
        daneKlient.setNip(null);

        // Create the DaneKlient, which fails.

        restDaneKlientMockMvc.perform(post("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daneKlient)))
            .andExpect(status().isBadRequest());

        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkUlicaIsRequired() throws Exception {
        int databaseSizeBeforeTest = daneKlientRepository.findAll().size();
        // set the field null
        daneKlient.setUlica(null);

        // Create the DaneKlient, which fails.

        restDaneKlientMockMvc.perform(post("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daneKlient)))
            .andExpect(status().isBadRequest());

        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMiejscowoscIsRequired() throws Exception {
        int databaseSizeBeforeTest = daneKlientRepository.findAll().size();
        // set the field null
        daneKlient.setMiejscowosc(null);

        // Create the DaneKlient, which fails.

        restDaneKlientMockMvc.perform(post("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daneKlient)))
            .andExpect(status().isBadRequest());

        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKodPocztowyIsRequired() throws Exception {
        int databaseSizeBeforeTest = daneKlientRepository.findAll().size();
        // set the field null
        daneKlient.setKodPocztowy(null);

        // Create the DaneKlient, which fails.

        restDaneKlientMockMvc.perform(post("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daneKlient)))
            .andExpect(status().isBadRequest());

        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkKrajIsRequired() throws Exception {
        int databaseSizeBeforeTest = daneKlientRepository.findAll().size();
        // set the field null
        daneKlient.setKraj(null);

        // Create the DaneKlient, which fails.

        restDaneKlientMockMvc.perform(post("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daneKlient)))
            .andExpect(status().isBadRequest());

        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDaneKlients() throws Exception {
        // Initialize the database
        daneKlientRepository.saveAndFlush(daneKlient);

        // Get all the daneKlientList
        restDaneKlientMockMvc.perform(get("/api/dane-klients?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(daneKlient.getId().intValue())))
            .andExpect(jsonPath("$.[*].imie").value(hasItem(DEFAULT_IMIE)))
            .andExpect(jsonPath("$.[*].nazwisko").value(hasItem(DEFAULT_NAZWISKO)))
            .andExpect(jsonPath("$.[*].numerTelefonu").value(hasItem(DEFAULT_NUMER_TELEFONU)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].firma").value(hasItem(DEFAULT_FIRMA)))
            .andExpect(jsonPath("$.[*].nip").value(hasItem(DEFAULT_NIP)))
            .andExpect(jsonPath("$.[*].ulica").value(hasItem(DEFAULT_ULICA)))
            .andExpect(jsonPath("$.[*].miejscowosc").value(hasItem(DEFAULT_MIEJSCOWOSC)))
            .andExpect(jsonPath("$.[*].kodPocztowy").value(hasItem(DEFAULT_KOD_POCZTOWY)))
            .andExpect(jsonPath("$.[*].kraj").value(hasItem(DEFAULT_KRAJ)));
    }
    
    @Test
    @Transactional
    public void getDaneKlient() throws Exception {
        // Initialize the database
        daneKlientRepository.saveAndFlush(daneKlient);

        // Get the daneKlient
        restDaneKlientMockMvc.perform(get("/api/dane-klients/{id}", daneKlient.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(daneKlient.getId().intValue()))
            .andExpect(jsonPath("$.imie").value(DEFAULT_IMIE))
            .andExpect(jsonPath("$.nazwisko").value(DEFAULT_NAZWISKO))
            .andExpect(jsonPath("$.numerTelefonu").value(DEFAULT_NUMER_TELEFONU))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.firma").value(DEFAULT_FIRMA))
            .andExpect(jsonPath("$.nip").value(DEFAULT_NIP))
            .andExpect(jsonPath("$.ulica").value(DEFAULT_ULICA))
            .andExpect(jsonPath("$.miejscowosc").value(DEFAULT_MIEJSCOWOSC))
            .andExpect(jsonPath("$.kodPocztowy").value(DEFAULT_KOD_POCZTOWY))
            .andExpect(jsonPath("$.kraj").value(DEFAULT_KRAJ));
    }

    @Test
    @Transactional
    public void getNonExistingDaneKlient() throws Exception {
        // Get the daneKlient
        restDaneKlientMockMvc.perform(get("/api/dane-klients/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDaneKlient() throws Exception {
        // Initialize the database
        daneKlientService.save(daneKlient);

        int databaseSizeBeforeUpdate = daneKlientRepository.findAll().size();

        // Update the daneKlient
        DaneKlient updatedDaneKlient = daneKlientRepository.findById(daneKlient.getId()).get();
        // Disconnect from session so that the updates on updatedDaneKlient are not directly saved in db
        em.detach(updatedDaneKlient);
        updatedDaneKlient
            .imie(UPDATED_IMIE)
            .nazwisko(UPDATED_NAZWISKO)
            .numerTelefonu(UPDATED_NUMER_TELEFONU)
            .email(UPDATED_EMAIL)
            .firma(UPDATED_FIRMA)
            .nip(UPDATED_NIP)
            .ulica(UPDATED_ULICA)
            .miejscowosc(UPDATED_MIEJSCOWOSC)
            .kodPocztowy(UPDATED_KOD_POCZTOWY)
            .kraj(UPDATED_KRAJ);

        restDaneKlientMockMvc.perform(put("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDaneKlient)))
            .andExpect(status().isOk());

        // Validate the DaneKlient in the database
        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeUpdate);
        DaneKlient testDaneKlient = daneKlientList.get(daneKlientList.size() - 1);
        assertThat(testDaneKlient.getImie()).isEqualTo(UPDATED_IMIE);
        assertThat(testDaneKlient.getNazwisko()).isEqualTo(UPDATED_NAZWISKO);
        assertThat(testDaneKlient.getNumerTelefonu()).isEqualTo(UPDATED_NUMER_TELEFONU);
        assertThat(testDaneKlient.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testDaneKlient.getFirma()).isEqualTo(UPDATED_FIRMA);
        assertThat(testDaneKlient.getNip()).isEqualTo(UPDATED_NIP);
        assertThat(testDaneKlient.getUlica()).isEqualTo(UPDATED_ULICA);
        assertThat(testDaneKlient.getMiejscowosc()).isEqualTo(UPDATED_MIEJSCOWOSC);
        assertThat(testDaneKlient.getKodPocztowy()).isEqualTo(UPDATED_KOD_POCZTOWY);
        assertThat(testDaneKlient.getKraj()).isEqualTo(UPDATED_KRAJ);
    }

    @Test
    @Transactional
    public void updateNonExistingDaneKlient() throws Exception {
        int databaseSizeBeforeUpdate = daneKlientRepository.findAll().size();

        // Create the DaneKlient

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDaneKlientMockMvc.perform(put("/api/dane-klients")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daneKlient)))
            .andExpect(status().isBadRequest());

        // Validate the DaneKlient in the database
        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDaneKlient() throws Exception {
        // Initialize the database
        daneKlientService.save(daneKlient);

        int databaseSizeBeforeDelete = daneKlientRepository.findAll().size();

        // Delete the daneKlient
        restDaneKlientMockMvc.perform(delete("/api/dane-klients/{id}", daneKlient.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DaneKlient> daneKlientList = daneKlientRepository.findAll();
        assertThat(daneKlientList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
