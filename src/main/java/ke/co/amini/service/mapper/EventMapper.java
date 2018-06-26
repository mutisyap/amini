package ke.co.amini.service.mapper;

import ke.co.amini.domain.*;
import ke.co.amini.service.dto.EventDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Event and its DTO EventDTO.
 */
@Mapper(componentModel = "spring", uses = {GeneralEventMapper.class})
public interface EventMapper extends EntityMapper<EventDTO, Event> {

    @Mapping(source = "generalEvent.id", target = "generalEventId")
    @Mapping(source = "generalEvent.name", target = "generalEventName")
    EventDTO toDto(Event event);

    @Mapping(source = "generalEventId", target = "generalEvent")
    Event toEntity(EventDTO eventDTO);

    default Event fromId(Long id) {
        if (id == null) {
            return null;
        }
        Event event = new Event();
        event.setId(id);
        return event;
    }
}
