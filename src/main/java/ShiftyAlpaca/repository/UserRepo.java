package ShiftyAlpaca.repository;

import ShiftyAlpaca.model.UserIdentity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ShiftyAlpaca.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, UserIdentity> {

}