package model;

public class TimeObserver implements Observer {

	private Team team;
	
	private static int observerIDTracker = 0;
	
	private int observerID;
	
	private Subject timeGrabber;
	
	public TimeObserver (Subject timeGrabber) {
		
		this.timeGrabber = timeGrabber;
		this.observerID = ++observerIDTracker;
		
		timeGrabber.register(this);
	}
	@Override
	public void update(Team team) {
		this.team = team;
		
		readTimes(team);
		
	}
	
	public void readTimes(Team team) {
		this.team = team;
		
		
		
	}

}
