package ke.co.amini.service;

import ke.co.amini.domain.Organization;
import ke.co.amini.domain.User;
import ke.co.amini.repository.OrganizationRepository;
import ke.co.amini.service.dto.OrganizationDTO;
import ke.co.amini.service.dto.OrganizationUserDTO;
import ke.co.amini.service.dto.RegisterOrganizationDTO;
import ke.co.amini.service.dto.UserDTO;
import ke.co.amini.service.mapper.OrganizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing Organization.
 */
@Service
@Transactional
public class OrganizationService {

    private final Logger log = LoggerFactory.getLogger(OrganizationService.class);

    private final OrganizationRepository organizationRepository;

    private final OrganizationMapper organizationMapper;

    private final UserService userService;

    private final OrganizationUserService organizationUserService;

    public OrganizationService(OrganizationRepository organizationRepository, OrganizationMapper organizationMapper, UserService userService, OrganizationUserService organizationUserService) {
        this.organizationRepository = organizationRepository;
        this.organizationMapper = organizationMapper;
        this.userService = userService;
        this.organizationUserService = organizationUserService;
    }

    /**
     * Save a organization.
     *
     * @param organizationDTO the entity to save
     * @return the persisted entity
     */
    public OrganizationDTO save(OrganizationDTO organizationDTO) {
        log.debug("Request to save Organization : {}", organizationDTO);
        Organization organization = organizationMapper.toEntity(organizationDTO);
        organization = organizationRepository.save(organization);
        return organizationMapper.toDto(organization);
    }

    /**
     * Get all the organizations.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<OrganizationDTO> findAll() {
        log.debug("Request to get all Organizations");
        return organizationRepository.findAll().stream()
            .map(organizationMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one organization by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public OrganizationDTO findOne(Long id) {
        log.debug("Request to get Organization : {}", id);
        Organization organization = organizationRepository.findOne(id);
        return organizationMapper.toDto(organization);
    }

    /**
     * Delete the organization by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Organization : {}", id);
        organizationRepository.delete(id);
    }

    public OrganizationDTO register(RegisterOrganizationDTO registerOrganizationDTO) {
        log.debug("Request to register Organization : {}", registerOrganizationDTO);

        OrganizationDTO organizationDTO = registerOrganizationDTO.toOrganizationDTO();
        organizationDTO.setUuid(UUID.randomUUID().toString());

        Organization organization = organizationMapper.toEntity(organizationDTO);
        organization = organizationRepository.save(organization);

        // save the user
        UserDTO userDTO = registerOrganizationDTO.toUserDTO();
        User user = userService.createUser(userDTO);

        String resetKey = user.getResetKey();

        log.debug("Reset Key = {}", resetKey);

        // set user password
        userService.completePasswordReset(registerOrganizationDTO.getPassword(), resetKey);

        // create OrganizationUser
        OrganizationUserDTO organizationUserDTO = new OrganizationUserDTO();
        organizationUserDTO.setOrganizationId(organizationDTO.getId());
        organizationUserDTO.setOrganizationName(organizationDTO.getName());
        organizationUserDTO.setUsername(user.getLogin());
        organizationDTO.setUuid(UUID.randomUUID().toString());


        return organizationMapper.toDto(organization);
    }
}
