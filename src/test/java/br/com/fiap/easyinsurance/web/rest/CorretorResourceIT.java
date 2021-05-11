package br.com.fiap.easyinsurance.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.fiap.easyinsurance.IntegrationTest;
import br.com.fiap.easyinsurance.domain.Corretor;
import br.com.fiap.easyinsurance.repository.CorretorRepository;
import java.time.LocalDate;
import java.time.ZoneId;
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
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link CorretorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class CorretorResourceIT {

    private static final String DEFAULT_NOME_CORRETOR = "AAAAAAAAAA";
    private static final String UPDATED_NOME_CORRETOR = "BBBBBBBBBB";

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATA_NASCIMENTO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATA_NASCIMENTO = LocalDate.now(ZoneId.systemDefault());

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_TELEFONE = "AAAAAAAAAA";
    private static final String UPDATED_TELEFONE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/corretors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private CorretorRepository corretorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCorretorMockMvc;

    private Corretor corretor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corretor createEntity(EntityManager em) {
        Corretor corretor = new Corretor()
            .nomeCorretor(DEFAULT_NOME_CORRETOR)
            .usuario(DEFAULT_USUARIO)
            .dataNascimento(DEFAULT_DATA_NASCIMENTO)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .telefone(DEFAULT_TELEFONE);
        return corretor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Corretor createUpdatedEntity(EntityManager em) {
        Corretor corretor = new Corretor()
            .nomeCorretor(UPDATED_NOME_CORRETOR)
            .usuario(UPDATED_USUARIO)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .telefone(UPDATED_TELEFONE);
        return corretor;
    }

    @BeforeEach
    public void initTest() {
        corretor = createEntity(em);
    }

    @Test
    @Transactional
    void createCorretor() throws Exception {
        int databaseSizeBeforeCreate = corretorRepository.findAll().size();
        // Create the Corretor
        restCorretorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(corretor)))
            .andExpect(status().isCreated());

        // Validate the Corretor in the database
        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeCreate + 1);
        Corretor testCorretor = corretorList.get(corretorList.size() - 1);
        assertThat(testCorretor.getNomeCorretor()).isEqualTo(DEFAULT_NOME_CORRETOR);
        assertThat(testCorretor.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testCorretor.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testCorretor.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testCorretor.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testCorretor.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
    }

    @Test
    @Transactional
    void createCorretorWithExistingId() throws Exception {
        // Create the Corretor with an existing ID
        corretor.setId(1L);

        int databaseSizeBeforeCreate = corretorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCorretorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(corretor)))
            .andExpect(status().isBadRequest());

        // Validate the Corretor in the database
        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeCorretorIsRequired() throws Exception {
        int databaseSizeBeforeTest = corretorRepository.findAll().size();
        // set the field null
        corretor.setNomeCorretor(null);

        // Create the Corretor, which fails.

        restCorretorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(corretor)))
            .andExpect(status().isBadRequest());

        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkUsuarioIsRequired() throws Exception {
        int databaseSizeBeforeTest = corretorRepository.findAll().size();
        // set the field null
        corretor.setUsuario(null);

        // Create the Corretor, which fails.

        restCorretorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(corretor)))
            .andExpect(status().isBadRequest());

        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCorretors() throws Exception {
        // Initialize the database
        corretorRepository.saveAndFlush(corretor);

        // Get all the corretorList
        restCorretorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(corretor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeCorretor").value(hasItem(DEFAULT_NOME_CORRETOR)))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO)))
            .andExpect(jsonPath("$.[*].dataNascimento").value(hasItem(DEFAULT_DATA_NASCIMENTO.toString())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE)));
    }

    @Test
    @Transactional
    void getCorretor() throws Exception {
        // Initialize the database
        corretorRepository.saveAndFlush(corretor);

        // Get the corretor
        restCorretorMockMvc
            .perform(get(ENTITY_API_URL_ID, corretor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(corretor.getId().intValue()))
            .andExpect(jsonPath("$.nomeCorretor").value(DEFAULT_NOME_CORRETOR))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO))
            .andExpect(jsonPath("$.dataNascimento").value(DEFAULT_DATA_NASCIMENTO.toString()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE));
    }

    @Test
    @Transactional
    void getNonExistingCorretor() throws Exception {
        // Get the corretor
        restCorretorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewCorretor() throws Exception {
        // Initialize the database
        corretorRepository.saveAndFlush(corretor);

        int databaseSizeBeforeUpdate = corretorRepository.findAll().size();

        // Update the corretor
        Corretor updatedCorretor = corretorRepository.findById(corretor.getId()).get();
        // Disconnect from session so that the updates on updatedCorretor are not directly saved in db
        em.detach(updatedCorretor);
        updatedCorretor
            .nomeCorretor(UPDATED_NOME_CORRETOR)
            .usuario(UPDATED_USUARIO)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .telefone(UPDATED_TELEFONE);

        restCorretorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedCorretor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedCorretor))
            )
            .andExpect(status().isOk());

        // Validate the Corretor in the database
        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeUpdate);
        Corretor testCorretor = corretorList.get(corretorList.size() - 1);
        assertThat(testCorretor.getNomeCorretor()).isEqualTo(UPDATED_NOME_CORRETOR);
        assertThat(testCorretor.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testCorretor.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testCorretor.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testCorretor.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testCorretor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void putNonExistingCorretor() throws Exception {
        int databaseSizeBeforeUpdate = corretorRepository.findAll().size();
        corretor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorretorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, corretor.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(corretor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Corretor in the database
        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCorretor() throws Exception {
        int databaseSizeBeforeUpdate = corretorRepository.findAll().size();
        corretor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorretorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(corretor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Corretor in the database
        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCorretor() throws Exception {
        int databaseSizeBeforeUpdate = corretorRepository.findAll().size();
        corretor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorretorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(corretor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Corretor in the database
        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCorretorWithPatch() throws Exception {
        // Initialize the database
        corretorRepository.saveAndFlush(corretor);

        int databaseSizeBeforeUpdate = corretorRepository.findAll().size();

        // Update the corretor using partial update
        Corretor partialUpdatedCorretor = new Corretor();
        partialUpdatedCorretor.setId(corretor.getId());

        partialUpdatedCorretor.nomeCorretor(UPDATED_NOME_CORRETOR).telefone(UPDATED_TELEFONE);

        restCorretorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCorretor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCorretor))
            )
            .andExpect(status().isOk());

        // Validate the Corretor in the database
        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeUpdate);
        Corretor testCorretor = corretorList.get(corretorList.size() - 1);
        assertThat(testCorretor.getNomeCorretor()).isEqualTo(UPDATED_NOME_CORRETOR);
        assertThat(testCorretor.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testCorretor.getDataNascimento()).isEqualTo(DEFAULT_DATA_NASCIMENTO);
        assertThat(testCorretor.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testCorretor.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testCorretor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void fullUpdateCorretorWithPatch() throws Exception {
        // Initialize the database
        corretorRepository.saveAndFlush(corretor);

        int databaseSizeBeforeUpdate = corretorRepository.findAll().size();

        // Update the corretor using partial update
        Corretor partialUpdatedCorretor = new Corretor();
        partialUpdatedCorretor.setId(corretor.getId());

        partialUpdatedCorretor
            .nomeCorretor(UPDATED_NOME_CORRETOR)
            .usuario(UPDATED_USUARIO)
            .dataNascimento(UPDATED_DATA_NASCIMENTO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .telefone(UPDATED_TELEFONE);

        restCorretorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCorretor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedCorretor))
            )
            .andExpect(status().isOk());

        // Validate the Corretor in the database
        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeUpdate);
        Corretor testCorretor = corretorList.get(corretorList.size() - 1);
        assertThat(testCorretor.getNomeCorretor()).isEqualTo(UPDATED_NOME_CORRETOR);
        assertThat(testCorretor.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testCorretor.getDataNascimento()).isEqualTo(UPDATED_DATA_NASCIMENTO);
        assertThat(testCorretor.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testCorretor.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testCorretor.getTelefone()).isEqualTo(UPDATED_TELEFONE);
    }

    @Test
    @Transactional
    void patchNonExistingCorretor() throws Exception {
        int databaseSizeBeforeUpdate = corretorRepository.findAll().size();
        corretor.setId(count.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCorretorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, corretor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(corretor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Corretor in the database
        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCorretor() throws Exception {
        int databaseSizeBeforeUpdate = corretorRepository.findAll().size();
        corretor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorretorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(corretor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Corretor in the database
        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCorretor() throws Exception {
        int databaseSizeBeforeUpdate = corretorRepository.findAll().size();
        corretor.setId(count.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCorretorMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(corretor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Corretor in the database
        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCorretor() throws Exception {
        // Initialize the database
        corretorRepository.saveAndFlush(corretor);

        int databaseSizeBeforeDelete = corretorRepository.findAll().size();

        // Delete the corretor
        restCorretorMockMvc
            .perform(delete(ENTITY_API_URL_ID, corretor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Corretor> corretorList = corretorRepository.findAll();
        assertThat(corretorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
