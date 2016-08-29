package rs.goran.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import rs.goran.model.Pomodoro;
import rs.goran.model.Team;
import rs.goran.model.User;

public class TestHibernatePomodoro {

    public void testFactory() {

        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
                .addAnnotatedClass(Team.class).addAnnotatedClass(Pomodoro.class).addAnnotatedClass(UserDetails.class)
                .addAnnotatedClass(Vehicle.class).buildSessionFactory();

        Session session = factory.openSession();
        session.beginTransaction();

        try {

            System.out.println("Creating new User object...");

            User user = new User("email@mail.com", "goran");
            Team team = new Team("Team", user.getUserId());

            session.save(user);
            // session.save(team);

            // delete user
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

            session.getTransaction().commit();
            session.close();

            user = null;

            session = factory.openSession();
            session.beginTransaction();

            try {
                for (int i = 1; i < 20; i++) {
                    user = (User) session.get(User.class, i);
                    if (user.getName() != null) {
                        System.out.println(user.getName());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            session.close();

            System.out.println("Done!");
        } finally {
            factory.close();
        }
    }

}
