package rs.goran;

import org.apache.log4j.Logger;

import rs.goran.configuration.WebServer;


public class PomodoroApp {

    private static final Logger logger = Logger.getLogger(PomodoroApp.class);

    public static void main(String[] args) {
        try {
            new WebServer().startJetty();
        } catch (Exception e) {
            logger.error("Webserver start failure.");
        }

    }
}
