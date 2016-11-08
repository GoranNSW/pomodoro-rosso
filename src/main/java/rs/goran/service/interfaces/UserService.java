package rs.goran.service.interfaces;

import java.util.List;

import rs.goran.model.Team;
import rs.goran.model.User;

public interface UserService {
    
    User save(User user);
    List<User> findAll();
    User findById(String id);
    User findByName(String userName);
    User findByEmail(String email);
}
