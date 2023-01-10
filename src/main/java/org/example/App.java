package org.example;

import org.example.model.Passport;
import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Person.class)
                .addAnnotatedClass(Passport.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            delete(session);

            session.getTransaction().commit();


        } finally {
            sessionFactory.close();
        }
    }

    private static void save(Session session) {
        Person person = new Person("Ivan", 32);
        Passport passport = new Passport(12345);
        person.setPassport(passport);

        session.save(person);
    }

    private static void getPerson(Session session) {
        Passport passport = session.get(Passport.class, 1);
        Person person = passport.getPerson();
    }

    private static void updatePassport(Session session) {
        Person person = session.get(Person.class, 1);
        Passport passport = person.getPassport();
        passport.setPassportNumber(678789);
    }

    private static void delete(Session session) {
        Person person = session.get(Person.class, 1);
        session.remove(person);
    }
}
