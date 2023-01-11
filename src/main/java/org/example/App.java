package org.example;

import org.example.model.Actor;
import org.example.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Actor.class)
                .addAnnotatedClass(Movie.class);

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
        Movie movie = new Movie("Film", 1994);
        Actor actor1 = new Actor("1", 23);
        Actor actor2 = new Actor("2", 34);

        movie.setActors(new ArrayList<>(Arrays.asList(actor1, actor2)));
        actor1.setMovies(new ArrayList<>(Collections.singletonList(movie)));
//        actor2.setMovies(new ArrayList<>(List.of(movie)));

        session.save(movie);
        session.save(actor1);
        session.save(actor2);
    }

    private static void getActors(Session session) {
        Movie movie = session.get(Movie.class, 1);
        System.out.println(movie.getActors());
    }

    private static void getMovies(Session session) {
        Actor actor = session.get(Actor.class, 2);
        System.out.println(actor.getMovies());
    }

    private static void newFilm(Session session) {
        Movie movie = new Movie("Film2", 1998);
        Actor actor = session.get(Actor.class, 1);

//        movie.setActors(List.of(actor));
        movie.setActors(Arrays.asList(actor));
        actor.getMovies().add(movie);

        session.save(movie);
    }
//
//    private static void updatePassport(Session session) {
//        Person person = session.get(Person.class, 1);
//        Passport passport = person.getPassport();
//        passport.setPassportNumber(678789);
//    }
//
    private static void delete(Session session) {
        Actor actor = session.get(Actor.class, 2);
        System.out.println(actor);

        int movieIndex = 0;

        Movie movie = actor.getMovies().get(movieIndex);
        System.out.println(movie);

        actor.getMovies().remove(movieIndex);
        movie.getActors().remove(actor);
    }
}
