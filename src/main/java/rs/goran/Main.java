package rs.goran;

import rs.goran.jdbc.TestJdbc;
import rs.goran.service.WebServer;

public class Main {

    private WebServer server;

    public Main() {
        server = new WebServer(8000);
    }

    public void start() throws Exception {
        server.start();
        server.join();
    }

    public static void main(String[] args) {

        // Start web service

        try {
            new Main().start();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // User user = new User("email@email.com", "Goran");
        // Team team = new Team("firstTeam", user.getUserId());
        //
        // user.getTeams().add(team);
        // User user2 = new User("asd@asd.com", "Nobody");
        //
        // TimeGrabber timeGrabber = new TimeGrabber(user);
        //
        // TimeObserver timeObserver = new TimeObserver(timeGrabber);

        // timeGrabber.setTeamTimes(team);
        // user2.setPomodoroTime(10);
        // team.deleteUser("email@email.com");
        // timeGrabber.setTeamTimes(team);

        TestJdbc testJdbc = new TestJdbc();
        testJdbc.test();

        // TestHibernate testHibernate = new TestHibernate();
        // testHibernate.testFactory();

        // TestHibernatePomodoro testHibernatePomodoro = new
        // TestHibernatePomodoro();
        // testHibernatePomodoro.testFactory();

        System.out.println("End.");

    }

}
