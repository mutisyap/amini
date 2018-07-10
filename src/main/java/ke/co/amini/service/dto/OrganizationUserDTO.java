package ke.co.amini.service.dto;


import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the OrganizationUser entity.
 */
public class OrganizationUserDTO implements Serializable {

    private Long id;

    @NotNull
    private String username;

    private String uuid;

    private Long organizationId;

    private String organizationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

        OrganizationUserDTO organizationUserDTO = (OrganizationUserDTO) o;
        if(organizationUserDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), organizationUserDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OrganizationUserDTO{" +
            "id=" + getId() +
            ", username='" + getUsername() + "'" +
            ", uuid='" + getUuid() + "'" +
            "}";
    }
}
