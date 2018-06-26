package ke.co.amini.web.rest;

import ke.co.amini.AminiApp;

import ke.co.amini.domain.Album;
import ke.co.amini.repository.AlbumRepository;
import ke.co.amini.service.AlbumService;
import ke.co.amini.service.dto.AlbumDTO;
import ke.co.amini.service.mapper.AlbumMapper;
import ke.co.amini.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static ke.co.amini.web.rest.TestUtil.sameInstant;
import static ke.co.amini.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AlbumResource REST controller.
 *
 * @see AlbumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AminiApp.class)
public class AlbumResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_OBJECT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_OBJECT_UUID = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAlbumMockMvc;

    private Album album;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AlbumResource albumResource = new AlbumResource(albumService);
        this.restAlbumMockMvc = MockMvcBuilders.standaloneSetup(albumResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Album createEntity(EntityManager em) {
        Album album = new Album()
            .name(DEFAULT_NAME)
            .objectUuid(DEFAULT_OBJECT_UUID)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON);
        return album;
    }

    @Before
    public void initTest() {
        album = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlbum() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();

        // Create the Album
        AlbumDTO albumDTO = albumMapper.toDto(album);
        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isCreated());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate + 1);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAlbum.getObjectUuid()).isEqualTo(DEFAULT_OBJECT_UUID);
        assertThat(testAlbum.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAlbum.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testAlbum.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
        assertThat(testAlbum.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void createAlbumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();

        // Create the Album with an existing ID
        album.setId(1L);
        AlbumDTO albumDTO = albumMapper.toDto(album);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAlbums() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].objectUuid").value(hasItem(DEFAULT_OBJECT_UUID.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_ON))));
    }

    @Test
    @Transactional
    public void getAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get the album
        restAlbumMockMvc.perform(get("/api/albums/{id}", album.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(album.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.objectUuid").value(DEFAULT_OBJECT_UUID.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedOn").value(sameInstant(DEFAULT_LAST_UPDATED_ON)));
    }

    @Test
    @Transactional
    public void getNonExistingAlbum() throws Exception {
        // Get the album
        restAlbumMockMvc.perform(get("/api/albums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);
        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // Update the album
        Album updatedAlbum = albumRepository.findOne(album.getId());
        // Disconnect from session so that the updates on updatedAlbum are not directly saved in db
        em.detach(updatedAlbum);
        updatedAlbum
            .name(UPDATED_NAME)
            .objectUuid(UPDATED_OBJECT_UUID)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON);
        AlbumDTO albumDTO = albumMapper.toDto(updatedAlbum);

        restAlbumMockMvc.perform(put("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isOk());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAlbum.getObjectUuid()).isEqualTo(UPDATED_OBJECT_UUID);
        assertThat(testAlbum.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAlbum.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testAlbum.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        assertThat(testAlbum.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
    }

    @Test
    @Transactional
    public void updateNonExistingAlbum() throws Exception {
        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // Create the Album
        AlbumDTO albumDTO = albumMapper.toDto(album);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlbumMockMvc.perform(put("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isCreated());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);
        int databaseSizeBeforeDelete = albumRepository.findAll().size();

        // Get the album
        restAlbumMockMvc.perform(delete("/api/albums/{id}", album.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Album.class);
        Album album1 = new Album();
        album1.setId(1L);
        Album album2 = new Album();
        album2.setId(album1.getId());
        assertThat(album1).isEqualTo(album2);
        album2.setId(2L);
        assertThat(album1).isNotEqualTo(album2);
        album1.setId(null);
        assertThat(album1).isNotEqualTo(album2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AlbumDTO.class);
        AlbumDTO albumDTO1 = new AlbumDTO();
        albumDTO1.setId(1L);
        AlbumDTO albumDTO2 = new AlbumDTO();
        assertThat(albumDTO1).isNotEqualTo(albumDTO2);
        albumDTO2.setId(albumDTO1.getId());
        assertThat(albumDTO1).isEqualTo(albumDTO2);
        albumDTO2.setId(2L);
        assertThat(albumDTO1).isNotEqualTo(albumDTO2);
        albumDTO1.setId(null);
        assertThat(albumDTO1).isNotEqualTo(albumDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(albumMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(albumMapper.fromId(null)).isNull();
    }
}
