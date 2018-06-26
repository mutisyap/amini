package ke.co.amini.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Objects;

import ke.co.amini.domain.enumeration.EventType;

import ke.co.amini.domain.enumeration.DayOfWeek;

import ke.co.amini.domain.enumeration.EventCycle;

import ke.co.amini.domain.enumeration.ContentStatus;

/**
 * A GeneralEvent.
 */
@Entity
@Table(name = "tbl_general_events")
public class GeneralEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private EventType type;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week")
    private DayOfWeek dayOfWeek;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "description")
    private String description;

    @Column(name = "age_limit")
    private Integer ageLimit;

    @Column(name = "host")
    private String host;

    @Column(name = "venue")
    private String venue;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_cycle")
    private EventCycle cycle;

    @Column(name = "jhi_time")
    private String time;

    @Column(name = "language")
    private String language;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "last_updated_on")
    private ZonedDateTime lastUpdatedOn;

    @Column(name = "charges")
    private Float charges;

    @Column(name = "contact_1")
    private String contact1;

    @Column(name = "contact_1_value")
    private String contact1Value;

    @Column(name = "contact_2")
    private String contact2;

    @Column(name = "contact_2_value")
    private String contact2Value;

    @Column(name = "contact_3")
    private String contact3;

    @Column(name = "contact_3_value")
    private String contact3Value;

    @Column(name = "charged")
    private Boolean charged;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_status")
    private ContentStatus contentStatus;

    @Column(name = "uuid")
    private String uuid;

    @ManyToOne
    private Organization organization;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public GeneralEvent name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getType() {
        return type;
    }

    public GeneralEvent type(EventType type) {
        this.type = type;
        return this;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public GeneralEvent dayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public GeneralEvent startDate(LocalDate startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public GeneralEvent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public GeneralEvent ageLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
        return this;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getHost() {
        return host;
    }

    public GeneralEvent host(String host) {
        this.host = host;
        return this;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getVenue() {
        return venue;
    }

    public GeneralEvent venue(String venue) {
        this.venue = venue;
        return this;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getLatitude() {
        return latitude;
    }

    public GeneralEvent latitude(String latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public GeneralEvent longitude(String longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public EventCycle getCycle() {
        return cycle;
    }

    public GeneralEvent cycle(EventCycle cycle) {
        this.cycle = cycle;
        return this;
    }

    public void setCycle(EventCycle cycle) {
        this.cycle = cycle;
    }

    public String getTime() {
        return time;
    }

    public GeneralEvent time(String time) {
        this.time = time;
        return this;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLanguage() {
        return language;
    }

    public GeneralEvent language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public GeneralEvent createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public GeneralEvent createdOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public GeneralEvent lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public ZonedDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public GeneralEvent lastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
        return this;
    }

    public void setLastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Float getCharges() {
        return charges;
    }

    public GeneralEvent charges(Float charges) {
        this.charges = charges;
        return this;
    }

    public void setCharges(Float charges) {
        this.charges = charges;
    }

    public String getContact1() {
        return contact1;
    }

    public GeneralEvent contact1(String contact1) {
        this.contact1 = contact1;
        return this;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact1Value() {
        return contact1Value;
    }

    public GeneralEvent contact1Value(String contact1Value) {
        this.contact1Value = contact1Value;
        return this;
    }

    public void setContact1Value(String contact1Value) {
        this.contact1Value = contact1Value;
    }

    public String getContact2() {
        return contact2;
    }

    public GeneralEvent contact2(String contact2) {
        this.contact2 = contact2;
        return this;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getContact2Value() {
        return contact2Value;
    }

    public GeneralEvent contact2Value(String contact2Value) {
        this.contact2Value = contact2Value;
        return this;
    }

    public void setContact2Value(String contact2Value) {
        this.contact2Value = contact2Value;
    }

    public String getContact3() {
        return contact3;
    }

    public GeneralEvent contact3(String contact3) {
        this.contact3 = contact3;
        return this;
    }

    public void setContact3(String contact3) {
        this.contact3 = contact3;
    }

    public String getContact3Value() {
        return contact3Value;
    }

    public GeneralEvent contact3Value(String contact3Value) {
        this.contact3Value = contact3Value;
        return this;
    }

    public void setContact3Value(String contact3Value) {
        this.contact3Value = contact3Value;
    }

    public Boolean isCharged() {
        return charged;
    }

    public GeneralEvent charged(Boolean charged) {
        this.charged = charged;
        return this;
    }

    public void setCharged(Boolean charged) {
        this.charged = charged;
    }

    public ContentStatus getContentStatus() {
        return contentStatus;
    }

    public GeneralEvent contentStatus(ContentStatus contentStatus) {
        this.contentStatus = contentStatus;
        return this;
    }

    public void setContentStatus(ContentStatus contentStatus) {
        this.contentStatus = contentStatus;
    }

    public String getUuid() {
        return uuid;
    }

    public GeneralEvent uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Organization getOrganization() {
        return organization;
    }

    public GeneralEvent organization(Organization organization) {
        this.organization = organization;
        return this;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GeneralEvent generalEvent = (GeneralEvent) o;
        if (generalEvent.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), generalEvent.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GeneralEvent{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", ageLimit=" + getAgeLimit() +
            ", host='" + getHost() + "'" +
            ", venue='" + getVenue() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", cycle='" + getCycle() + "'" +
            ", time='" + getTime() + "'" +
            ", language='" + getLanguage() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            ", charges=" + getCharges() +
            ", contact1='" + getContact1() + "'" +
            ", contact1Value='" + getContact1Value() + "'" +
            ", contact2='" + getContact2() + "'" +
            ", contact2Value='" + getContact2Value() + "'" +
            ", contact3='" + getContact3() + "'" +
            ", contact3Value='" + getContact3Value() + "'" +
            ", charged='" + isCharged() + "'" +
            ", contentStatus='" + getContentStatus() + "'" +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
