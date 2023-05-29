package ru.cherkas.course.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.cherkas.course.dao.BuyDao;
import ru.cherkas.course.dao.ClockDao;
import ru.cherkas.course.models.Buy;
import ru.cherkas.course.models.Clock;

import java.util.Date;

@Controller
@RequestMapping("/buy")
public class BuyController {
    private final BuyDao buyDao;
    private final ClockDao clockDao;

    @Autowired
    public BuyController(BuyDao buyDao, ClockDao clockDao) {
        this.buyDao = buyDao;
        this.clockDao = clockDao;
    }
    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("buy_list", buyDao.index());
        return "buy/index";
    }
    // получить форму
    @GetMapping("/new")
    public String newBuy(@ModelAttribute("buy") Buy buy, Model model) {
        model.addAttribute("clocks", clockDao.index());
        return "buy/new";
    }
    // добавить
    @PostMapping("")
    public String create(@ModelAttribute("buy") @Valid Buy buy,
                         BindingResult bindingResult) {
        //sellersValidator.validate(sellers, bindingResult);
        // если ошибки возвращаемся
        if (bindingResult.hasErrors())
            return "buy/new";
        buyDao.save(buy);
        return "redirect:/buy/index";
    }
    // редактировать получить форму
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("buy", buyDao.show(id));
        model.addAttribute("clocks", clockDao.index());
        return "buy/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("buy") @Valid Buy buy,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        //sellersValidator.validate(sellers, bindingResult);
        if (bindingResult.hasErrors())
            return "/buy/edit";
        buyDao.update(id, buy);
        return "redirect:/buy/index";
    }
}
