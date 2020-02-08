package com.vistula.magazyn.web.rest;

import com.vistula.magazyn.MagazynGrzewczyApp;
import com.vistula.magazyn.domain.ZamowienieWpis;
import com.vistula.magazyn.repository.ZamowienieWpisRepository;
import com.vistula.magazyn.service.ZamowienieWpisService;
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

import com.vistula.magazyn.domain.enumeration.StatusEnum;
import com.vistula.magazyn.domain.enumeration.StatusZamowieniaEnum;
/**
 * Integration tests for the {@link ZamowienieWpisResource} REST controller.
 */
@SpringBootTest(classes = MagazynGrzewczyApp.class)
public class ZamowienieWpisResourceIT {

    private static final Integer DEFAULT_ILOSC = 1;
    private static final Integer UPDATED_ILOSC = 2;

    private static final Double DEFAULT_CENA = 1D;
    private static final Double UPDATED_CENA = 2D;

    private static final StatusEnum DEFAULT_STATUS = StatusEnum.KOSZYK;
    private static final StatusEnum UPDATED_STATUS = StatusEnum.ZAMOWIENIE;

    private static final StatusZamowieniaEnum DEFAULT_STATUS_ZAMOWIENIA = StatusZamowieniaEnum.UTWORZONE;
    private static final StatusZamowieniaEnum UPDATED_STATUS_ZAMOWIENIA = StatusZamowieniaEnum.ZATWIERDZONE;

    @Autowired
    private ZamowienieWpisRepository zamowienieWpisRepository;

    @Autowired
    private ZamowienieWpisService zamowienieWpisService;

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

    private MockMvc restZamowienieWpisMockMvc;

