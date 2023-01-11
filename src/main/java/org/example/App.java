package org.example;

import org.example.model.Hero;
import org.example.model.Item;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 */
public class App {

    public static void main(String[] args) {
        Configuration configuration = new Configuration()
                .addAnnotatedClass(Hero.class)
                .addAnnotatedClass(Item.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();
        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            addItem(session);

            session.getTransaction().commit();

            System.out.println();

        } finally {
            sessionFactory.close();
        }
    }

    private static List<Item> getHeroItems(Session session) {
        Hero hero = session.get(Hero.class, 3);
        // для того, чтобы Hibernate сделал за нас SQL-запрос, нужно вызывать getter в рамках транзакции,
        // иначе getter работает просто как с объектом
        return hero.getItems();
    }

    private static Hero getItemHero(Session session) {
        Item item = session.get(Item.class, 5);

        return item.getOwner();
    }

    private static void save(Session session) {
        Hero hero = session.get(Hero.class, 2);

        Item newItem = new Item("Item from Hibernate", hero);
        // Hibernate кэширует объекты для оптимизации быстродействия и памяти,
        // поэтому желательно задавать отношение с двух сторон, чтобы и в кэше, и в бд были актуальные изменения
        // Hibernate при изменении таблицы руководствуется отношением на дочерней стороне
        hero.getItems().add(newItem);

        session.save(newItem);

        System.out.println(hero.getItems());
    }

    private static void saveHeroAndItem(Session session) {
        Hero hero = new Hero("Test hero", 40);
        Item newItem = new Item("Item2 from Hibernate", hero);
        hero.setItems(new ArrayList<>(Collections.singletonList(newItem)));

        // Сохраняем сначала в родительскую таблицу, а затем в дочернюю,
        // так как в Hibernate пока не настроено каскадирование
        session.save(hero);
        session.save(newItem);
    }

    private static void deleteItems(Session session) {
        Hero hero = session.get(Hero.class, 4);

        List<Item> items = hero.getItems();

        // Удаляются из таблицы SQL
        for (Item item : items) {
            session.remove(item);
        }
        // очищаем в кэше Hibernate
        hero.getItems().clear();
    }

    private static void deleteHero(Session session) {
        Hero hero = session.get(Hero.class, 4);
        // для строк в таблице Item, которые имеют связь с текущей строкой таблицы Hero,
        // проставятся значения null при удалении строки из Таблицы Hero,
        // так как на стороне бд задали каскадирование
        session.remove(hero);
//        hero.getItems().forEach(item -> item.setOwner(null));
    }

    private static void update(Session session) {
        Hero hero = session.get(Hero.class, 4);
        Item item = session.get(Item.class, 5);

        // Операция для кэша Хибера - удаляем у прежнего владельца предмет
        item.getOwner().getItems().remove(item);
        // Операция для SQL - назначаем связь
        item.setOwner(hero);
        // Операция для кэша Хибера - назначаем обратную связь
        hero.getItems().add(item);
    }

    private static void cascade(Session session) {
        Hero hero = new Hero("Test cascading", 40);
        Item item = new Item("Item by cascading", hero);
        hero.setItems(new ArrayList<>(Collections.singletonList(item)));
        session.save(hero);
//        session.persist(hero);
    }

    private static void addItem(Session session) {
        Hero hero = new Hero("Test", 23);
        hero.addItem(new Item("Item1"));
        hero.addItem(new Item("Item2"));
        hero.addItem(new Item("Item3"));

        session.save(hero);
    }

    private static void init(SessionFactory factory) {
        Session session = factory.getCurrentSession();
        session.beginTransaction();

        Hero hero = session.get(Hero.class, 1);
        System.out.println("Получили героя из таблицы");

        session.getTransaction().commit();

        System.out.println("Сессия закончилась");
        // hero перешел из состояния Persistent в состояние Detached
        // Открываем сессию и транзакцию, сделать это можно в любом другом месте
        session = factory.getCurrentSession();
        session.beginTransaction();

        System.out.println("Внутри второй транзакции");
        hero = (Hero) session.merge(hero);
//  подгрузить связанные ленивые сущности - это помогает сохранять в объект элементы таблицы Many для работы с объектом при состоянии Detached
        // Первый вариант
        Hibernate.initialize(hero.getItems());
        // Второй вариант
//        List<Item> items = session.createQuery("SELECT i from Item i where i.owner.id =: heroId", Item.class)
//                        .setParameter("heroId", hero.getId()).getResultList();
//        System.out.println(items);

        session.getTransaction().commit();

        System.out.println("Вне второй сессии");
//  это сработает так как связаные Item были загружены во второй сессии
        System.out.println(hero.getItems());
    }
}
