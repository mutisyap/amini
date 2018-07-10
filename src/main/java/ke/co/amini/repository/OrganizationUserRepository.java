package ke.co.amini.repository;

import ke.co.amini.domain.OrganizationUser;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the OrganizationUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrganizationUserRepository extends JpaRepository<OrganizationUser, Long> {

}
