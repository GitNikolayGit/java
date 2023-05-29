package ru.cherkas.course.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.cherkas.course.dao.CountryDao;
import ru.cherkas.course.dao.ManufacturerDao;
import ru.cherkas.course.models.Clock;
import ru.cherkas.course.models.Manufacturer;
import ru.cherkas.course.util.ManufacturerValidator;

@Controller
@RequestMapping("/manufacturer")
public class ManufacturerController {
    ManufacturerDao manufacturerDao;
    CountryDao countryDao;
    ManufacturerValidator manufacturerValidator;

    @Autowired
    public ManufacturerController(ManufacturerDao manufacturerDao, CountryDao countryDao, ManufacturerValidator manufacturerValidator) {
        this.manufacturerDao = manufacturerDao;
        this.countryDao = countryDao;
        this.manufacturerValidator = manufacturerValidator;
    }
    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("manufactures", manufacturerDao.index());
        return "manufacturer/index";
    }
    // получить форму
    @GetMapping("/new")
    public String newManufacturer(@ModelAttribute("manufacturer") Manufacturer manufacturer, Model model) {
        model.addAttribute("countries", countryDao.index());
        return "manufacturer/new";
    }
    // добавить
    @PostMapping("")
    public String create(@ModelAttribute("manufacturer") @Valid Manufacturer manufacturer,
                         BindingResult bindingResult) {
        manufacturerValidator.validate(manufacturer, bindingResult);
        // если ошибки возвращаемся
        if (bindingResult.hasErrors())
            return "manufacturer/new";
        manufacturerDao.save(manufacturer);
        return "redirect:/manufacturer/index";
    }
    // редактировать получить форму
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("manufacturer", manufacturerDao.show(id));
        model.addAttribute("countries", countryDao.index());
        return "manufacturer/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("manufacturer") @Valid Manufacturer manufacturer,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        manufacturerValidator.validate(manufacturer, bindingResult);
        if (bindingResult.hasErrors())
            return "/manufacturer/edit";
        manufacturerDao.update(id, manufacturer);
        return "redirect:/manufacturer/index";
    }

}