    private ZamowienieWpis zamowienieWpis;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ZamowienieWpisResource zamowienieWpisResource = new ZamowienieWpisResource(zamowienieWpisService, null,null);
        this.restZamowienieWpisMockMvc = MockMvcBuilders.standaloneSetup(zamowienieWpisResource)
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
    public static ZamowienieWpis createEntity(EntityManager em) {
        ZamowienieWpis zamowienieWpis = new ZamowienieWpis()
            .ilosc(DEFAULT_ILOSC)
            .cena(DEFAULT_CENA)
            .status(DEFAULT_STATUS)
            .statusZamowienia(DEFAULT_STATUS_ZAMOWIENIA);
        return zamowienieWpis;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ZamowienieWpis createUpdatedEntity(EntityManager em) {
        ZamowienieWpis zamowienieWpis = new ZamowienieWpis()
            .ilosc(UPDATED_ILOSC)
            .cena(UPDATED_CENA)
            .status(UPDATED_STATUS)
            .statusZamowienia(UPDATED_STATUS_ZAMOWIENIA);
        return zamowienieWpis;
    }

    @BeforeEach
    public void initTest() {
        zamowienieWpis = createEntity(em);
    }

    @Test
    @Transactional
    public void createZamowienieWpis() throws Exception {
        int databaseSizeBeforeCreate = zamowienieWpisRepository.findAll().size();

        // Create the ZamowienieWpis
        restZamowienieWpisMockMvc.perform(post("/api/zamowienie-wpis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zamowienieWpis)))
            .andExpect(status().isCreated());

        // Validate the ZamowienieWpis in the database
        List<ZamowienieWpis> zamowienieWpisList = zamowienieWpisRepository.findAll();
        assertThat(zamowienieWpisList).hasSize(databaseSizeBeforeCreate + 1);
        ZamowienieWpis testZamowienieWpis = zamowienieWpisList.get(zamowienieWpisList.size() - 1);
        assertThat(testZamowienieWpis.getIlosc()).isEqualTo(DEFAULT_ILOSC);
        assertThat(testZamowienieWpis.getCena()).isEqualTo(DEFAULT_CENA);
        assertThat(testZamowienieWpis.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testZamowienieWpis.getStatusZamowienia()).isEqualTo(DEFAULT_STATUS_ZAMOWIENIA);
    }

    @Test
    @Transactional
    public void createZamowienieWpisWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = zamowienieWpisRepository.findAll().size();

        // Create the ZamowienieWpis with an existing ID
        zamowienieWpis.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restZamowienieWpisMockMvc.perform(post("/api/zamowienie-wpis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zamowienieWpis)))
            .andExpect(status().isBadRequest());

        // Validate the ZamowienieWpis in the database
        List<ZamowienieWpis> zamowienieWpisList = zamowienieWpisRepository.findAll();
        assertThat(zamowienieWpisList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllZamowienieWpis() throws Exception {
        // Initialize the database
        zamowienieWpisRepository.saveAndFlush(zamowienieWpis);

        // Get all the zamowienieWpisList
        restZamowienieWpisMockMvc.perform(get("/api/zamowienie-wpis?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(zamowienieWpis.getId().intValue())))
            .andExpect(jsonPath("$.[*].ilosc").value(hasItem(DEFAULT_ILOSC)))
            .andExpect(jsonPath("$.[*].cena").value(hasItem(DEFAULT_CENA.doubleValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].statusZamowienia").value(hasItem(DEFAULT_STATUS_ZAMOWIENIA.toString())));
    }
    
    @Test
    @Transactional
    public void getZamowienieWpis() throws Exception {
        // Initialize the database
        zamowienieWpisRepository.saveAndFlush(zamowienieWpis);

        // Get the zamowienieWpis
        restZamowienieWpisMockMvc.perform(get("/api/zamowienie-wpis/{id}", zamowienieWpis.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(zamowienieWpis.getId().intValue()))
            .andExpect(jsonPath("$.ilosc").value(DEFAULT_ILOSC))
            .andExpect(jsonPath("$.cena").value(DEFAULT_CENA.doubleValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.statusZamowienia").value(DEFAULT_STATUS_ZAMOWIENIA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingZamowienieWpis() throws Exception {
        // Get the zamowienieWpis
        restZamowienieWpisMockMvc.perform(get("/api/zamowienie-wpis/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateZamowienieWpis() throws Exception {
        // Initialize the database
        zamowienieWpisService.save(zamowienieWpis);

        int databaseSizeBeforeUpdate = zamowienieWpisRepository.findAll().size();

        // Update the zamowienieWpis
        ZamowienieWpis updatedZamowienieWpis = zamowienieWpisRepository.findById(zamowienieWpis.getId()).get();
        // Disconnect from session so that the updates on updatedZamowienieWpis are not directly saved in db
        em.detach(updatedZamowienieWpis);
        updatedZamowienieWpis
            .ilosc(UPDATED_ILOSC)
            .cena(UPDATED_CENA)
            .status(UPDATED_STATUS)
            .statusZamowienia(UPDATED_STATUS_ZAMOWIENIA);

        restZamowienieWpisMockMvc.perform(put("/api/zamowienie-wpis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedZamowienieWpis)))
            .andExpect(status().isOk());

        // Validate the ZamowienieWpis in the database
        List<ZamowienieWpis> zamowienieWpisList = zamowienieWpisRepository.findAll();
        assertThat(zamowienieWpisList).hasSize(databaseSizeBeforeUpdate);
        ZamowienieWpis testZamowienieWpis = zamowienieWpisList.get(zamowienieWpisList.size() - 1);
        assertThat(testZamowienieWpis.getIlosc()).isEqualTo(UPDATED_ILOSC);
        assertThat(testZamowienieWpis.getCena()).isEqualTo(UPDATED_CENA);
        assertThat(testZamowienieWpis.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testZamowienieWpis.getStatusZamowienia()).isEqualTo(UPDATED_STATUS_ZAMOWIENIA);
    }

    @Test
    @Transactional
    public void updateNonExistingZamowienieWpis() throws Exception {
        int databaseSizeBeforeUpdate = zamowienieWpisRepository.findAll().size();

        // Create the ZamowienieWpis

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restZamowienieWpisMockMvc.perform(put("/api/zamowienie-wpis")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(zamowienieWpis)))
            .andExpect(status().isBadRequest());

        // Validate the ZamowienieWpis in the database
        List<ZamowienieWpis> zamowienieWpisList = zamowienieWpisRepository.findAll();
        assertThat(zamowienieWpisList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteZamowienieWpis() throws Exception {
        // Initialize the database
        zamowienieWpisService.save(zamowienieWpis);

        int databaseSizeBeforeDelete = zamowienieWpisRepository.findAll().size();

        // Delete the zamowienieWpis
        restZamowienieWpisMockMvc.perform(delete("/api/zamowienie-wpis/{id}", zamowienieWpis.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ZamowienieWpis> zamowienieWpisList = zamowienieWpisRepository.findAll();
        assertThat(zamowienieWpisList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
