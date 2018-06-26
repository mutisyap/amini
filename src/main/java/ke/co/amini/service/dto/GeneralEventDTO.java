package ke.co.amini.service.dto;


import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import ke.co.amini.domain.enumeration.EventType;
import ke.co.amini.domain.enumeration.DayOfWeek;
import ke.co.amini.domain.enumeration.EventCycle;
import ke.co.amini.domain.enumeration.ContentStatus;

/**
 * A DTO for the GeneralEvent entity.
 */
public class GeneralEventDTO implements Serializable {

    private Long id;

    private String name;

    private EventType type;

    private DayOfWeek dayOfWeek;

    private LocalDate startDate;

    private String description;

    private Integer ageLimit;

    private String host;

    private String venue;

    private String latitude;

    private String longitude;

    private EventCycle cycle;

    private String time;

    private String language;

    private String createdBy;

    private ZonedDateTime createdOn;

    private String lastUpdatedBy;

    private ZonedDateTime lastUpdatedOn;

    private Float charges;

    private String contact1;

    private String contact1Value;

    private String contact2;

    private String contact2Value;

    private String contact3;

    private String contact3Value;

    private Boolean charged;

    private ContentStatus contentStatus;

    private String uuid;

    private Long organizationId;

    private String organizationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAgeLimit() {
        return ageLimit;
    }

    public void setAgeLimit(Integer ageLimit) {
        this.ageLimit = ageLimit;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public EventCycle getCycle() {
        return cycle;
    }

    public void setCycle(EventCycle cycle) {
        this.cycle = cycle;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public ZonedDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public Float getCharges() {
        return charges;
    }

    public void setCharges(Float charges) {
        this.charges = charges;
    }

    public String getContact1() {
        return contact1;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact1Value() {
        return contact1Value;
    }

    public void setContact1Value(String contact1Value) {
        this.contact1Value = contact1Value;
    }

    public String getContact2() {
        return contact2;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getContact2Value() {
        return contact2Value;
    }

    public void setContact2Value(String contact2Value) {
        this.contact2Value = contact2Value;
    }

    public String getContact3() {
        return contact3;
    }

    public void setContact3(String contact3) {
        this.contact3 = contact3;
    }

    public String getContact3Value() {
        return contact3Value;
    }

    public void setContact3Value(String contact3Value) {
        this.contact3Value = contact3Value;
    }

    public Boolean isCharged() {
        return charged;
    }

    public void setCharged(Boolean charged) {
        this.charged = charged;
    }

    public ContentStatus getContentStatus() {
        return contentStatus;
    }

    public void setContentStatus(ContentStatus contentStatus) {
        this.contentStatus = contentStatus;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(Long organizationId) {
        this.organizationId = organizationId;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GeneralEventDTO generalEventDTO = (GeneralEventDTO) o;
        if(generalEventDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), generalEventDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GeneralEventDTO{" +
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
