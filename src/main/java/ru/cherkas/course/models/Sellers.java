package ru.cherkas.course.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Sellers")
public class Sellers {
    @Id
    @Column(name = "id")
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "surname")
    @NotEmpty(message = "Заполните поле")
    private String surname;
    @Column(name = "firstName")
    @NotEmpty(message = "Заполните поле")
    private String firstName;
    @Column(name = "patronymic")
    @NotEmpty(message = "Заполните поле")
    private String patronymic;
    @Column(name = "passport")
    @NotEmpty(message = "Заполните поле")
    private String passport;
    @Column(name = "percent")
    @Min(value = 0, message = "Не верный процент")
    private int percent;
    public Sellers() { }

    public Sellers(String surname, String firstName, String patronymic, String passport, int percent) {
        this.surname = surname;
        this.firstName = firstName;
        this.patronymic = patronymic;
        this.passport = passport;
        this.percent = percent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    @Override
    public String toString() {
        return surname + ' ' + firstName + ' ' + patronymic + ' ' + passport;
    }
}
