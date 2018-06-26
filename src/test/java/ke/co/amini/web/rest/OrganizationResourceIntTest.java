package ke.co.amini.web.rest;

import ke.co.amini.AminiApp;

import ke.co.amini.domain.Organization;
import ke.co.amini.repository.OrganizationRepository;
import ke.co.amini.service.OrganizationService;
import ke.co.amini.service.dto.OrganizationDTO;
import ke.co.amini.service.mapper.OrganizationMapper;
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

import ke.co.amini.domain.enumeration.OrganizationType;
/**
 * Test class for the OrganizationResource REST controller.
 *
 * @see OrganizationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AminiApp.class)
public class OrganizationResourceIntTest {

    private static final OrganizationType DEFAULT_TYPE = OrganizationType.CHURCH;
    private static final OrganizationType UPDATED_TYPE = OrganizationType.CHARITY;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Float DEFAULT_LATITUDE = 1F;
    private static final Float UPDATED_LATITUDE = 2F;

    private static final Float DEFAULT_LONGITUDE = 1F;
    private static final Float UPDATED_LONGITUDE = 2F;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_1 = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_1_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_1_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_2 = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_2_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_2_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_3 = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_3 = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_3_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_3_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restOrganizationMockMvc;

    private Organization organization;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrganizationResource organizationResource = new OrganizationResource(organizationService);
        this.restOrganizationMockMvc = MockMvcBuilders.standaloneSetup(organizationResource)
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
    public static Organization createEntity(EntityManager em) {
        Organization organization = new Organization()
            .type(DEFAULT_TYPE)
            .name(DEFAULT_NAME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON)
            .description(DEFAULT_DESCRIPTION)
            .contact1(DEFAULT_CONTACT_1)
            .contact1Value(DEFAULT_CONTACT_1_VALUE)
            .contact2(DEFAULT_CONTACT_2)
            .contact2Value(DEFAULT_CONTACT_2_VALUE)
            .contact3(DEFAULT_CONTACT_3)
            .contact3Value(DEFAULT_CONTACT_3_VALUE)
            .contentStatus(DEFAULT_CONTENT_STATUS)
            .uuid(DEFAULT_UUID);
        return organization;
    }

    @Before
    public void initTest() {
        organization = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrganization() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);
        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate + 1);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOrganization.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrganization.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testOrganization.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testOrganization.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOrganization.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testOrganization.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
        assertThat(testOrganization.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
        assertThat(testOrganization.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOrganization.getContact1()).isEqualTo(DEFAULT_CONTACT_1);
        assertThat(testOrganization.getContact1Value()).isEqualTo(DEFAULT_CONTACT_1_VALUE);
        assertThat(testOrganization.getContact2()).isEqualTo(DEFAULT_CONTACT_2);
        assertThat(testOrganization.getContact2Value()).isEqualTo(DEFAULT_CONTACT_2_VALUE);
        assertThat(testOrganization.getContact3()).isEqualTo(DEFAULT_CONTACT_3);
        assertThat(testOrganization.getContact3Value()).isEqualTo(DEFAULT_CONTACT_3_VALUE);
        assertThat(testOrganization.getContentStatus()).isEqualTo(DEFAULT_CONTENT_STATUS);
        assertThat(testOrganization.getUuid()).isEqualTo(DEFAULT_UUID);
    }

    @Test
    @Transactional
    public void createOrganizationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = organizationRepository.findAll().size();

        // Create the Organization with an existing ID
        organization.setId(1L);
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrganizationMockMvc.perform(post("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllOrganizations() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get all the organizationList
        restOrganizationMockMvc.perform(get("/api/organizations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(organization.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_ON))))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].contact1").value(hasItem(DEFAULT_CONTACT_1.toString())))
            .andExpect(jsonPath("$.[*].contact1Value").value(hasItem(DEFAULT_CONTACT_1_VALUE.toString())))
            .andExpect(jsonPath("$.[*].contact2").value(hasItem(DEFAULT_CONTACT_2.toString())))
            .andExpect(jsonPath("$.[*].contact2Value").value(hasItem(DEFAULT_CONTACT_2_VALUE.toString())))
            .andExpect(jsonPath("$.[*].contact3").value(hasItem(DEFAULT_CONTACT_3.toString())))
            .andExpect(jsonPath("$.[*].contact3Value").value(hasItem(DEFAULT_CONTACT_3_VALUE.toString())))
            .andExpect(jsonPath("$.[*].contentStatus").value(hasItem(DEFAULT_CONTENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())));
    }

    @Test
    @Transactional
    public void getOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);

        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", organization.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(organization.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedOn").value(sameInstant(DEFAULT_LAST_UPDATED_ON)))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.contact1").value(DEFAULT_CONTACT_1.toString()))
            .andExpect(jsonPath("$.contact1Value").value(DEFAULT_CONTACT_1_VALUE.toString()))
            .andExpect(jsonPath("$.contact2").value(DEFAULT_CONTACT_2.toString()))
            .andExpect(jsonPath("$.contact2Value").value(DEFAULT_CONTACT_2_VALUE.toString()))
            .andExpect(jsonPath("$.contact3").value(DEFAULT_CONTACT_3.toString()))
            .andExpect(jsonPath("$.contact3Value").value(DEFAULT_CONTACT_3_VALUE.toString()))
            .andExpect(jsonPath("$.contentStatus").value(DEFAULT_CONTENT_STATUS.toString()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOrganization() throws Exception {
        // Get the organization
        restOrganizationMockMvc.perform(get("/api/organizations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Update the organization
        Organization updatedOrganization = organizationRepository.findOne(organization.getId());
        // Disconnect from session so that the updates on updatedOrganization are not directly saved in db
        em.detach(updatedOrganization);
        updatedOrganization
            .type(UPDATED_TYPE)
            .name(UPDATED_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .description(UPDATED_DESCRIPTION)
            .contact1(UPDATED_CONTACT_1)
            .contact1Value(UPDATED_CONTACT_1_VALUE)
            .contact2(UPDATED_CONTACT_2)
            .contact2Value(UPDATED_CONTACT_2_VALUE)
            .contact3(UPDATED_CONTACT_3)
            .contact3Value(UPDATED_CONTACT_3_VALUE)
            .contentStatus(UPDATED_CONTENT_STATUS)
            .uuid(UPDATED_UUID);
        OrganizationDTO organizationDTO = organizationMapper.toDto(updatedOrganization);

        restOrganizationMockMvc.perform(put("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isOk());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate);
        Organization testOrganization = organizationList.get(organizationList.size() - 1);
        assertThat(testOrganization.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOrganization.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrganization.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testOrganization.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testOrganization.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOrganization.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testOrganization.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        assertThat(testOrganization.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testOrganization.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOrganization.getContact1()).isEqualTo(UPDATED_CONTACT_1);
        assertThat(testOrganization.getContact1Value()).isEqualTo(UPDATED_CONTACT_1_VALUE);
        assertThat(testOrganization.getContact2()).isEqualTo(UPDATED_CONTACT_2);
        assertThat(testOrganization.getContact2Value()).isEqualTo(UPDATED_CONTACT_2_VALUE);
        assertThat(testOrganization.getContact3()).isEqualTo(UPDATED_CONTACT_3);
        assertThat(testOrganization.getContact3Value()).isEqualTo(UPDATED_CONTACT_3_VALUE);
        assertThat(testOrganization.getContentStatus()).isEqualTo(UPDATED_CONTENT_STATUS);
        assertThat(testOrganization.getUuid()).isEqualTo(UPDATED_UUID);
    }

    @Test
    @Transactional
    public void updateNonExistingOrganization() throws Exception {
        int databaseSizeBeforeUpdate = organizationRepository.findAll().size();

        // Create the Organization
        OrganizationDTO organizationDTO = organizationMapper.toDto(organization);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restOrganizationMockMvc.perform(put("/api/organizations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(organizationDTO)))
            .andExpect(status().isCreated());

        // Validate the Organization in the database
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteOrganization() throws Exception {
        // Initialize the database
        organizationRepository.saveAndFlush(organization);
        int databaseSizeBeforeDelete = organizationRepository.findAll().size();

        // Get the organization
        restOrganizationMockMvc.perform(delete("/api/organizations/{id}", organization.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Organization> organizationList = organizationRepository.findAll();
        assertThat(organizationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Organization.class);
        Organization organization1 = new Organization();
        organization1.setId(1L);
        Organization organization2 = new Organization();
        organization2.setId(organization1.getId());
        assertThat(organization1).isEqualTo(organization2);
        organization2.setId(2L);
        assertThat(organization1).isNotEqualTo(organization2);
        organization1.setId(null);
        assertThat(organization1).isNotEqualTo(organization2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrganizationDTO.class);
        OrganizationDTO organizationDTO1 = new OrganizationDTO();
        organizationDTO1.setId(1L);
        OrganizationDTO organizationDTO2 = new OrganizationDTO();
        assertThat(organizationDTO1).isNotEqualTo(organizationDTO2);
        organizationDTO2.setId(organizationDTO1.getId());
        assertThat(organizationDTO1).isEqualTo(organizationDTO2);
        organizationDTO2.setId(2L);
        assertThat(organizationDTO1).isNotEqualTo(organizationDTO2);
        organizationDTO1.setId(null);
        assertThat(organizationDTO1).isNotEqualTo(organizationDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(organizationMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(organizationMapper.fromId(null)).isNull();
    }
}
