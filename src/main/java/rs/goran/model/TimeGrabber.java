package rs.goran.model;

import java.util.ArrayList;
import java.util.List;

public class TimeGrabber implements Subject {

    private List<Observer> observers;

    private User user;

    public TimeGrabber(User user) {
        this.user = user;
        observers = new ArrayList<>();
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
            observer.update(this.user);
        }
    }

    // public void setTimes(User user) {
    // this.user = user;
    // for (User user : team.getUsers()) {
    // //TODO - Refresh times and put them to the database, and show them to the
    // other team members
    // System.out.println(user.getName() + " has " + (user.getPomodoroTime() /
    // (60*1000)) + " minutes left in his pomodoro.");
    // }
    // notifyObserver();
    // }

}
