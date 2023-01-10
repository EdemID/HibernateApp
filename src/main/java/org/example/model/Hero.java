package org.example.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Hero")
public class Hero {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column(name = "experience")
    private Integer exp;

    @OneToMany(mappedBy = "owner")
    private List<Item> items;

    public Hero() {
    }

    public Hero(String name, Integer exp) {
        this.name = name;
        this.exp = exp;
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

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", exp='" + exp + '\'' +
                '}';
    }
}
