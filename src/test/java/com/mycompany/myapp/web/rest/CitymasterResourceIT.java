package com.mycompany.myapp.web.rest;

import static com.mycompany.myapp.domain.CitymasterAsserts.*;
import static com.mycompany.myapp.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mycompany.myapp.IntegrationTest;
import com.mycompany.myapp.domain.Citymaster;
import com.mycompany.myapp.repository.CitymasterRepository;
import com.mycompany.myapp.service.dto.CitymasterDTO;
import com.mycompany.myapp.service.mapper.CitymasterMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link CitymasterResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CitymasterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CITY_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_STATUS = false;
    private static final Boolean UPDATED_STATUS = true;

    private static final String ENTITY_API_URL = "/api/citymasters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CitymasterRepository citymasterRepository;

    @Autowired
    private CitymasterMapper citymasterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCitymasterMockMvc;

    private Citymaster citymaster;

    private Citymaster insertedCitymaster;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Citymaster createEntity(EntityManager em) {
        Citymaster citymaster = new Citymaster().name(DEFAULT_NAME).cityCode(DEFAULT_CITY_CODE).status(DEFAULT_STATUS);
        return citymaster;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Citymaster createUpdatedEntity(EntityManager em) {
        Citymaster citymaster = new Citymaster().name(UPDATED_NAME).cityCode(UPDATED_CITY_CODE).status(UPDATED_STATUS);
        return citymaster;
    }

    @BeforeEach
    public void initTest() {
        citymaster = createEntity(em);
    }

    @AfterEach
    public void cleanup() {
        if (insertedCitymaster != null) {
            citymasterRepository.delete(insertedCitymaster);
            insertedCitymaster = null;
        }
    }

    @Test
    @Transactional
    void createCitymaster() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Citymaster
        CitymasterDTO citymasterDTO = citymasterMapper.toDto(citymaster);
        var returnedCitymasterDTO = om.readValue(
            restCitymasterMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citymasterDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CitymasterDTO.class
        );

        // Validate the Citymaster in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCitymaster = citymasterMapper.toEntity(returnedCitymasterDTO);
        assertCitymasterUpdatableFieldsEquals(returnedCitymaster, getPersistedCitymaster(returnedCitymaster));

        insertedCitymaster = returnedCitymaster;
    }

    @Test
    @Transactional
    void createCitymasterWithExistingId() throws Exception {
        // Create the Citymaster with an existing ID
        citymaster.setId(1L);
        CitymasterDTO citymasterDTO = citymasterMapper.toDto(citymaster);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCitymasterMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citymasterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Citymaster in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllCitymasters() throws Exception {
        // Initialize the database
        insertedCitymaster = citymasterRepository.saveAndFlush(citymaster);

        // Get all the citymasterList
        restCitymasterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(citymaster.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].cityCode").value(hasItem(DEFAULT_CITY_CODE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.booleanValue())));
    }

    @Test
    @Transactional
    void getCitymaster() throws Exception {
        // Initialize the database
        insertedCitymaster = citymasterRepository.saveAndFlush(citymaster);

        // Get the citymaster
        restCitymasterMockMvc
            .perform(get(ENTITY_API_URL_ID, citymaster.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(citymaster.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.cityCode").value(DEFAULT_CITY_CODE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.booleanValue()));
    }

    @Test
    @Transactional
    void getNonExistingCitymaster() throws Exception {
        // Get the citymaster
        restCitymasterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCitymaster() throws Exception {
        // Initialize the database
        insertedCitymaster = citymasterRepository.saveAndFlush(citymaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the citymaster
        Citymaster updatedCitymaster = citymasterRepository.findById(citymaster.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCitymaster are not directly saved in db
        em.detach(updatedCitymaster);
        updatedCitymaster.name(UPDATED_NAME).cityCode(UPDATED_CITY_CODE).status(UPDATED_STATUS);
        CitymasterDTO citymasterDTO = citymasterMapper.toDto(updatedCitymaster);

        restCitymasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citymasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(citymasterDTO))
            )
            .andExpect(status().isOk());

        // Validate the Citymaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCitymasterToMatchAllProperties(updatedCitymaster);
    }

    @Test
    @Transactional
    void putNonExistingCitymaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        citymaster.setId(longCount.incrementAndGet());

        // Create the Citymaster
        CitymasterDTO citymasterDTO = citymasterMapper.toDto(citymaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitymasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, citymasterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(citymasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Citymaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCitymaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        citymaster.setId(longCount.incrementAndGet());

        // Create the Citymaster
        CitymasterDTO citymasterDTO = citymasterMapper.toDto(citymaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitymasterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(citymasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Citymaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCitymaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        citymaster.setId(longCount.incrementAndGet());

        // Create the Citymaster
        CitymasterDTO citymasterDTO = citymasterMapper.toDto(citymaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitymasterMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(citymasterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Citymaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCitymasterWithPatch() throws Exception {
        // Initialize the database
        insertedCitymaster = citymasterRepository.saveAndFlush(citymaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the citymaster using partial update
        Citymaster partialUpdatedCitymaster = new Citymaster();
        partialUpdatedCitymaster.setId(citymaster.getId());

        partialUpdatedCitymaster.cityCode(UPDATED_CITY_CODE);

        restCitymasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitymaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCitymaster))
            )
            .andExpect(status().isOk());

        // Validate the Citymaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCitymasterUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedCitymaster, citymaster),
            getPersistedCitymaster(citymaster)
        );
    }

    @Test
    @Transactional
    void fullUpdateCitymasterWithPatch() throws Exception {
        // Initialize the database
        insertedCitymaster = citymasterRepository.saveAndFlush(citymaster);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the citymaster using partial update
        Citymaster partialUpdatedCitymaster = new Citymaster();
        partialUpdatedCitymaster.setId(citymaster.getId());

        partialUpdatedCitymaster.name(UPDATED_NAME).cityCode(UPDATED_CITY_CODE).status(UPDATED_STATUS);

        restCitymasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCitymaster.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCitymaster))
            )
            .andExpect(status().isOk());

        // Validate the Citymaster in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCitymasterUpdatableFieldsEquals(partialUpdatedCitymaster, getPersistedCitymaster(partialUpdatedCitymaster));
    }

    @Test
    @Transactional
    void patchNonExistingCitymaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        citymaster.setId(longCount.incrementAndGet());

        // Create the Citymaster
        CitymasterDTO citymasterDTO = citymasterMapper.toDto(citymaster);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCitymasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, citymasterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(citymasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Citymaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCitymaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        citymaster.setId(longCount.incrementAndGet());

        // Create the Citymaster
        CitymasterDTO citymasterDTO = citymasterMapper.toDto(citymaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitymasterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(citymasterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Citymaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCitymaster() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        citymaster.setId(longCount.incrementAndGet());

        // Create the Citymaster
        CitymasterDTO citymasterDTO = citymasterMapper.toDto(citymaster);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCitymasterMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(citymasterDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Citymaster in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCitymaster() throws Exception {
        // Initialize the database
        insertedCitymaster = citymasterRepository.saveAndFlush(citymaster);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the citymaster
        restCitymasterMockMvc
            .perform(delete(ENTITY_API_URL_ID, citymaster.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return citymasterRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Citymaster getPersistedCitymaster(Citymaster citymaster) {
        return citymasterRepository.findById(citymaster.getId()).orElseThrow();
    }

    protected void assertPersistedCitymasterToMatchAllProperties(Citymaster expectedCitymaster) {
        assertCitymasterAllPropertiesEquals(expectedCitymaster, getPersistedCitymaster(expectedCitymaster));
    }

    protected void assertPersistedCitymasterToMatchUpdatableProperties(Citymaster expectedCitymaster) {
        assertCitymasterAllUpdatablePropertiesEquals(expectedCitymaster, getPersistedCitymaster(expectedCitymaster));
    }
}
