package ShiftyAlpaca.service;

import ShiftyAlpaca.model.User;
import ShiftyAlpaca.model.UserIdentity;
import ShiftyAlpaca.repository.UserRepo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private ObjectMapper mapper = new ObjectMapper();
  private UserRepo userRepo;

  public UserService(UserRepo userRepo) { this.userRepo = userRepo; }

  public User findOrCreateUser(JsonNode json) {
    User user = userRepo.findByUserIdent(json.get("team_id").asText(), json.get("event").get("user").asText());
    if (user != null){
      return user;
    }
    return new User(new UserIdentity(json.get("team_id").asText(), json.get("event").get("user").asText()));;
  }

  public void recordNewQuery(JsonNode json) {

  }
}
