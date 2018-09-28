package tedpoch.ShiftyAlpaca.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tedpoch.ShiftyAlpaca.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnalyzerUsers extends JpaRepository<User, String> {

  @Override
  <S extends User> S save(S s);

  @Override
  Optional<User> findById(String id);

  @Override
  boolean existsById(String id);

  @Override
  long count();

  @Override
  void deleteById(String id);

  @Override
  void delete(User user);

  List<User> findByName(String name);
  List<User> findByNameIgnoreCaseStartsWith(String name);

}
