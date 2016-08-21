package rs.goran.model;

public class TimeObserver implements Observer {

    private User user;

    private static int observerIDTracker = 0;
    private int observerID;

    private Subject timeGrabber;

    public TimeObserver(Subject timeGrabber) {
        this.timeGrabber = timeGrabber;

        timeGrabber.register(this);
    }

    @Override
    public void update(User user) {
        this.user = user;

        readTimes(user);
    }

    public void readTimes(User user) {
        for (Team team : user.getTeams()) {
            // TODO - find all users from team
        }

    }

}
