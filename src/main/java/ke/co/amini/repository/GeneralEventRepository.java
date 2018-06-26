package ke.co.amini.repository;

import ke.co.amini.domain.GeneralEvent;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the GeneralEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GeneralEventRepository extends JpaRepository<GeneralEvent, Long> {

}
