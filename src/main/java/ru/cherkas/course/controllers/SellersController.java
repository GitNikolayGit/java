package ru.cherkas.course.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.cherkas.course.dao.SellersDao;
import ru.cherkas.course.models.Sellers;
import ru.cherkas.course.util.SellersValidator;

@Controller
@RequestMapping("/")
public class SellersController {

    private final SellersDao sellersDao;
    private final SellersValidator sellersValidator;
    @Autowired
    public SellersController(SellersDao sellersDao, SellersValidator sellersValidator){
        this.sellersDao = sellersDao;
        this.sellersValidator = sellersValidator;
    }
    @GetMapping()
    public String start(){
        return "index";
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("sellers", sellersDao.index());
        return "sellers/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("sellers", sellersDao.show(id));
        return "sellers/show";
    }
    // получить форму
    @GetMapping("/new")
    public String newPerson(@ModelAttribute("sellers") Sellers sellers) {
        return "sellers/new";
    }
    // добавить
    @PostMapping()
    public String create(@ModelAttribute("sellers") @Valid Sellers sellers,
                         BindingResult bindingResult) {
        sellersValidator.validate(sellers, bindingResult);
        // если ошибки возвращаемся
        if (bindingResult.hasErrors())
            return "sellers/new";

        sellersDao.save(sellers);
        return "redirect:/index";
    }
    // редактировать получить форму
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("sellers", sellersDao.show(id));
        return "sellers/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("sellers") @Valid Sellers sellers,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "sellers/edit";
        sellersDao.update(id, sellers);
        return "redirect:/index";
    }
    // удалить
    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        sellersDao.delete(id);
        return "redirect:/index";
    }
}
