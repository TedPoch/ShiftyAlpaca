package ShiftyAlpaca.repository;

import ShiftyAlpaca.model.Event;
import org.springframework.data.repository.CrudRepository;

public interface EventRepo extends CrudRepository<Event, Long> {
}
