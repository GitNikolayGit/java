package ru.cherkas.course.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table(name = "Buy")
public class Buy {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "date_buy")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date date_buy;

    @Column(name = "price_buy")
    private int price_buy;

    @Min(value = 0, message = "Не верное количество")
    @Column(name = "count_clock")
    private int count_clock;

    @OneToOne
    @JoinColumn(name = "clock_id", referencedColumnName = "id")
    private Clock clock;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate_buy() {
        return date_buy;
    }

    public void setDate_buy(Date date_buy) {
        this.date_buy = date_buy;
    }

    public int getPrice_buy() {
        return price_buy;
    }

    public void setPrice_buy(int price_buy) {
        this.price_buy = price_buy;
    }

    public int getCount_clock() {
        return count_clock;
    }

    public void setCount_clock(int count_clock) {
        this.count_clock = count_clock;
    }

    public Clock getClock() {
        return clock;
    }

    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public Buy() {}

    public Buy(Date date_buy, int count_clock, Clock clock) {
        this.setDate_buy(date_buy);
        this.setCount_clock(count_clock);
        this.setClock(clock);
        this.setPrice_buy(all_buy_price());
    }
    // вычисление стоимости закупки
    public int all_buy_price(){
        return count_clock * clock.getPrice();
    }
}
