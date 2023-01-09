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

            Person save = new Person("lil3", 32);
            session.save(save);
// в рамках одной транзации возможно создавать новый элемент в бд и возвращать
            Person person = session.get(Person.class, 12);
            System.out.println(person.getName());
            System.out.println(person.getAge());

            session.getTransaction().commit();

        } finally {
            sessionFactory.close();
        }
    }
}
