package ke.co.amini.web.rest;

import ke.co.amini.AminiApp;

import ke.co.amini.domain.OrganizationUser;
import ke.co.amini.domain.Organization;
import ke.co.amini.repository.OrganizationUserRepository;
import ke.co.amini.service.OrganizationUserService;
import ke.co.amini.service.dto.OrganizationUserDTO;
import ke.co.amini.service.mapper.OrganizationUserMapper;
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
import java.util.List;

import static ke.co.amini.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the OrganizationUserResource REST controller.
 *
 * @see OrganizationUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AminiApp.class)
public class OrganizationUserResourceIntTest {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    @Autowired
    private OrganizationUserRepository organizationUserRepository;

    @Autowired
    private OrganizationUserMapper organizationUserMapper;

    @Autowired
    private OrganizationUserService organizationUserService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrganizationUserMockMvc;

    private OrganizationUser organizationUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganizationUserResource organizationUserResource = new OrganizationUserResource(organizationUserService);
        this.restOrganizationUserMockMvc = MockMvcBuilders.standaloneSetup(organizationUserResource)
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
    public static OrganizationUser createEntity(EntityManager em) {
        OrganizationUser organizationUser = new OrganizationUser()
            .username(DEFAULT_USERNAME)
            .uuid(DEFAULT_UUID);
        // Add required entity
        Organization organization = OrganizationResourceIntTest.createEntity(em);
        em.persist(organization);
        em.flush();
        organizationUser.setOrganization(organization);
        return organizationUser;
    }

    @Before
    public void initTest() {
        organizationUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganizationUser() throws Exception {
        int databaseSizeBeforeCreate = organizationUserRepository.findAll().size();

        // Create the OrganizationUser
        OrganizationUserDTO organizationUserDTO = organizationUserMapper.toDto(organizationUser);
        restOrganizationUserMockMvc.perform(post("/api/organization-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationUserDTO)))
            .andExpect(status().isCreated());

        // Validate the OrganizationUser in the database
        List<OrganizationUser> organizationUserList = organizationUserRepository.findAll();
        assertThat(organizationUserList).hasSize(databaseSizeBeforeCreate + 1);
        OrganizationUser testOrganizationUser = organizationUserList.get(organizationUserList.size() - 1);
        assertThat(testOrganizationUser.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testOrganizationUser.getUuid()).isEqualTo(DEFAULT_UUID);
    }

    @Test
    @Transactional
    public void createOrganizationUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organizationUserRepository.findAll().size();

        // Create the OrganizationUser with an existing ID
        organizationUser.setId(1L);
        OrganizationUserDTO organizationUserDTO = organizationUserMapper.toDto(organizationUser);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationUserMockMvc.perform(post("/api/organization-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationUserDTO)))
            .andExpect(status().isBadRequest());

        // Validate the OrganizationUser in the database
        List<OrganizationUser> organizationUserList = organizationUserRepository.findAll();
        assertThat(organizationUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = organizationUserRepository.findAll().size();
        // set the field null
        organizationUser.setUsername(null);

        // Create the OrganizationUser, which fails.
        OrganizationUserDTO organizationUserDTO = organizationUserMapper.toDto(organizationUser);

        restOrganizationUserMockMvc.perform(post("/api/organization-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationUserDTO)))
            .andExpect(status().isBadRequest());

        List<OrganizationUser> organizationUserList = organizationUserRepository.findAll();
        assertThat(organizationUserList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOrganizationUsers() throws Exception {
        // Initialize the database
        organizationUserRepository.saveAndFlush(organizationUser);

        // Get all the organizationUserList
        restOrganizationUserMockMvc.perform(get("/api/organization-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organizationUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME.toString())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())));
    }

    @Test
    @Transactional
    public void getOrganizationUser() throws Exception {
        // Initialize the database
        organizationUserRepository.saveAndFlush(organizationUser);

        // Get the organizationUser
        restOrganizationUserMockMvc.perform(get("/api/organization-users/{id}", organizationUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organizationUser.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME.toString()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganizationUser() throws Exception {
        // Get the organizationUser
        restOrganizationUserMockMvc.perform(get("/api/organization-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganizationUser() throws Exception {
        // Initialize the database
        organizationUserRepository.saveAndFlush(organizationUser);
        int databaseSizeBeforeUpdate = organizationUserRepository.findAll().size();

        // Update the organizationUser
        OrganizationUser updatedOrganizationUser = organizationUserRepository.findOne(organizationUser.getId());
        // Disconnect from session so that the updates on updatedOrganizationUser are not directly saved in db
        em.detach(updatedOrganizationUser);
        updatedOrganizationUser
            .username(UPDATED_USERNAME)
            .uuid(UPDATED_UUID);
        OrganizationUserDTO organizationUserDTO = organizationUserMapper.toDto(updatedOrganizationUser);

        restOrganizationUserMockMvc.perform(put("/api/organization-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationUserDTO)))
            .andExpect(status().isOk());

        // Validate the OrganizationUser in the database
        List<OrganizationUser> organizationUserList = organizationUserRepository.findAll();
        assertThat(organizationUserList).hasSize(databaseSizeBeforeUpdate);
        OrganizationUser testOrganizationUser = organizationUserList.get(organizationUserList.size() - 1);
        assertThat(testOrganizationUser.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testOrganizationUser.getUuid()).isEqualTo(UPDATED_UUID);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganizationUser() throws Exception {
        int databaseSizeBeforeUpdate = organizationUserRepository.findAll().size();

        // Create the OrganizationUser
        OrganizationUserDTO organizationUserDTO = organizationUserMapper.toDto(organizationUser);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrganizationUserMockMvc.perform(put("/api/organization-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationUserDTO)))
            .andExpect(status().isCreated());

        // Validate the OrganizationUser in the database
        List<OrganizationUser> organizationUserList = organizationUserRepository.findAll();
        assertThat(organizationUserList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrganizationUser() throws Exception {
        // Initialize the database
        organizationUserRepository.saveAndFlush(organizationUser);
        int databaseSizeBeforeDelete = organizationUserRepository.findAll().size();

        // Get the organizationUser
        restOrganizationUserMockMvc.perform(delete("/api/organization-users/{id}", organizationUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<OrganizationUser> organizationUserList = organizationUserRepository.findAll();
        assertThat(organizationUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationUser.class);
        OrganizationUser organizationUser1 = new OrganizationUser();
        organizationUser1.setId(1L);
        OrganizationUser organizationUser2 = new OrganizationUser();
        organizationUser2.setId(organizationUser1.getId());
        assertThat(organizationUser1).isEqualTo(organizationUser2);
        organizationUser2.setId(2L);
        assertThat(organizationUser1).isNotEqualTo(organizationUser2);
        organizationUser1.setId(null);
        assertThat(organizationUser1).isNotEqualTo(organizationUser2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationUserDTO.class);
        OrganizationUserDTO organizationUserDTO1 = new OrganizationUserDTO();
        organizationUserDTO1.setId(1L);
        OrganizationUserDTO organizationUserDTO2 = new OrganizationUserDTO();
        assertThat(organizationUserDTO1).isNotEqualTo(organizationUserDTO2);
        organizationUserDTO2.setId(organizationUserDTO1.getId());
        assertThat(organizationUserDTO1).isEqualTo(organizationUserDTO2);
        organizationUserDTO2.setId(2L);
        assertThat(organizationUserDTO1).isNotEqualTo(organizationUserDTO2);
        organizationUserDTO1.setId(null);
        assertThat(organizationUserDTO1).isNotEqualTo(organizationUserDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(organizationUserMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(organizationUserMapper.fromId(null)).isNull();
    }
}
