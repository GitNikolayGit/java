package ru.cherkas.course.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.cherkas.course.models.Buy;
import ru.cherkas.course.models.Clock;
import ru.cherkas.course.models.Sales;
import ru.cherkas.course.models.Sellers;

import java.util.Date;
import java.util.List;

@Component
public class SalesDao {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;
    private final ClockDao clockDao;
    private final SellersDao sellersDao;
    private final BuyDao buyDao;


    @Autowired
    public SalesDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory, ClockDao clockDao, SellersDao sellersDao, BuyDao buyDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
        this.clockDao = clockDao;
        this.sellersDao = sellersDao;
        this.buyDao = buyDao;
    }

    @Transactional
    public List<Sales> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select s from Sales s", Sales.class)
                .getResultList();
    }
    @Transactional
    public Sales show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Sales.class, id);
    }
    // добавить
    @Transactional
    public void save(Sales sales) {
        Session session = sessionFactory.getCurrentSession();
        Clock clock = clockDao.show(sales.getClock().getId());
        // устанавливаем цену для продажи

        Sellers sellers = sellersDao.show(sales.getSellers().getId());
        sales.setDate_sales(new Date());
        sales.setClock(clock);
        sales.setSellers(sellers);
        // устанавливаем цену для продажи
        sales.setEnd_price((clock.getPrice() + clock.getPrice() * sales.PERCENT / 100));
        session.persist(sales);
    }
    // редактировать
    @Transactional
    public void update(int id, Sales updatedSales) {
        Session session = sessionFactory.getCurrentSession();
        Sales sales = session.get(Sales.class, id);
        sales.setDate_sales(updatedSales.getDate_sales());
        sales.setCount_clock(updatedSales.getCount_clock());
        Sellers sellers = sellersDao.show(updatedSales.getSellers().getId());
        sales.setSellers(sellers);
        Clock clock = clockDao.show(updatedSales.getClock().getId());
        sales.setClock(clock);
        sales.setEnd_price(clock.getPrice() * sales.getCount_clock());
    }
    // удалить
    @Transactional
    public void delete(int id) {
        Session session = sessionFactory.getCurrentSession();
        Sales sales = session.get(Sales.class, id);
        Buy buy = buyDao.show(sales.getClock());
        buy.setCount_clock(buy.getCount_clock() + sales.getCount_clock());
        buy.setClock(sales.getClock());
        buyDao.update(buy.getId(), buy);
        session.delete(sales);
    }
}
