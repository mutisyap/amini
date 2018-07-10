package ke.co.amini.service.mapper;

import ke.co.amini.domain.*;
import ke.co.amini.service.dto.OrganizationUserDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity OrganizationUser and its DTO OrganizationUserDTO.
 */
@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface OrganizationUserMapper extends EntityMapper<OrganizationUserDTO, OrganizationUser> {

    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "organization.name", target = "organizationName")
    OrganizationUserDTO toDto(OrganizationUser organizationUser);

    @Mapping(source = "organizationId", target = "organization")
    OrganizationUser toEntity(OrganizationUserDTO organizationUserDTO);

    default OrganizationUser fromId(Long id) {
        if (id == null) {
            return null;
        }
        OrganizationUser organizationUser = new OrganizationUser();
        organizationUser.setId(id);
        return organizationUser;
    }
}
