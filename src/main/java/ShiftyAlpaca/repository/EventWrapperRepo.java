package ShiftyAlpaca.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ShiftyAlpaca.model.EventWrapper;

@Repository
public interface EventWrapperRepo extends CrudRepository<EventWrapper, Long> {

}
