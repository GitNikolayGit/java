package ru.cherkas.course.controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.cherkas.course.dao.ClockDao;
import ru.cherkas.course.dao.ManufacturerDao;
import ru.cherkas.course.models.Clock;
import ru.cherkas.course.models.Sellers;

@Controller
@RequestMapping("/clock")
public class ClockController {
    private final ClockDao clockDao;
    private final ManufacturerDao manufacturerDao;
    //private final SellersValidator sellersValidator;
    @Autowired
    public ClockController(ClockDao clockDao, ManufacturerDao manufacturerDao){//}, SellersValidator sellersValidator){
        this.clockDao = clockDao;
       // this.sellersValidator = sellersValidator;
        this.manufacturerDao = manufacturerDao;
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("clocks", clockDao.index());
        return "clock/index";
    }
    // получить форму
    @GetMapping("/new")
    public String newClock(@ModelAttribute("clock") Clock clock, Model model) {
        model.addAttribute("manufacturers", manufacturerDao.index());
        return "clock/new";
    }
    // добавить
    @PostMapping("")
    public String create(@ModelAttribute("clock") @Valid Clock clock,
                         BindingResult bindingResult) {
        //sellersValidator.validate(sellers, bindingResult);
        // если ошибки возвращаемся
        if (bindingResult.hasErrors())
            return "clock/new";
        clockDao.save(clock);
        return "redirect:/clock/index";
    }
    // редактировать получить форму
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("clock", clockDao.show(id));
        model.addAttribute("manufacturers", manufacturerDao.index());
        return "clock/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("clock") @Valid Clock clock,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        //sellersValidator.validate(sellers, bindingResult);
        if (bindingResult.hasErrors())
            return "/clock/edit";
        clockDao.update(id, clock);
        return "redirect:/clock/index";
    }

}
