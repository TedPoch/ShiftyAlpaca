package ShiftyAlpaca.repository;

import ShiftyAlpaca.model.SlackWrapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Spring implements the methods in this interface behind the scenes
 * as necessary to carry out CRUD operations against the chosen DB.
 *
 * This repository pattern replaces the more common DAO pattern also used
 * by Spring programmers.
 *
 */
@Repository
public interface SlackWrapperRepo extends CrudRepository<SlackWrapper, Long> {

}
