package ru.cherkas.course.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "manufacturer")
public class Manufacturer {
    @Id
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Заполните поле")
    @Column(name = "title")
    private String title;
   // @NotEmpty(message = "Заполните поле")
    //@Column(name = "country_id")
    @OneToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id")
    private Country country;

    public Manufacturer() {}

    public Manufacturer(String title, Country country) {
        this.title = title;
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
