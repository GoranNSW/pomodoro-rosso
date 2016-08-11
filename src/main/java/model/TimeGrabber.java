package model;

import java.util.ArrayList;

public class TimeGrabber implements Subject {

	private ArrayList<Observer> observers;
	
	private Team team;
	
	public TimeGrabber(Team team) {
		this.team = team;
		observers = new ArrayList<Observer>();		
	}
	
	@Override
	public void register(Observer newObserver) {
		observers.add(newObserver);		
	}

	@Override
	public void unregister(Observer deleteObserver) {		
		observers.remove(deleteObserver);		
	}

	@Override
	public void notifyObserver() {
		for (Observer observer : observers) {
			observer.update(this.team);	
		}
			
	}
	
	public void setTeamTimes(Team team) {
		this.team = team;
		for (User user : team.getUsers()) {
			//TODO - Refresh times and put them to the database, and show them to the other team members
			System.out.println(user.getName() + " has " + (user.getPomodoroTime() / (60*1000)) + " minutes left in his pomodoro.");
		}
		notifyObserver();
	}

}
