package ke.co.amini.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

import ke.co.amini.domain.enumeration.OrganizationType;

/**
 * A Organization.
 */
@Entity
@Table(name = "tbl_organizations")
public class Organization implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "jhi_type")
    private OrganizationType type;

    @Column(name = "name")
    private String name;

    @Column(name = "latitude")
    private Float latitude;

    @Column(name = "longitude")
    private Float longitude;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_on")
    private ZonedDateTime createdOn;

    @Column(name = "last_updated_by")
    private String lastUpdatedBy;

    @Column(name = "last_updated_on")
    private ZonedDateTime lastUpdatedOn;

    @Column(name = "description")
    private String description;

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

    @Column(name = "content_status")
    private String contentStatus;

    @Column(name = "uuid")
    private String uuid;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrganizationType getType() {
        return type;
    }

    public Organization type(OrganizationType type) {
        this.type = type;
        return this;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Organization name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getLatitude() {
        return latitude;
    }

    public Organization latitude(Float latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public Organization longitude(Float longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public Organization createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public Organization createdOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
        return this;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public Organization lastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
        return this;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public ZonedDateTime getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public Organization lastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
        return this;
    }

    public void setLastUpdatedOn(ZonedDateTime lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getDescription() {
        return description;
    }

    public Organization description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContact1() {
        return contact1;
    }

    public Organization contact1(String contact1) {
        this.contact1 = contact1;
        return this;
    }

    public void setContact1(String contact1) {
        this.contact1 = contact1;
    }

    public String getContact1Value() {
        return contact1Value;
    }

    public Organization contact1Value(String contact1Value) {
        this.contact1Value = contact1Value;
        return this;
    }

    public void setContact1Value(String contact1Value) {
        this.contact1Value = contact1Value;
    }

    public String getContact2() {
        return contact2;
    }

    public Organization contact2(String contact2) {
        this.contact2 = contact2;
        return this;
    }

    public void setContact2(String contact2) {
        this.contact2 = contact2;
    }

    public String getContact2Value() {
        return contact2Value;
    }

    public Organization contact2Value(String contact2Value) {
        this.contact2Value = contact2Value;
        return this;
    }

    public void setContact2Value(String contact2Value) {
        this.contact2Value = contact2Value;
    }

    public String getContact3() {
        return contact3;
    }

    public Organization contact3(String contact3) {
        this.contact3 = contact3;
        return this;
    }

    public void setContact3(String contact3) {
        this.contact3 = contact3;
    }

    public String getContact3Value() {
        return contact3Value;
    }

    public Organization contact3Value(String contact3Value) {
        this.contact3Value = contact3Value;
        return this;
    }

    public void setContact3Value(String contact3Value) {
        this.contact3Value = contact3Value;
    }

    public String getContentStatus() {
        return contentStatus;
    }

    public Organization contentStatus(String contentStatus) {
        this.contentStatus = contentStatus;
        return this;
    }

    public void setContentStatus(String contentStatus) {
        this.contentStatus = contentStatus;
    }

    public String getUuid() {
        return uuid;
    }

    public Organization uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
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
        Organization organization = (Organization) o;
        if (organization.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organization.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Organization{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", name='" + getName() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdOn='" + getCreatedOn() + "'" +
            ", lastUpdatedBy='" + getLastUpdatedBy() + "'" +
            ", lastUpdatedOn='" + getLastUpdatedOn() + "'" +
            ", description='" + getDescription() + "'" +
            ", contact1='" + getContact1() + "'" +
            ", contact1Value='" + getContact1Value() + "'" +
            ", contact2='" + getContact2() + "'" +
            ", contact2Value='" + getContact2Value() + "'" +
            ", contact3='" + getContact3() + "'" +
            ", contact3Value='" + getContact3Value() + "'" +
            ", contentStatus='" + getContentStatus() + "'" +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
