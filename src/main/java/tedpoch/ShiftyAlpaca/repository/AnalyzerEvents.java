package tedpoch.ShiftyAlpaca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tedpoch.ShiftyAlpaca.model.EventWrapper;

import java.util.Optional;

@Repository
public interface AnalyzerEvents extends JpaRepository<EventWrapper, String> {

  @Override
  <S extends EventWrapper> S save(S s);

  @Override
  Optional<EventWrapper> findById(String app_id);

  @Override
  long count();

  @Override
  void deleteById(String app_id);

  @Override
  void delete(EventWrapper eventWrapper);
}
