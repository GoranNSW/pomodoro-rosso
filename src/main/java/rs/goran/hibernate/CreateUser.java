package rs.goran.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import rs.goran.model.User;

public class CreateUser {

    public void testFactory() {

        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class)
                .buildSessionFactory();

        Session session = factory.getCurrentSession();

        try {

            System.out.println("Creating new User object...");
            User tempUser = new User("goran@email.com", "goran");

            session.beginTransaction();

            // drop table
            session.createQuery("delete rs.goran.model.User").executeUpdate();

            System.out.println("Saving the User...");
            session.save(tempUser);

            session.getTransaction().commit();

            System.out.println("Done!");
        } finally {
            factory.close();
        }
    }

}
