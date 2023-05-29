package ru.cherkas.course.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.cherkas.course.dao.CountryDao;
import ru.cherkas.course.models.Country;
import ru.cherkas.course.models.Sellers;

@Component
public class CountryValidator implements Validator {

    private final CountryDao countryDao;

    public CountryValidator(CountryDao countryDao) {
        this.countryDao = countryDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Country.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Country country = (Country) target;
         if (countryDao.show(country.getTitle()).isPresent()){
              errors.rejectValue("title", "", "такая страна уже есть");
         }
    }
}
