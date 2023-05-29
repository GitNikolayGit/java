package ru.cherkas.course.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.cherkas.course.dao.SellersDao;
import ru.cherkas.course.models.Sellers;

@Component
public class SellersValidator implements Validator {

    private final SellersDao sellersDao;

    @Autowired
    public SellersValidator(SellersDao sellersDao) {
        this.sellersDao = sellersDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Sellers.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Sellers sellers = (Sellers) target;
        if (sellersDao.show(sellers.getPassport()).isPresent()){
            errors.rejectValue("passport", "", "такой паспорт уже есть");
        }

    }
}
