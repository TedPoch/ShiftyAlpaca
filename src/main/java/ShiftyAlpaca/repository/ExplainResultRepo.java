package ShiftyAlpaca.repository;

import ShiftyAlpaca.model.ExplainResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/** Handles storage of the results of ANALYZE events that are run at a user's request
 * against a specific database in production. The table will have relations with the users
 * and event_wrappers tables in order to track results.
 *
 */
@Repository
public interface ExplainResultRepo extends CrudRepository<ExplainResult, Long> {
}
