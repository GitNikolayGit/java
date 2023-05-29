package ru.cherkas.course.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.cherkas.course.models.Clock;
import ru.cherkas.course.models.Country;
import ru.cherkas.course.models.Manufacturer;

import java.util.List;
import java.util.Optional;

@Component
public class ManufacturerDao {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;
    private final CountryDao countryDao;

    public ManufacturerDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory, CountryDao countryDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
        this.countryDao = countryDao;
    }
    @Transactional
    public List<Manufacturer> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select s from Manufacturer s", Manufacturer.class)
                .getResultList();
    }
    // находит по индексу
    @Transactional
    public Manufacturer show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Manufacturer.class, id);
    }
    // находит по названию
    @Transactional
    public Optional<Manufacturer> show(String title) {
        Session session = sessionFactory.getCurrentSession();
        return jdbcTemplate.query("select * from Manufacturer where title = ?", new Object[]{title},
                new BeanPropertyRowMapper<>(Manufacturer.class)).stream().findAny();
    }
    // добавить
    @Transactional
    public void save(Manufacturer manufacturer) {
        Session session = sessionFactory.getCurrentSession();
        Country country = countryDao.show(manufacturer.getCountry().getId());
        manufacturer.setCountry(country);
        session.persist(manufacturer);
    }

    // редактировать
    @Transactional
    public void update(int id, Manufacturer updatedManufacturer) {
        Session session = sessionFactory.getCurrentSession();
        Manufacturer manufacturer = session.get(Manufacturer.class, id);
        manufacturer.setTitle(updatedManufacturer.getTitle());
        Country country = countryDao.show(updatedManufacturer.getCountry().getId());
        manufacturer.setCountry(country);
        session.persist(manufacturer);
    }

}
