package ru.cherkas.course.controllers;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.cherkas.course.dao.BuyDao;
import ru.cherkas.course.dao.ClockDao;
import ru.cherkas.course.dao.SalesDao;
import ru.cherkas.course.dao.SellersDao;
import ru.cherkas.course.models.Buy;
import ru.cherkas.course.models.Clock;
import ru.cherkas.course.models.Sales;
import ru.cherkas.course.models.Sellers;

@Controller
@RequestMapping("/sales")
public class SalesController {
    private final SalesDao salesDao;
    private final ClockDao clockDao;
    private final SellersDao sellersDao;
    private final BuyDao buyDao;

    public SalesController(SalesDao salesDao, ClockDao clockDao, SellersDao sellersDao, BuyDao buyDao) {
        this.salesDao = salesDao;
        this.clockDao = clockDao;
        this.sellersDao = sellersDao;
        this.buyDao = buyDao;
    }

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("sales_list", salesDao.index());
        return "sales/index";
    }
    // получить форму
    @GetMapping("/new")
    public String newSales(@ModelAttribute("sales") Sales sales, Model model) {
        model.addAttribute("clocks_list", clockDao.index());
        model.addAttribute("sellers_list", sellersDao.index());
        return "sales/new";
    }
    // добавить
    @PostMapping("")
    public String create(@ModelAttribute("sales") @Valid Sales sales,
                         BindingResult bindingResult) {

        // если ошибки возвращаемся
        if (bindingResult.hasErrors())
            return "sales/new";

        Clock clock = clockDao.show(sales.getClock().getId());
        Sellers sellers = sellersDao.show(sales.getSellers().getId());
        sales.setClock(clock);
        sales.setSellers(sellers);
        // находим закупку с такими часами
        Buy buy = buyDao.show(clock);
        // проверка сколько часов осталось
        int count = buy.getCount_clock() - sales.getCount_clock();
        if (count >= 0) {
            // sales.setCount_clock(count);
            salesDao.save(sales);
            // в закупке изменяем количество часов
            buy.setCount_clock(count);
            buy.setClock(clock);
            buyDao.update(buy.getId(), buy);
        }
        else {
            return "sales/new";
        }
        return "redirect:/sales/index";
    }

    // редактировать получить форму
    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("sales", salesDao.show(id));
        model.addAttribute("clocks_list", clockDao.index());
        model.addAttribute("sellers_list", sellersDao.index());
        return "sales/edit";
    }
    @PatchMapping("/{id}")
    public String update(@ModelAttribute("sales") @Valid Sales sales,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        //sellersValidator.validate(sellers, bindingResult);
        if (bindingResult.hasErrors())
            return "/sales/edit";
        salesDao.update(id, sales);
        return "redirect:/sales/index";
    }
    // удалить
    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        salesDao.delete(id);
        return "redirect:/sales/index";
    }
}
