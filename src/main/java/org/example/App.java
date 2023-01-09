package org.example;

import org.example.model.Person;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            Person save = new Person("lil7", 32);
            session.save(save);
// в рамках одной транзации возможно создавать новый элемент в бд, возвращать, обновлять и удалять
            Person person = session.get(Person.class, 18);
            System.out.println(person.getName());
            System.out.println(person.getAge());

            person.setName("lil8");
            System.out.println(person.getName());
            session.delete(person);

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }
}
