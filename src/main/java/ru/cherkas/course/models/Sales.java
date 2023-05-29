package ru.cherkas.course.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;

import java.util.Date;

@Entity
@Table(name = "Sales")
public class Sales {
    @Transient
    // процент добавочной стоимости
    public final int PERCENT = 10;

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "date_sales")
    @Temporal(TemporalType.DATE)
    private Date date_sales;

    @OneToOne
    @JoinColumn(name = "sellers_id", referencedColumnName = "id")
    private Sellers sellers;
    @OneToOne
    @JoinColumn(name = "clock_id", referencedColumnName = "id")
    private Clock clock;

    @Min(value = 0, message = "Не верная цена")
    @Column(name = "end_price")
    private int end_price;

    @Min(value = 0, message = "Не верное количество")
    @Column(name = "count_clock")
    private int count_clock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_sales() {
        return date_sales;
    }

    public void setDate_sales(Date date_sales) {
        this.date_sales = date_sales;
    }

    public Sellers getSellers() {
        return sellers;
    }

    public void setSellers(Sellers sellers) {
        this.sellers = sellers;
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public int getEnd_price() {
        return end_price;
    }

    public void setEnd_price(int end_price) {
        this.end_price = end_price;
    }

    public int getCount_clock() {
        return count_clock;
    }

    public void setCount_clock(int count_clock) {
        this.count_clock = count_clock;
    }

    public Sales() {}

    public Sales(Date date_sales, Sellers sellers, Clock clock, int end_price, int count_clock) {
        this.date_sales = date_sales;
        this.sellers = sellers;
        this.clock = clock;
        this.end_price = end_price;
        this.count_clock = count_clock;
    }
    // сумма продажи
    public int sum_sales(){
        return (clock.getPrice() + clock.getPrice() * PERCENT / 100) * this.getCount_clock();
    }
}
