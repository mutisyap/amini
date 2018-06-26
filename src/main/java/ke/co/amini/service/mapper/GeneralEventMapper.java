package ke.co.amini.service.mapper;

import ke.co.amini.domain.*;
import ke.co.amini.service.dto.GeneralEventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity GeneralEvent and its DTO GeneralEventDTO.
 */
@Mapper(componentModel = "spring", uses = {OrganizationMapper.class})
public interface GeneralEventMapper extends EntityMapper<GeneralEventDTO, GeneralEvent> {

    @Mapping(source = "organization.id", target = "organizationId")
    @Mapping(source = "organization.name", target = "organizationName")
    GeneralEventDTO toDto(GeneralEvent generalEvent);

    @Mapping(source = "organizationId", target = "organization")
    GeneralEvent toEntity(GeneralEventDTO generalEventDTO);

    default GeneralEvent fromId(Long id) {
        if (id == null) {
            return null;
        }
        GeneralEvent generalEvent = new GeneralEvent();
        generalEvent.setId(id);
        return generalEvent;
    }
}
