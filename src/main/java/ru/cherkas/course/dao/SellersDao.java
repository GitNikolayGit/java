package ru.cherkas.course.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.cherkas.course.models.Country;
import ru.cherkas.course.models.Sellers;

import java.util.List;
import java.util.Optional;

@Component
public class SellersDao {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    @Autowired
    public SellersDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }

    @Transactional
    public List<Sellers> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select s from Sellers s", Sellers.class)
                .getResultList();
    }

    public Optional<Sellers> show(String passport){
        return jdbcTemplate.query("select * from Sellers where passport = ?", new Object[]{passport},
                new BeanPropertyRowMapper<>(Sellers.class)).stream().findAny();
    }
    @Transactional
    public Sellers show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Sellers.class, id);
    }
    // добавить
    @Transactional
    public void save(Sellers sellers) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(sellers);
    }
    // редактировать
    @Transactional
    public void update(int id, Sellers updatedSales) {
        Session session = sessionFactory.getCurrentSession();
        Sellers sellers = session.get(Sellers.class, id);

        sellers.setSurname(updatedSales.getSurname());
        sellers.setFirstName(updatedSales.getFirstName());
        sellers.setPatronymic(updatedSales.getPatronymic());
        sellers.setPassport(updatedSales.getPassport());
        sellers.setPercent(updatedSales.getPercent());
    }
    // удалить

    public void delete(int id){
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Sellers.class, id));
    }

}