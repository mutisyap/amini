package ke.co.amini.service.dto;

import java.io.Serializable;
import java.util.Set;

public class RegisterOrganizationDTO implements Serializable {

    private String fullName;

    private String emailAddress;

    private String password;

    private Set<String> roles;

    private Long locationId;

    private String locationName;

    private String organizationName;

    private String description;

    private Float latitude;

    private Float longitude;


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public OrganizationDTO toOrganizationDTO() {
        OrganizationDTO organizationDTO = new OrganizationDTO();

        organizationDTO.setLocationId(this.locationId);
        organizationDTO.setLocationName(this.locationName);
        organizationDTO.setName(this.organizationName);
        organizationDTO.setDescription(this.description);
        organizationDTO.setLatitude(this.latitude);
        organizationDTO.setLongitude(this.longitude);

        return organizationDTO;
    }

    public UserDTO toUserDTO() {
        UserDTO userDTO = new UserDTO();

        userDTO.setLogin(this.emailAddress);
        userDTO.setFirstName(splitString(fullName)[0]);
        userDTO.setLastName(splitString(fullName)[1]);
        userDTO.setAuthorities(this.roles);
        userDTO.setCreatedBy("self");
        userDTO.setEmail(this.emailAddress);

        return userDTO;
    }

    private String[] splitString(String fullName) {
        if (fullName == null) {
            return null;
        }
        return fullName.trim().split(" ", 2);
    }

    @Override
    public String toString() {
        return "RegisterOrganizationDTO{" +
            "fullName='" + fullName + '\'' +
            ", emailAddress='" + emailAddress + '\'' +
            ", password='" + password + '\'' +
            ", roles=" + roles +
            ", locationId=" + locationId +
            ", locationName='" + locationName + '\'' +
            ", organizationName='" + organizationName + '\'' +
            ", description='" + description + '\'' +
            ", latitude=" + latitude +
            ", longitude=" + longitude +
            '}';
    }
}
