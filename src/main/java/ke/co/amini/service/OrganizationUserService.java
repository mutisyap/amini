package ke.co.amini.service;

import ke.co.amini.domain.OrganizationUser;
import ke.co.amini.repository.OrganizationUserRepository;
import ke.co.amini.service.dto.OrganizationUserDTO;
import ke.co.amini.service.mapper.OrganizationUserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing OrganizationUser.
 */
@Service
@Transactional
public class OrganizationUserService {

    private final Logger log = LoggerFactory.getLogger(OrganizationUserService.class);

    private final OrganizationUserRepository organizationUserRepository;

    private final OrganizationUserMapper organizationUserMapper;

    public OrganizationUserService(OrganizationUserRepository organizationUserRepository, OrganizationUserMapper organizationUserMapper) {
        this.organizationUserRepository = organizationUserRepository;
        this.organizationUserMapper = organizationUserMapper;
    }

    /**
     * Save a organizationUser.
     *
     * @param organizationUserDTO the entity to save
     * @return the persisted entity
     */
    public OrganizationUserDTO save(OrganizationUserDTO organizationUserDTO) {
        log.debug("Request to save OrganizationUser : {}", organizationUserDTO);
        OrganizationUser organizationUser = organizationUserMapper.toEntity(organizationUserDTO);
        organizationUser = organizationUserRepository.save(organizationUser);
        return organizationUserMapper.toDto(organizationUser);
    }

    /**
     * Get all the organizationUsers.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OrganizationUserDTO> findAll() {
        log.debug("Request to get all OrganizationUsers");
        return organizationUserRepository.findAll().stream()
            .map(organizationUserMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one organizationUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OrganizationUserDTO findOne(Long id) {
        log.debug("Request to get OrganizationUser : {}", id);
        OrganizationUser organizationUser = organizationUserRepository.findOne(id);
        return organizationUserMapper.toDto(organizationUser);
    }

    /**
     * Delete the organizationUser by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete OrganizationUser : {}", id);
        organizationUserRepository.delete(id);
    }
}
