package ke.co.amini.service;

import ke.co.amini.domain.GeneralEvent;
import ke.co.amini.repository.GeneralEventRepository;
import ke.co.amini.service.dto.GeneralEventDTO;
import ke.co.amini.service.mapper.GeneralEventMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing GeneralEvent.
 */
@Service
@Transactional
public class GeneralEventService {

    private final Logger log = LoggerFactory.getLogger(GeneralEventService.class);

    private final GeneralEventRepository generalEventRepository;

    private final GeneralEventMapper generalEventMapper;

    public GeneralEventService(GeneralEventRepository generalEventRepository, GeneralEventMapper generalEventMapper) {
        this.generalEventRepository = generalEventRepository;
        this.generalEventMapper = generalEventMapper;
    }

    /**
     * Save a generalEvent.
     *
     * @param generalEventDTO the entity to save
     * @return the persisted entity
     */
    public GeneralEventDTO save(GeneralEventDTO generalEventDTO) {
        log.debug("Request to save GeneralEvent : {}", generalEventDTO);
        GeneralEvent generalEvent = generalEventMapper.toEntity(generalEventDTO);
        generalEvent = generalEventRepository.save(generalEvent);
        return generalEventMapper.toDto(generalEvent);
    }

    /**
     * Get all the generalEvents.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<GeneralEventDTO> findAll() {
        log.debug("Request to get all GeneralEvents");
        return generalEventRepository.findAll().stream()
            .map(generalEventMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one generalEvent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public GeneralEventDTO findOne(Long id) {
        log.debug("Request to get GeneralEvent : {}", id);
        GeneralEvent generalEvent = generalEventRepository.findOne(id);
        return generalEventMapper.toDto(generalEvent);
    }

    /**
     * Delete the generalEvent by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete GeneralEvent : {}", id);
        generalEventRepository.delete(id);
    }
}
