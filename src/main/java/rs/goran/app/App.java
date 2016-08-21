package rs.goran.app;

import rs.goran.hibernate.CreateUser;
import rs.goran.jdbc.TestJdbc;
import rs.goran.model.Team;
import rs.goran.model.TimeGrabber;
import rs.goran.model.TimeObserver;
import rs.goran.model.User;

public class App {

    public static void main(String[] args) {

        Team team = new Team("firstTeam");
        User user = new User("email@email.com", "Goran");
        user.addTeam("firstTeam");
        User user2 = new User("asd@asd.com", "Nobody");

        TimeGrabber timeGrabber = new TimeGrabber(user);

        TimeObserver timeObserver = new TimeObserver(timeGrabber);

        // timeGrabber.setTeamTimes(team);
        // user2.setPomodoroTime(10);
        // team.deleteUser("email@email.com");
        // timeGrabber.setTeamTimes(team);

        TestJdbc testJdbc = new TestJdbc();
        testJdbc.test();

        CreateUser createUser = new CreateUser();
        createUser.testFactory();
    }

}
