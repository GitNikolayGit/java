package ru.cherkas.course.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.cherkas.course.dao.ClockDao;
import ru.cherkas.course.dao.CountryDao;
import ru.cherkas.course.dao.QueryDao;
import ru.cherkas.course.models.Clock;
import ru.cherkas.course.models.Country;

import java.util.Date;

@Controller
@RequestMapping("/query")
public class QueryController {
    private final QueryDao queryDao;
    private final ClockDao clockDao;
    private final CountryDao countryDao;

    @Autowired
    public QueryController(QueryDao queryDao, ClockDao clockDao, CountryDao countryDao) {
        this.queryDao = queryDao;
        this.clockDao = clockDao;
        this.countryDao = countryDao;
    }

    @GetMapping("/index")
    public String index(@ModelAttribute("clock") Clock clock, @ModelAttribute("country") Country country, Model model) {
        model.addAttribute("clocks", clockDao.index());
        model.addAttribute("countries", countryDao.index());
        return "/query/index";
    }
    // 1.	Вывести информацию о часах заданного типа (механические часы, смарт-часы,
    @PostMapping("/query1")
    public String query1(@ModelAttribute("clock") Clock clock,  Model model) {
        Clock clock1 = clockDao.show(clock.getId());
        model.addAttribute("clocks", queryDao.query1(clock1.getTypeClock()));

        return "/clock/index";
    }
    // 2.	Вывести информацию о часах заданного типа, цена на которые в заданном диапазоне значений
    @PostMapping("/query2")
    public String query2(@ModelAttribute("clock") Clock clock, Model model, @RequestParam("min") Integer min, @RequestParam("max") Integer max) {
        Clock clock1 = clockDao.show(clock.getId());
        model.addAttribute("clocks", queryDao.query2(clock1.getTypeClock(), min, max));
        return "/clock/index";
    }
    // 3.	Вывести информацию о часах, произведенных в заданной стране
    @PostMapping("/query3")
    public String query3(@ModelAttribute("country") Country country, Model model) {
        model.addAttribute("clocks", queryDao.query3(country));
        return "/clock/index";
    }
    // 4.	Вывести продажи в магазине за указанный период
    @PostMapping("/query4")
    public String query4(@RequestParam("date_min") String date_min, @RequestParam("date_max") String date_max, Model model){
        model.addAttribute("buy_list", queryDao.query4(date_min, date_max));
        return "/buy/index";
    }
}
