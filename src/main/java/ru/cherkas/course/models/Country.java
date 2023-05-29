package ru.cherkas.course.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Country")
public class Country {
    @Id
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Заполните поле")
    @Column(name = "title")
    private String title;

    public Country() {}

    public Country(String title) {
        this.title = title;
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
}
