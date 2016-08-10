package rs.goran;

import controller.Team;
import controller.TimeGrabber;
import controller.TimeObserver;

public class App {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
			System.out.println("Test");
			
			Team team = new Team("firstTeam");
			
			TimeGrabber timeGrabber = new TimeGrabber(team);
			
			TimeObserver timeObserver = new TimeObserver(timeGrabber);
			
			timeGrabber.setTeamTimes(team);
	}

}
