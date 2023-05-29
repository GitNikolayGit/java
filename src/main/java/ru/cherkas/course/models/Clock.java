package ru.cherkas.course.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Entity
@Table(name = "Clock")
public class Clock {
    @Id
    @Column(name = "id")
    private int id;
    @NotEmpty(message = "Заполните поле")
    @Column(name = "brand")
    private String brand;
    @NotEmpty(message = "Заполните поле")
    @Column(name = "typeClock")
    private String typeClock;
    @Min(value = 0, message = "Не верная цена")
    @Column(name = "price")
    private int price;

    @OneToOne
    @JoinColumn(name = "manufacturer_id", referencedColumnName = "id")
    private Manufacturer manufacturer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getTypeClock() {
        return typeClock;
    }

    public void setTypeClock(String typeClock) {
        this.typeClock = typeClock;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Clock() { }

    public Clock(String brand, String typeClock, int price, Manufacturer manufacturer) {
        this.brand = brand;
        this.typeClock = typeClock;
        this.price = price;
        this.manufacturer = manufacturer;
    }

    public String toString() {
        return this.getBrand() + " " + this.getManufacturer().getTitle() + " " + this.getManufacturer().getCountry().getTitle() + " Цена: " + this.getPrice();
    }
}
