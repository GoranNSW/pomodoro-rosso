package rs.goran.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import rs.goran.model.User;

public interface UserDao extends JpaRepository<User, Long> {

    User save(User user);
    List<User> findAll();
    User findById(String id);
    User findByName(String name);
    User findByEmail(String email);
}
