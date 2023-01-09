package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "person_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
// в случае, если бд автоматическине не ходит в Sequence за очередным числом. Hibernate берет на себя отвественность за передачу и генерацию айди
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_generator_person")  //
//    @SequenceGenerator(name = "seq_generator_person",                                        // name = название в коде
//    sequenceName = "person_id_seq", allocationSize = 1)   // sequenceName = название в бд, allocationSize = множитель числа из sequence
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    public Person() {}

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
