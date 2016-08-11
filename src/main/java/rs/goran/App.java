package rs.goran;

import model.Team;
import model.TimeGrabber;
import model.TimeObserver;
import model.User;

public class App {

	public static void main(String[] args) {
			
			Team team = new Team("firstTeam");
			User user = new User("email@email.com", "Goran");
			team.addUser(user);
			User user2 = new User("asd@asd.com", "Nobody");
			team.addUser(user2);
			
			TimeGrabber timeGrabber = new TimeGrabber(team);
			
			TimeObserver timeObserver = new TimeObserver(timeGrabber);
			
			timeGrabber.setTeamTimes(team);
			user2.setPomodoroTime(10);
			timeGrabber.setTeamTimes(team);
	}

}
