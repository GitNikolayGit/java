package ru.cherkas.course.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.cherkas.course.models.Country;
import ru.cherkas.course.models.Manufacturer;
import ru.cherkas.course.models.Sellers;

import java.util.List;
import java.util.Optional;

@Component
public class CountryDao {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;

    public CountryDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
    }
    @Transactional
    public List<Country> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select s from Country s", Country.class)
                .getResultList();
    }
    @Transactional
    public Optional<Country> show(String title){
        return jdbcTemplate.query("select * from Country where title = ?", new Object[]{title},
                new BeanPropertyRowMapper<>(Country.class)).stream().findAny();
    }
    // находить по id
    @Transactional
    public Country show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Country.class, id);
    }
    // добавить
    @Transactional
    public void save(Country country) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(country);
    }
    // редактировать
    @Transactional
    public void update(int id, Country updatedCountry) {
        Session session = sessionFactory.getCurrentSession();
        Country country = session.get(Country.class, id);
        country.setTitle(updatedCountry.getTitle());
    }
}
