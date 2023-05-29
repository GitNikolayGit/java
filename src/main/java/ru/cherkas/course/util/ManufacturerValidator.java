package ru.cherkas.course.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.cherkas.course.dao.CountryDao;
import ru.cherkas.course.dao.ManufacturerDao;
import ru.cherkas.course.models.Country;
import ru.cherkas.course.models.Manufacturer;

@Component
public class ManufacturerValidator implements Validator {
    ManufacturerDao manufacturerDao;
    CountryDao countryDao;

    public ManufacturerValidator(ManufacturerDao manufacturerDao, CountryDao countryDao) {
        this.manufacturerDao = manufacturerDao;
        this.countryDao = countryDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Manufacturer.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Manufacturer manufacturer = (Manufacturer) target;
        Country country = countryDao.show(manufacturer.getCountry().getId());
        if (manufacturerDao.show(manufacturer.getTitle()).isPresent() && countryDao.show(country.getTitle()).isPresent()){
            errors.rejectValue("title", "", "Такой производитель уже есть");
        }
    }
}
