package ShiftyAlpaca.service;

import ShiftyAlpaca.model.User;
import ShiftyAlpaca.repository.UserRepo;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepo users;

  public UserService(UserRepo users) {
    this.users = users;
  }

  /* Update - update info about an existing user
   *
   */
  public User update(User user) {
    return this.users.save(user);
  }

  /* Create - create new user in repository
   *
   */
  public User create(User user) {
    return this.users.save(user);
  }

  /* Delete - get rid of a user in the repository
   *
   */
//  public void delete(String id) {
//    final User user = this.users.findOne(id);
//    this.users.delete(user);
//  }
//
//  /*
//   *
//   */
//  public List<User> findAll() {
//    return this.users.findAll();
//  }
//
//  /*
//   *
//   */
//  public User findOne(String id) {
//    return this.users.findOne(id);
//  }
}
