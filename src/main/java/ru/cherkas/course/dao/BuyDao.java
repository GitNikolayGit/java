package ru.cherkas.course.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.cherkas.course.models.Buy;
import ru.cherkas.course.models.Clock;

import java.util.Date;
import java.util.List;

@Component
public class BuyDao {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;
    private final ClockDao clockDao;


    @Autowired
    public BuyDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory, ClockDao clockDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
        this.clockDao = clockDao;
    }

    @Transactional
    public List<Buy> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select s from Buy s", Buy.class)
                .getResultList();
    }
    @Transactional
    public Buy show(Clock clock) {
        return jdbcTemplate.query("select * from Buy  where clock_id = ?", new Object[]{clock.getId()},
                        new BeanPropertyRowMapper<>(Buy.class)).stream().findAny().orElse(null);

       // Session session = sessionFactory.getCurrentSession();
       // String sql = String.format("from Buy where clock.id = %d", clock_param.getId());
       // Query<Buy> buy = session.createQuery(sql, Buy.class);
       // return (Buy) buy;
    }
    @Transactional
    public Buy show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Buy.class, id);
    }
    // добавить
    @Transactional
    public void save(Buy buy) {
        Session session = sessionFactory.getCurrentSession();
        Clock clock = clockDao.show(buy.getClock().getId());
        buy.setPrice_buy(clock.getPrice() * buy.getCount_clock());
        buy.setDate_buy(new Date());
        buy.setClock(clock);

        session.persist(buy);
    }
    // редактировать
    @Transactional
    public void update(int id, Buy updatedBuy) {
        Session session = sessionFactory.getCurrentSession();
        Buy buy = session.get(Buy.class, id);
        buy.setDate_buy(updatedBuy.getDate_buy());
        buy.setCount_clock(updatedBuy.getCount_clock());
        buy.setCount_clock(updatedBuy.getCount_clock());
        Clock clock = clockDao.show(updatedBuy.getClock().getId());
        buy.setClock(clock);
        buy.setPrice_buy(clock.getPrice() * buy.getCount_clock());
    }
}