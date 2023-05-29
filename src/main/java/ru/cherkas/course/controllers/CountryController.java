package ru.cherkas.course.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.cherkas.course.dao.CountryDao;
import ru.cherkas.course.models.Country;
import ru.cherkas.course.util.CountryValidator;

@Controller
@RequestMapping("/country")
public class CountryController {
    private final CountryDao countryDao;
    private final CountryValidator countryValidator;

    @Autowired
    public CountryController(CountryDao countryDao, CountryValidator countryValidator) {
        this.countryDao = countryDao;
        this.countryValidator = countryValidator;
    }
    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("countries", countryDao.index());
        return "country/index";
    }
    // получить форму
    @GetMapping("/new")
    public String newCountry(@ModelAttribute("country") Country country ) {
        return "country/new";
    }
    // добавить
    @PostMapping("")
    public String create(@ModelAttribute("country") @Valid Country country,
                         BindingResult bindingResult) {
        countryValidator.validate(country, bindingResult);
        // если ошибки возвращаемся
        if (bindingResult.hasErrors())
            return "country/new";

        countryDao.save(country);
        return "redirect:/country/index";
    }
    // редактировать получить форму
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("country", countryDao.show(id));
        return "country/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("country") @Valid Country country,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "/clock/edit";
        countryDao.update(id, country);
        return "redirect:/country/index";
    }
}
