package ru.cherkas.course.dao;

import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.cherkas.course.models.Clock;
import ru.cherkas.course.models.Manufacturer;

import java.util.List;

@Component
public class ClockDao {
    private final JdbcTemplate jdbcTemplate;
    private final SessionFactory sessionFactory;
    private final ManufacturerDao manufacturerDao;

    @Autowired
    public ClockDao(JdbcTemplate jdbcTemplate, SessionFactory sessionFactory, ManufacturerDao manufacturerDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.sessionFactory = sessionFactory;
        this.manufacturerDao = manufacturerDao;
    }

    @Transactional
    public List<Clock> index() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select s from Clock s", Clock.class)
                .getResultList();
    }

    @Transactional
    public Clock show(int id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Clock.class, id);
    }

    // добавить
    @Transactional
    public void save(Clock clock) {
        Session session = sessionFactory.getCurrentSession();
        Manufacturer manufacturer = manufacturerDao.show(clock.getManufacturer().getId());
        clock.setManufacturer(manufacturer);
        session.persist(clock);
    }
    // редактировать
    @Transactional
    public void update(int id, Clock updatedClock) {
        Session session = sessionFactory.getCurrentSession();
        Clock clock = session.get(Clock.class, id);
        clock.setBrand(updatedClock.getBrand());
        clock.setTypeClock(updatedClock.getTypeClock());
        clock.setPrice(updatedClock.getPrice());
        Manufacturer manufacturer = manufacturerDao.show(updatedClock.getManufacturer().getId());
        clock.setManufacturer(manufacturer);
    }
}
