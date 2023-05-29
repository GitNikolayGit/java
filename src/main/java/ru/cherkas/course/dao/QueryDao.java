package ru.cherkas.course.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.cherkas.course.models.Buy;
import ru.cherkas.course.models.Clock;
import ru.cherkas.course.models.Country;
import ru.cherkas.course.models.Sales;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Component
public class QueryDao {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    public QueryDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }
    // 1.	Вывести информацию о часах заданного типа (механические часы, смарт-часы,
    @Transactional
    public List<Clock> query1(String typeClock){
        Session session = sessionFactory.getCurrentSession();
        String sql = String.format("from Clock where typeClock = '%s'", typeClock);
        Query<Clock> query = session.createQuery(sql, Clock.class);
        return query.list();
    }
    // 2.	Вывести информацию о часах заданного типа, цена на которые в заданном диапазоне значений
    @Transactional
    public List<Clock> query2(String typeClock, int min, int max){
        Session session = sessionFactory.getCurrentSession();
        String sql = String.format("from Clock where typeClock = '%s' and price between '%d' and '%d'", typeClock, min, max);
        Query<Clock> query = session.createQuery(sql, Clock.class);
        return query.list();
    }
    // 3.	Вывести информацию о часах, произведенных в заданной стране
    @Transactional
    public List<Clock> query3(Country country){
        Session session = sessionFactory.getCurrentSession();
        String sql = String.format("from Clock where manufacturer.country.id = %d", country.getId());
        return session.createQuery(sql, Clock.class).list();
    }
    // 4.	Вывести продажи в магазине за указанный период
    @Transactional
    public List<Buy> query4(String date_min, String date_max){

        return null;
    }

    // 5.	Вывести поставки в магазине за указанный период
    public List<Buy> query5(){
        return null;
    }
}
