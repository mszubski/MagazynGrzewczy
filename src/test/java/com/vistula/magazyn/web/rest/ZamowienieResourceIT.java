package com.vistula.magazyn.web.rest;

import com.vistula.magazyn.MagazynGrzewczyApp;
import com.vistula.magazyn.domain.Zamowienie;
import com.vistula.magazyn.repository.ZamowienieRepository;
import com.vistula.magazyn.service.ZamowienieService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.vistula.magazyn.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.vistula.magazyn.domain.enumeration.StatusZamowienieEnum;
/**
 * Integration tests for the {@link ZamowienieResource} REST controller.
 */
@SpringBootTest(classes = MagazynGrzewczyApp.class)
public class ZamowienieResourceIT {

    private static final Double DEFAULT_CENA = 1D;
    private static final Double UPDATED_CENA = 2D;

    private static final StatusZamowienieEnum DEFAULT_STATUS = StatusZamowienieEnum.UTWORZONE;
    private static final StatusZamowienieEnum UPDATED_STATUS = StatusZamowienieEnum.ZAAKCEPTOWANE;

    private static final LocalDate DEFAULT_DATA_UTWORZENIA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_UTWORZENIA = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private ZamowienieRepository zamowienieRepository;

    @Autowired
    private ZamowienieService zamowienieService;

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

    private MockMvc restZamowienieMockMvc;

    private Zamowienie zamowienie;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZamowienieResource zamowienieResource = new ZamowienieResource(zamowienieService);
        this.restZamowienieMockMvc = MockMvcBuilders.standaloneSetup(zamowienieResource)
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
    public static Zamowienie createEntity(EntityManager em) {
        Zamowienie zamowienie = new Zamowienie()
            .cena(DEFAULT_CENA)
            .status(DEFAULT_STATUS)
            .dataUtworzenia(DEFAULT_DATA_UTWORZENIA);
        return zamowienie;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Zamowienie createUpdatedEntity(EntityManager em) {
        Zamowienie zamowienie = new Zamowienie()
            .cena(UPDATED_CENA)
            .status(UPDATED_STATUS)
            .dataUtworzenia(UPDATED_DATA_UTWORZENIA);
        return zamowienie;
    }

    @BeforeEach
    public void initTest() {
        zamowienie = createEntity(em);
    }

    @Test
    @Transactional
    public void createZamowienie() throws Exception {
        int databaseSizeBeforeCreate = zamowienieRepository.findAll().size();

        // Create the Zamowienie
        restZamowienieMockMvc.perform(post("/api/zamowienies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zamowienie)))
            .andExpect(status().isCreated());

        // Validate the Zamowienie in the database
        List<Zamowienie> zamowienieList = zamowienieRepository.findAll();
        assertThat(zamowienieList).hasSize(databaseSizeBeforeCreate + 1);
        Zamowienie testZamowienie = zamowienieList.get(zamowienieList.size() - 1);
        assertThat(testZamowienie.getCena()).isEqualTo(DEFAULT_CENA);
        assertThat(testZamowienie.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testZamowienie.getDataUtworzenia()).isEqualTo(DEFAULT_DATA_UTWORZENIA);
    }

    @Test
    @Transactional
    public void createZamowienieWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zamowienieRepository.findAll().size();

        // Create the Zamowienie with an existing ID
        zamowienie.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZamowienieMockMvc.perform(post("/api/zamowienies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zamowienie)))
            .andExpect(status().isBadRequest());

        // Validate the Zamowienie in the database
        List<Zamowienie> zamowienieList = zamowienieRepository.findAll();
        assertThat(zamowienieList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllZamowienies() throws Exception {
        // Initialize the database
        zamowienieRepository.saveAndFlush(zamowienie);

        // Get all the zamowienieList
        restZamowienieMockMvc.perform(get("/api/zamowienies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zamowienie.getId().intValue())))
            .andExpect(jsonPath("$.[*].cena").value(hasItem(DEFAULT_CENA.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].dataUtworzenia").value(hasItem(DEFAULT_DATA_UTWORZENIA.toString())));
    }
    
    @Test
    @Transactional
    public void getZamowienie() throws Exception {
        // Initialize the database
        zamowienieRepository.saveAndFlush(zamowienie);

        // Get the zamowienie
        restZamowienieMockMvc.perform(get("/api/zamowienies/{id}", zamowienie.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zamowienie.getId().intValue()))
            .andExpect(jsonPath("$.cena").value(DEFAULT_CENA.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.dataUtworzenia").value(DEFAULT_DATA_UTWORZENIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingZamowienie() throws Exception {
        // Get the zamowienie
        restZamowienieMockMvc.perform(get("/api/zamowienies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZamowienie() throws Exception {
        // Initialize the database
        zamowienieService.save(zamowienie);

        int databaseSizeBeforeUpdate = zamowienieRepository.findAll().size();

        // Update the zamowienie
        Zamowienie updatedZamowienie = zamowienieRepository.findById(zamowienie.getId()).get();
        // Disconnect from session so that the updates on updatedZamowienie are not directly saved in db
        em.detach(updatedZamowienie);
        updatedZamowienie
            .cena(UPDATED_CENA)
            .status(UPDATED_STATUS)
            .dataUtworzenia(UPDATED_DATA_UTWORZENIA);

        restZamowienieMockMvc.perform(put("/api/zamowienies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedZamowienie)))
            .andExpect(status().isOk());

        // Validate the Zamowienie in the database
        List<Zamowienie> zamowienieList = zamowienieRepository.findAll();
        assertThat(zamowienieList).hasSize(databaseSizeBeforeUpdate);
        Zamowienie testZamowienie = zamowienieList.get(zamowienieList.size() - 1);
        assertThat(testZamowienie.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testZamowienie.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testZamowienie.getDataUtworzenia()).isEqualTo(UPDATED_DATA_UTWORZENIA);
    }

    @Test
    @Transactional
    public void updateNonExistingZamowienie() throws Exception {
        int databaseSizeBeforeUpdate = zamowienieRepository.findAll().size();

        // Create the Zamowienie

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZamowienieMockMvc.perform(put("/api/zamowienies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zamowienie)))
            .andExpect(status().isBadRequest());

        // Validate the Zamowienie in the database
        List<Zamowienie> zamowienieList = zamowienieRepository.findAll();
        assertThat(zamowienieList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZamowienie() throws Exception {
        // Initialize the database
        zamowienieService.save(zamowienie);

        int databaseSizeBeforeDelete = zamowienieRepository.findAll().size();

        // Delete the zamowienie
        restZamowienieMockMvc.perform(delete("/api/zamowienies/{id}", zamowienie.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Zamowienie> zamowienieList = zamowienieRepository.findAll();
        assertThat(zamowienieList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
