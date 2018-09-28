package tedpoch.ShiftyAlpaca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tedpoch.ShiftyAlpaca.model.Response;

import java.util.Optional;

@Repository
public interface AnalyzerResponses extends JpaRepository<Response, String> {
  @Override
  <S extends Response> S save(S s);

  @Override
  Optional<Response> findById(String app_id);

  @Override
  long count();

  @Override
  void deleteById(String app_id);

  @Override
  void delete(Response response);
}
