package ke.co.amini.web.rest;

import com.codahale.metrics.annotation.Timed;
import ke.co.amini.service.GeneralEventService;
import ke.co.amini.web.rest.errors.BadRequestAlertException;
import ke.co.amini.web.rest.util.HeaderUtil;
import ke.co.amini.web.rest.util.PaginationUtil;
import ke.co.amini.service.dto.GeneralEventDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing GeneralEvent.
 */
@RestController
@RequestMapping("/api")
public class GeneralEventResource {

    private final Logger log = LoggerFactory.getLogger(GeneralEventResource.class);

    private static final String ENTITY_NAME = "generalEvent";

    private final GeneralEventService generalEventService;

    public GeneralEventResource(GeneralEventService generalEventService) {
        this.generalEventService = generalEventService;
    }

    /**
     * POST  /general-events : Create a new generalEvent.
     *
     * @param generalEventDTO the generalEventDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new generalEventDTO, or with status 400 (Bad Request) if the generalEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/general-events")
    @Timed
    public ResponseEntity<GeneralEventDTO> createGeneralEvent(@RequestBody GeneralEventDTO generalEventDTO) throws URISyntaxException {
        log.debug("REST request to save GeneralEvent : {}", generalEventDTO);
        if (generalEventDTO.getId() != null) {
            throw new BadRequestAlertException("A new generalEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GeneralEventDTO result = generalEventService.save(generalEventDTO);
        return ResponseEntity.created(new URI("/api/general-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /general-events : Updates an existing generalEvent.
     *
     * @param generalEventDTO the generalEventDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated generalEventDTO,
     * or with status 400 (Bad Request) if the generalEventDTO is not valid,
     * or with status 500 (Internal Server Error) if the generalEventDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/general-events")
    @Timed
    public ResponseEntity<GeneralEventDTO> updateGeneralEvent(@RequestBody GeneralEventDTO generalEventDTO) throws URISyntaxException {
        log.debug("REST request to update GeneralEvent : {}", generalEventDTO);
        if (generalEventDTO.getId() == null) {
            return createGeneralEvent(generalEventDTO);
        }
        GeneralEventDTO result = generalEventService.save(generalEventDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, generalEventDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /general-events : get all the generalEvents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of generalEvents in body
     */
    @GetMapping("/general-events")
    @Timed
    public ResponseEntity<List<GeneralEventDTO>> getAllGeneralEvents(Pageable pageable) {
        log.debug("REST request to get a page of GeneralEvents");
        Page<GeneralEventDTO> page = generalEventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/general-events");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /general-events/:id : get the "id" generalEvent.
     *
     * @param id the id of the generalEventDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the generalEventDTO, or with status 404 (Not Found)
     */
    @GetMapping("/general-events/{id}")
    @Timed
    public ResponseEntity<GeneralEventDTO> getGeneralEvent(@PathVariable Long id) {
        log.debug("REST request to get GeneralEvent : {}", id);
        GeneralEventDTO generalEventDTO = generalEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(generalEventDTO));
    }

    /**
     * DELETE  /general-events/:id : delete the "id" generalEvent.
     *
     * @param id the id of the generalEventDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/general-events/{id}")
    @Timed
    public ResponseEntity<Void> deleteGeneralEvent(@PathVariable Long id) {
        log.debug("REST request to delete GeneralEvent : {}", id);
        generalEventService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
