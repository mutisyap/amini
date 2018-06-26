package ke.co.amini.web.rest;

import ke.co.amini.AminiApp;

import ke.co.amini.domain.GeneralEvent;
import ke.co.amini.repository.GeneralEventRepository;
import ke.co.amini.service.GeneralEventService;
import ke.co.amini.service.dto.GeneralEventDTO;
import ke.co.amini.service.mapper.GeneralEventMapper;
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
import java.time.LocalDate;
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

import ke.co.amini.domain.enumeration.EventType;
import ke.co.amini.domain.enumeration.DayOfWeek;
import ke.co.amini.domain.enumeration.EventCycle;
import ke.co.amini.domain.enumeration.ContentStatus;
/**
 * Test class for the GeneralEventResource REST controller.
 *
 * @see GeneralEventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AminiApp.class)
public class GeneralEventResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final EventType DEFAULT_TYPE = EventType.MASS;
    private static final EventType UPDATED_TYPE = EventType.ADORATION;

    private static final DayOfWeek DEFAULT_DAY_OF_WEEK = DayOfWeek.MONDAY;
    private static final DayOfWeek UPDATED_DAY_OF_WEEK = DayOfWeek.TUESDAY;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_AGE_LIMIT = 1;
    private static final Integer UPDATED_AGE_LIMIT = 2;

    private static final String DEFAULT_HOST = "AAAAAAAAAA";
    private static final String UPDATED_HOST = "BBBBBBBBBB";

    private static final String DEFAULT_VENUE = "AAAAAAAAAA";
    private static final String UPDATED_VENUE = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final EventCycle DEFAULT_CYCLE = EventCycle.ONE_OFF;
    private static final EventCycle UPDATED_CYCLE = EventCycle.DAILY;

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_LANGUAGE = "AAAAAAAAAA";
    private static final String UPDATED_LANGUAGE = "BBBBBBBBBB";

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_CREATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_CREATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_LAST_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_UPDATED_BY = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LAST_UPDATED_ON = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LAST_UPDATED_ON = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Float DEFAULT_CHARGES = 1F;
    private static final Float UPDATED_CHARGES = 2F;

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

    private static final Boolean DEFAULT_CHARGED = false;
    private static final Boolean UPDATED_CHARGED = true;

    private static final ContentStatus DEFAULT_CONTENT_STATUS = ContentStatus.DRAFT;
    private static final ContentStatus UPDATED_CONTENT_STATUS = ContentStatus.PUBLISHED;

    private static final String DEFAULT_UUID = "AAAAAAAAAA";
    private static final String UPDATED_UUID = "BBBBBBBBBB";

    @Autowired
    private GeneralEventRepository generalEventRepository;

    @Autowired
    private GeneralEventMapper generalEventMapper;

    @Autowired
    private GeneralEventService generalEventService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restGeneralEventMockMvc;

    private GeneralEvent generalEvent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GeneralEventResource generalEventResource = new GeneralEventResource(generalEventService);
        this.restGeneralEventMockMvc = MockMvcBuilders.standaloneSetup(generalEventResource)
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
    public static GeneralEvent createEntity(EntityManager em) {
        GeneralEvent generalEvent = new GeneralEvent()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .dayOfWeek(DEFAULT_DAY_OF_WEEK)
            .startDate(DEFAULT_START_DATE)
            .description(DEFAULT_DESCRIPTION)
            .ageLimit(DEFAULT_AGE_LIMIT)
            .host(DEFAULT_HOST)
            .venue(DEFAULT_VENUE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .cycle(DEFAULT_CYCLE)
            .time(DEFAULT_TIME)
            .language(DEFAULT_LANGUAGE)
            .createdBy(DEFAULT_CREATED_BY)
            .createdOn(DEFAULT_CREATED_ON)
            .lastUpdatedBy(DEFAULT_LAST_UPDATED_BY)
            .lastUpdatedOn(DEFAULT_LAST_UPDATED_ON)
            .charges(DEFAULT_CHARGES)
            .contact1(DEFAULT_CONTACT_1)
            .contact1Value(DEFAULT_CONTACT_1_VALUE)
            .contact2(DEFAULT_CONTACT_2)
            .contact2Value(DEFAULT_CONTACT_2_VALUE)
            .contact3(DEFAULT_CONTACT_3)
            .contact3Value(DEFAULT_CONTACT_3_VALUE)
            .charged(DEFAULT_CHARGED)
            .contentStatus(DEFAULT_CONTENT_STATUS)
            .uuid(DEFAULT_UUID);
        return generalEvent;
    }

    @Before
    public void initTest() {
        generalEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createGeneralEvent() throws Exception {
        int databaseSizeBeforeCreate = generalEventRepository.findAll().size();

        // Create the GeneralEvent
        GeneralEventDTO generalEventDTO = generalEventMapper.toDto(generalEvent);
        restGeneralEventMockMvc.perform(post("/api/general-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalEventDTO)))
            .andExpect(status().isCreated());

        // Validate the GeneralEvent in the database
        List<GeneralEvent> generalEventList = generalEventRepository.findAll();
        assertThat(generalEventList).hasSize(databaseSizeBeforeCreate + 1);
        GeneralEvent testGeneralEvent = generalEventList.get(generalEventList.size() - 1);
        assertThat(testGeneralEvent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGeneralEvent.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testGeneralEvent.getDayOfWeek()).isEqualTo(DEFAULT_DAY_OF_WEEK);
        assertThat(testGeneralEvent.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testGeneralEvent.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testGeneralEvent.getAgeLimit()).isEqualTo(DEFAULT_AGE_LIMIT);
        assertThat(testGeneralEvent.getHost()).isEqualTo(DEFAULT_HOST);
        assertThat(testGeneralEvent.getVenue()).isEqualTo(DEFAULT_VENUE);
        assertThat(testGeneralEvent.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testGeneralEvent.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testGeneralEvent.getCycle()).isEqualTo(DEFAULT_CYCLE);
        assertThat(testGeneralEvent.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testGeneralEvent.getLanguage()).isEqualTo(DEFAULT_LANGUAGE);
        assertThat(testGeneralEvent.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testGeneralEvent.getCreatedOn()).isEqualTo(DEFAULT_CREATED_ON);
        assertThat(testGeneralEvent.getLastUpdatedBy()).isEqualTo(DEFAULT_LAST_UPDATED_BY);
        assertThat(testGeneralEvent.getLastUpdatedOn()).isEqualTo(DEFAULT_LAST_UPDATED_ON);
        assertThat(testGeneralEvent.getCharges()).isEqualTo(DEFAULT_CHARGES);
        assertThat(testGeneralEvent.getContact1()).isEqualTo(DEFAULT_CONTACT_1);
        assertThat(testGeneralEvent.getContact1Value()).isEqualTo(DEFAULT_CONTACT_1_VALUE);
        assertThat(testGeneralEvent.getContact2()).isEqualTo(DEFAULT_CONTACT_2);
        assertThat(testGeneralEvent.getContact2Value()).isEqualTo(DEFAULT_CONTACT_2_VALUE);
        assertThat(testGeneralEvent.getContact3()).isEqualTo(DEFAULT_CONTACT_3);
        assertThat(testGeneralEvent.getContact3Value()).isEqualTo(DEFAULT_CONTACT_3_VALUE);
        assertThat(testGeneralEvent.isCharged()).isEqualTo(DEFAULT_CHARGED);
        assertThat(testGeneralEvent.getContentStatus()).isEqualTo(DEFAULT_CONTENT_STATUS);
        assertThat(testGeneralEvent.getUuid()).isEqualTo(DEFAULT_UUID);
    }

    @Test
    @Transactional
    public void createGeneralEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = generalEventRepository.findAll().size();

        // Create the GeneralEvent with an existing ID
        generalEvent.setId(1L);
        GeneralEventDTO generalEventDTO = generalEventMapper.toDto(generalEvent);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGeneralEventMockMvc.perform(post("/api/general-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalEventDTO)))
            .andExpect(status().isBadRequest());

        // Validate the GeneralEvent in the database
        List<GeneralEvent> generalEventList = generalEventRepository.findAll();
        assertThat(generalEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllGeneralEvents() throws Exception {
        // Initialize the database
        generalEventRepository.saveAndFlush(generalEvent);

        // Get all the generalEventList
        restGeneralEventMockMvc.perform(get("/api/general-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(generalEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].dayOfWeek").value(hasItem(DEFAULT_DAY_OF_WEEK.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].ageLimit").value(hasItem(DEFAULT_AGE_LIMIT)))
            .andExpect(jsonPath("$.[*].host").value(hasItem(DEFAULT_HOST.toString())))
            .andExpect(jsonPath("$.[*].venue").value(hasItem(DEFAULT_VENUE.toString())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.toString())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.toString())))
            .andExpect(jsonPath("$.[*].cycle").value(hasItem(DEFAULT_CYCLE.toString())))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME.toString())))
            .andExpect(jsonPath("$.[*].language").value(hasItem(DEFAULT_LANGUAGE.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY.toString())))
            .andExpect(jsonPath("$.[*].createdOn").value(hasItem(sameInstant(DEFAULT_CREATED_ON))))
            .andExpect(jsonPath("$.[*].lastUpdatedBy").value(hasItem(DEFAULT_LAST_UPDATED_BY.toString())))
            .andExpect(jsonPath("$.[*].lastUpdatedOn").value(hasItem(sameInstant(DEFAULT_LAST_UPDATED_ON))))
            .andExpect(jsonPath("$.[*].charges").value(hasItem(DEFAULT_CHARGES.doubleValue())))
            .andExpect(jsonPath("$.[*].contact1").value(hasItem(DEFAULT_CONTACT_1.toString())))
            .andExpect(jsonPath("$.[*].contact1Value").value(hasItem(DEFAULT_CONTACT_1_VALUE.toString())))
            .andExpect(jsonPath("$.[*].contact2").value(hasItem(DEFAULT_CONTACT_2.toString())))
            .andExpect(jsonPath("$.[*].contact2Value").value(hasItem(DEFAULT_CONTACT_2_VALUE.toString())))
            .andExpect(jsonPath("$.[*].contact3").value(hasItem(DEFAULT_CONTACT_3.toString())))
            .andExpect(jsonPath("$.[*].contact3Value").value(hasItem(DEFAULT_CONTACT_3_VALUE.toString())))
            .andExpect(jsonPath("$.[*].charged").value(hasItem(DEFAULT_CHARGED.booleanValue())))
            .andExpect(jsonPath("$.[*].contentStatus").value(hasItem(DEFAULT_CONTENT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())));
    }

    @Test
    @Transactional
    public void getGeneralEvent() throws Exception {
        // Initialize the database
        generalEventRepository.saveAndFlush(generalEvent);

        // Get the generalEvent
        restGeneralEventMockMvc.perform(get("/api/general-events/{id}", generalEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(generalEvent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.dayOfWeek").value(DEFAULT_DAY_OF_WEEK.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.ageLimit").value(DEFAULT_AGE_LIMIT))
            .andExpect(jsonPath("$.host").value(DEFAULT_HOST.toString()))
            .andExpect(jsonPath("$.venue").value(DEFAULT_VENUE.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.toString()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.toString()))
            .andExpect(jsonPath("$.cycle").value(DEFAULT_CYCLE.toString()))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME.toString()))
            .andExpect(jsonPath("$.language").value(DEFAULT_LANGUAGE.toString()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY.toString()))
            .andExpect(jsonPath("$.createdOn").value(sameInstant(DEFAULT_CREATED_ON)))
            .andExpect(jsonPath("$.lastUpdatedBy").value(DEFAULT_LAST_UPDATED_BY.toString()))
            .andExpect(jsonPath("$.lastUpdatedOn").value(sameInstant(DEFAULT_LAST_UPDATED_ON)))
            .andExpect(jsonPath("$.charges").value(DEFAULT_CHARGES.doubleValue()))
            .andExpect(jsonPath("$.contact1").value(DEFAULT_CONTACT_1.toString()))
            .andExpect(jsonPath("$.contact1Value").value(DEFAULT_CONTACT_1_VALUE.toString()))
            .andExpect(jsonPath("$.contact2").value(DEFAULT_CONTACT_2.toString()))
            .andExpect(jsonPath("$.contact2Value").value(DEFAULT_CONTACT_2_VALUE.toString()))
            .andExpect(jsonPath("$.contact3").value(DEFAULT_CONTACT_3.toString()))
            .andExpect(jsonPath("$.contact3Value").value(DEFAULT_CONTACT_3_VALUE.toString()))
            .andExpect(jsonPath("$.charged").value(DEFAULT_CHARGED.booleanValue()))
            .andExpect(jsonPath("$.contentStatus").value(DEFAULT_CONTENT_STATUS.toString()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingGeneralEvent() throws Exception {
        // Get the generalEvent
        restGeneralEventMockMvc.perform(get("/api/general-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGeneralEvent() throws Exception {
        // Initialize the database
        generalEventRepository.saveAndFlush(generalEvent);
        int databaseSizeBeforeUpdate = generalEventRepository.findAll().size();

        // Update the generalEvent
        GeneralEvent updatedGeneralEvent = generalEventRepository.findOne(generalEvent.getId());
        // Disconnect from session so that the updates on updatedGeneralEvent are not directly saved in db
        em.detach(updatedGeneralEvent);
        updatedGeneralEvent
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .dayOfWeek(UPDATED_DAY_OF_WEEK)
            .startDate(UPDATED_START_DATE)
            .description(UPDATED_DESCRIPTION)
            .ageLimit(UPDATED_AGE_LIMIT)
            .host(UPDATED_HOST)
            .venue(UPDATED_VENUE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .cycle(UPDATED_CYCLE)
            .time(UPDATED_TIME)
            .language(UPDATED_LANGUAGE)
            .createdBy(UPDATED_CREATED_BY)
            .createdOn(UPDATED_CREATED_ON)
            .lastUpdatedBy(UPDATED_LAST_UPDATED_BY)
            .lastUpdatedOn(UPDATED_LAST_UPDATED_ON)
            .charges(UPDATED_CHARGES)
            .contact1(UPDATED_CONTACT_1)
            .contact1Value(UPDATED_CONTACT_1_VALUE)
            .contact2(UPDATED_CONTACT_2)
            .contact2Value(UPDATED_CONTACT_2_VALUE)
            .contact3(UPDATED_CONTACT_3)
            .contact3Value(UPDATED_CONTACT_3_VALUE)
            .charged(UPDATED_CHARGED)
            .contentStatus(UPDATED_CONTENT_STATUS)
            .uuid(UPDATED_UUID);
        GeneralEventDTO generalEventDTO = generalEventMapper.toDto(updatedGeneralEvent);

        restGeneralEventMockMvc.perform(put("/api/general-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalEventDTO)))
            .andExpect(status().isOk());

        // Validate the GeneralEvent in the database
        List<GeneralEvent> generalEventList = generalEventRepository.findAll();
        assertThat(generalEventList).hasSize(databaseSizeBeforeUpdate);
        GeneralEvent testGeneralEvent = generalEventList.get(generalEventList.size() - 1);
        assertThat(testGeneralEvent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGeneralEvent.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testGeneralEvent.getDayOfWeek()).isEqualTo(UPDATED_DAY_OF_WEEK);
        assertThat(testGeneralEvent.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testGeneralEvent.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testGeneralEvent.getAgeLimit()).isEqualTo(UPDATED_AGE_LIMIT);
        assertThat(testGeneralEvent.getHost()).isEqualTo(UPDATED_HOST);
        assertThat(testGeneralEvent.getVenue()).isEqualTo(UPDATED_VENUE);
        assertThat(testGeneralEvent.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testGeneralEvent.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testGeneralEvent.getCycle()).isEqualTo(UPDATED_CYCLE);
        assertThat(testGeneralEvent.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testGeneralEvent.getLanguage()).isEqualTo(UPDATED_LANGUAGE);
        assertThat(testGeneralEvent.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testGeneralEvent.getCreatedOn()).isEqualTo(UPDATED_CREATED_ON);
        assertThat(testGeneralEvent.getLastUpdatedBy()).isEqualTo(UPDATED_LAST_UPDATED_BY);
        assertThat(testGeneralEvent.getLastUpdatedOn()).isEqualTo(UPDATED_LAST_UPDATED_ON);
        assertThat(testGeneralEvent.getCharges()).isEqualTo(UPDATED_CHARGES);
        assertThat(testGeneralEvent.getContact1()).isEqualTo(UPDATED_CONTACT_1);
        assertThat(testGeneralEvent.getContact1Value()).isEqualTo(UPDATED_CONTACT_1_VALUE);
        assertThat(testGeneralEvent.getContact2()).isEqualTo(UPDATED_CONTACT_2);
        assertThat(testGeneralEvent.getContact2Value()).isEqualTo(UPDATED_CONTACT_2_VALUE);
        assertThat(testGeneralEvent.getContact3()).isEqualTo(UPDATED_CONTACT_3);
        assertThat(testGeneralEvent.getContact3Value()).isEqualTo(UPDATED_CONTACT_3_VALUE);
        assertThat(testGeneralEvent.isCharged()).isEqualTo(UPDATED_CHARGED);
        assertThat(testGeneralEvent.getContentStatus()).isEqualTo(UPDATED_CONTENT_STATUS);
        assertThat(testGeneralEvent.getUuid()).isEqualTo(UPDATED_UUID);
    }

    @Test
    @Transactional
    public void updateNonExistingGeneralEvent() throws Exception {
        int databaseSizeBeforeUpdate = generalEventRepository.findAll().size();

        // Create the GeneralEvent
        GeneralEventDTO generalEventDTO = generalEventMapper.toDto(generalEvent);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restGeneralEventMockMvc.perform(put("/api/general-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(generalEventDTO)))
            .andExpect(status().isCreated());

        // Validate the GeneralEvent in the database
        List<GeneralEvent> generalEventList = generalEventRepository.findAll();
        assertThat(generalEventList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteGeneralEvent() throws Exception {
        // Initialize the database
        generalEventRepository.saveAndFlush(generalEvent);
        int databaseSizeBeforeDelete = generalEventRepository.findAll().size();

        // Get the generalEvent
        restGeneralEventMockMvc.perform(delete("/api/general-events/{id}", generalEvent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<GeneralEvent> generalEventList = generalEventRepository.findAll();
        assertThat(generalEventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeneralEvent.class);
        GeneralEvent generalEvent1 = new GeneralEvent();
        generalEvent1.setId(1L);
        GeneralEvent generalEvent2 = new GeneralEvent();
        generalEvent2.setId(generalEvent1.getId());
        assertThat(generalEvent1).isEqualTo(generalEvent2);
        generalEvent2.setId(2L);
        assertThat(generalEvent1).isNotEqualTo(generalEvent2);
        generalEvent1.setId(null);
        assertThat(generalEvent1).isNotEqualTo(generalEvent2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(GeneralEventDTO.class);
        GeneralEventDTO generalEventDTO1 = new GeneralEventDTO();
        generalEventDTO1.setId(1L);
        GeneralEventDTO generalEventDTO2 = new GeneralEventDTO();
        assertThat(generalEventDTO1).isNotEqualTo(generalEventDTO2);
        generalEventDTO2.setId(generalEventDTO1.getId());
        assertThat(generalEventDTO1).isEqualTo(generalEventDTO2);
        generalEventDTO2.setId(2L);
        assertThat(generalEventDTO1).isNotEqualTo(generalEventDTO2);
        generalEventDTO1.setId(null);
        assertThat(generalEventDTO1).isNotEqualTo(generalEventDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(generalEventMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(generalEventMapper.fromId(null)).isNull();
    }
}
