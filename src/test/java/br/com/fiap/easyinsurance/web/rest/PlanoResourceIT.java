package br.com.fiap.easyinsurance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.fiap.easyinsurance.IntegrationTest;
import br.com.fiap.easyinsurance.domain.Plano;
import br.com.fiap.easyinsurance.repository.PlanoRepository;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link PlanoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanoResourceIT {

    private static final String DEFAULT_NOME_PLANO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_PLANO = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/planos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PlanoRepository planoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoMockMvc;

    private Plano plano;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plano createEntity(EntityManager em) {
        Plano plano = new Plano().nomePlano(DEFAULT_NOME_PLANO);
        return plano;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Plano createUpdatedEntity(EntityManager em) {
        Plano plano = new Plano().nomePlano(UPDATED_NOME_PLANO);
        return plano;
    }

    @BeforeEach
    public void initTest() {
        plano = createEntity(em);
    }

    @Test
    @Transactional
    void createPlano() throws Exception {
        int databaseSizeBeforeCreate = planoRepository.findAll().size();
        // Create the Plano
        restPlanoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plano)))
            .andExpect(status().isCreated());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeCreate + 1);
        Plano testPlano = planoList.get(planoList.size() - 1);
        assertThat(testPlano.getNomePlano()).isEqualTo(DEFAULT_NOME_PLANO);
    }

    @Test
    @Transactional
    void createPlanoWithExistingId() throws Exception {
        // Create the Plano with an existing ID
        plano.setId(1L);

        int databaseSizeBeforeCreate = planoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plano)))
            .andExpect(status().isBadRequest());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomePlanoIsRequired() throws Exception {
        int databaseSizeBeforeTest = planoRepository.findAll().size();
        // set the field null
        plano.setNomePlano(null);

        // Create the Plano, which fails.

        restPlanoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plano)))
            .andExpect(status().isBadRequest());

        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlanos() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get all the planoList
        restPlanoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plano.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomePlano").value(hasItem(DEFAULT_NOME_PLANO)));
    }

    @Test
    @Transactional
    void getPlano() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        // Get the plano
        restPlanoMockMvc
            .perform(get(ENTITY_API_URL_ID, plano.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plano.getId().intValue()))
            .andExpect(jsonPath("$.nomePlano").value(DEFAULT_NOME_PLANO));
    }

    @Test
    @Transactional
    void getNonExistingPlano() throws Exception {
        // Get the plano
        restPlanoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPlano() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        int databaseSizeBeforeUpdate = planoRepository.findAll().size();

        // Update the plano
        Plano updatedPlano = planoRepository.findById(plano.getId()).get();
        // Disconnect from session so that the updates on updatedPlano are not directly saved in db
        em.detach(updatedPlano);
        updatedPlano.nomePlano(UPDATED_NOME_PLANO);

        restPlanoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlano.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedPlano))
            )
            .andExpect(status().isOk());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeUpdate);
        Plano testPlano = planoList.get(planoList.size() - 1);
        assertThat(testPlano.getNomePlano()).isEqualTo(UPDATED_NOME_PLANO);
    }

    @Test
    @Transactional
    void putNonExistingPlano() throws Exception {
        int databaseSizeBeforeUpdate = planoRepository.findAll().size();
        plano.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plano.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plano))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlano() throws Exception {
        int databaseSizeBeforeUpdate = planoRepository.findAll().size();
        plano.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(plano))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlano() throws Exception {
        int databaseSizeBeforeUpdate = planoRepository.findAll().size();
        plano.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(plano)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanoWithPatch() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        int databaseSizeBeforeUpdate = planoRepository.findAll().size();

        // Update the plano using partial update
        Plano partialUpdatedPlano = new Plano();
        partialUpdatedPlano.setId(plano.getId());

        partialUpdatedPlano.nomePlano(UPDATED_NOME_PLANO);

        restPlanoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlano.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlano))
            )
            .andExpect(status().isOk());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeUpdate);
        Plano testPlano = planoList.get(planoList.size() - 1);
        assertThat(testPlano.getNomePlano()).isEqualTo(UPDATED_NOME_PLANO);
    }

    @Test
    @Transactional
    void fullUpdatePlanoWithPatch() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        int databaseSizeBeforeUpdate = planoRepository.findAll().size();

        // Update the plano using partial update
        Plano partialUpdatedPlano = new Plano();
        partialUpdatedPlano.setId(plano.getId());

        partialUpdatedPlano.nomePlano(UPDATED_NOME_PLANO);

        restPlanoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlano.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPlano))
            )
            .andExpect(status().isOk());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeUpdate);
        Plano testPlano = planoList.get(planoList.size() - 1);
        assertThat(testPlano.getNomePlano()).isEqualTo(UPDATED_NOME_PLANO);
    }

    @Test
    @Transactional
    void patchNonExistingPlano() throws Exception {
        int databaseSizeBeforeUpdate = planoRepository.findAll().size();
        plano.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plano.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plano))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlano() throws Exception {
        int databaseSizeBeforeUpdate = planoRepository.findAll().size();
        plano.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(plano))
            )
            .andExpect(status().isBadRequest());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlano() throws Exception {
        int databaseSizeBeforeUpdate = planoRepository.findAll().size();
        plano.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(plano)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Plano in the database
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlano() throws Exception {
        // Initialize the database
        planoRepository.saveAndFlush(plano);

        int databaseSizeBeforeDelete = planoRepository.findAll().size();

        // Delete the plano
        restPlanoMockMvc
            .perform(delete(ENTITY_API_URL_ID, plano.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Plano> planoList = planoRepository.findAll();
        assertThat(planoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
