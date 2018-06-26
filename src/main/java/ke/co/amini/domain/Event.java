package ke.co.amini.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Event.
 */
@Entity
@Table(name = "tbl_events")
public class Event implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "day")
    private LocalDate day;

    @Column(name = "description")
    private String description;

    @Column(name = "language")
    private String language;

    @Column(name = "content_status")
    private String contentStatus;

    @Column(name = "uuid")
    private String uuid;

    @ManyToOne
    private GeneralEvent generalEvent;

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

    public Event name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDay() {
        return day;
    }

    public Event day(LocalDate day) {
        this.day = day;
        return this;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public String getDescription() {
        return description;
    }

    public Event description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public Event language(String language) {
        this.language = language;
        return this;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getContentStatus() {
        return contentStatus;
    }

    public Event contentStatus(String contentStatus) {
        this.contentStatus = contentStatus;
        return this;
    }

    public void setContentStatus(String contentStatus) {
        this.contentStatus = contentStatus;
    }

    public String getUuid() {
        return uuid;
    }

    public Event uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public GeneralEvent getGeneralEvent() {
        return generalEvent;
    }

    public Event generalEvent(GeneralEvent generalEvent) {
        this.generalEvent = generalEvent;
        return this;
    }

    public void setGeneralEvent(GeneralEvent generalEvent) {
        this.generalEvent = generalEvent;
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
        Event event = (Event) o;
        if (event.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), event.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Event{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", day='" + getDay() + "'" +
            ", description='" + getDescription() + "'" +
            ", language='" + getLanguage() + "'" +
            ", contentStatus='" + getContentStatus() + "'" +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
