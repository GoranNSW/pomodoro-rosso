package rs.goran.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import rs.goran.model.Pomodoro;
import rs.goran.model.Team;
import rs.goran.model.User;

public class TestHibernate {

    public void testFactory() {

        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
                .addAnnotatedClass(Team.class).addAnnotatedClass(Pomodoro.class).addAnnotatedClass(UserDetails.class)
                .addAnnotatedClass(Vehicle.class).buildSessionFactory();

        // Session session = factory.getCurrentSession();
        Session session = factory.openSession();

        try {

            System.out.println("Creating new User object...");

            UserDetails user = new UserDetails();
            user.setUserName("First User");

            Vehicle vehicle = new Vehicle();
            vehicle.setVehicleName("Car");

            Vehicle vehicle2 = new Vehicle();
            vehicle2.setVehicleName("Jeep");

            user.getVehicle().add(vehicle);
            user.getVehicle().add(vehicle2);

            session.beginTransaction();

            // drop table
            // session.createQuery("delete
            // rs.goran.model.User").executeUpdate();

            // System.out.println("Saving the User... " + tempUser.getEmail() +
            // " " + tempUser.getName() + " "
            // + tempUser.getUserId());
            // session.save(tempUser);
            // // session.get(tempUser, id);
            // System.out.println(
            // "Saving the Team... " + tempTeam.getName() + " " +
            // tempTeam.getUser() + " " + tempTeam.getId());
            // session.save(tempTeam);

            session.save(user);
            session.save(vehicle);
            session.save(vehicle2);

            session.getTransaction().commit();
            session.close();

            System.out.println("Done!");
        } finally {
            factory.close();
        }
    }

}
