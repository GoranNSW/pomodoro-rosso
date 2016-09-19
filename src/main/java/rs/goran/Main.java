package rs.goran;

import org.apache.log4j.Logger;
import org.quartz.SchedulerException;

import rs.goran.model.Pomodoro;
import rs.goran.model.Team;
import rs.goran.model.User;
import rs.goran.service.WebServer;

public class Main {

    private WebServer server;

    final static Logger logger = Logger.getLogger(Main.class);

    public Main() {
        server = new WebServer(8000);
    }

    public void start() throws Exception {
        server.start();
        server.join();
    }

    public static void main(String[] args) throws SchedulerException {
    	
        try {
            new Main().start();
       } catch (Exception e) {
           e.printStackTrace();
       }
        
        logger.info("End.");

    }
}
