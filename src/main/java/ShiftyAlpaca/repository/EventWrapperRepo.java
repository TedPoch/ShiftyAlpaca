package ShiftyAlpaca.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ShiftyAlpaca.model.EventWrapper;

/** Spring implements the methods in this interface behind the scenes
 * as necessary to carry out CRUD operations against the chosen DB.
 *
 * This repository pattern replaces the more common DAO pattern also used
 * by Spring programmers.
 *
 */
@Repository
public interface EventWrapperRepo extends CrudRepository<EventWrapper, Long> {

}
