package ke.co.amini.web.rest;

import com.codahale.metrics.annotation.Timed;
import ke.co.amini.service.OrganizationUserService;
import ke.co.amini.web.rest.errors.BadRequestAlertException;
import ke.co.amini.web.rest.util.HeaderUtil;
import ke.co.amini.service.dto.OrganizationUserDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing OrganizationUser.
 */
@RestController
@RequestMapping("/api")
public class OrganizationUserResource {

    private final Logger log = LoggerFactory.getLogger(OrganizationUserResource.class);

    private static final String ENTITY_NAME = "organizationUser";

    private final OrganizationUserService organizationUserService;

    public OrganizationUserResource(OrganizationUserService organizationUserService) {
        this.organizationUserService = organizationUserService;
    }

    /**
     * POST  /organization-users : Create a new organizationUser.
     *
     * @param organizationUserDTO the organizationUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new organizationUserDTO, or with status 400 (Bad Request) if the organizationUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/organization-users")
    @Timed
    public ResponseEntity<OrganizationUserDTO> createOrganizationUser(@Valid @RequestBody OrganizationUserDTO organizationUserDTO) throws URISyntaxException {
        log.debug("REST request to save OrganizationUser : {}", organizationUserDTO);
        if (organizationUserDTO.getId() != null) {
            throw new BadRequestAlertException("A new organizationUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrganizationUserDTO result = organizationUserService.save(organizationUserDTO);
        return ResponseEntity.created(new URI("/api/organization-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /organization-users : Updates an existing organizationUser.
     *
     * @param organizationUserDTO the organizationUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated organizationUserDTO,
     * or with status 400 (Bad Request) if the organizationUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the organizationUserDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/organization-users")
    @Timed
    public ResponseEntity<OrganizationUserDTO> updateOrganizationUser(@Valid @RequestBody OrganizationUserDTO organizationUserDTO) throws URISyntaxException {
        log.debug("REST request to update OrganizationUser : {}", organizationUserDTO);
        if (organizationUserDTO.getId() == null) {
            return createOrganizationUser(organizationUserDTO);
        }
        OrganizationUserDTO result = organizationUserService.save(organizationUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, organizationUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /organization-users : get all the organizationUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of organizationUsers in body
     */
    @GetMapping("/organization-users")
    @Timed
    public List<OrganizationUserDTO> getAllOrganizationUsers() {
        log.debug("REST request to get all OrganizationUsers");
        return organizationUserService.findAll();
        }

    /**
     * GET  /organization-users/:id : get the "id" organizationUser.
     *
     * @param id the id of the organizationUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the organizationUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/organization-users/{id}")
    @Timed
    public ResponseEntity<OrganizationUserDTO> getOrganizationUser(@PathVariable Long id) {
        log.debug("REST request to get OrganizationUser : {}", id);
        OrganizationUserDTO organizationUserDTO = organizationUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(organizationUserDTO));
    }

    /**
     * DELETE  /organization-users/:id : delete the "id" organizationUser.
     *
     * @param id the id of the organizationUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/organization-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteOrganizationUser(@PathVariable Long id) {
        log.debug("REST request to delete OrganizationUser : {}", id);
        organizationUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
