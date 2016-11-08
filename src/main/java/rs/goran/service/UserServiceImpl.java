package rs.goran.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import rs.goran.dao.UserDao;
import rs.goran.model.Pomodoro;
import rs.goran.model.Team;
import rs.goran.model.User;
import rs.goran.service.interfaces.UserService;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    private static final Logger logger = Logger.getLogger(UserServiceImpl.class);

    @Override
    public User save(User user) {
        return userDao.save(user);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findById(String id) {
        return userDao.findById(id);
    }

    @Override
    public User findByName(String name) {
        return userDao.findByName(name);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    public void addUserToTeam(User user, Team team) {
        user.addTeam(team);
    }
    
    public void addPomodoroToUser(Pomodoro pomodoro, User user) {
        user.addPomodoro(pomodoro);
    }

}
