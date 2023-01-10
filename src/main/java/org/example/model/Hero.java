package org.example.model;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
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

    //@OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST)  // каскадирование для метода persist()
    @OneToMany(mappedBy = "owner")
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)      // каскадирование для методов save() и update()
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
    // добавить item текущему hero (тому, кто вызывает)
    public void addItem(Item item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }

        this.items.add(item);
        item.setOwner(this);
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
