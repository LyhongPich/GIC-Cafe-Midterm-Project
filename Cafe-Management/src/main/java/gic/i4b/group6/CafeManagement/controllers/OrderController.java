package gic.i4b.group6.CafeManagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import gic.i4b.group6.CafeManagement.services.CategoryService;
import gic.i4b.group6.CafeManagement.services.DrinkService;
import gic.i4b.group6.CafeManagement.services.TableService;
import gic.i4b.group6.CafeManagement.services.UserService;

@Controller
public class OrderController {
    private UserService userService;
    private TableService tableService;
    private CategoryService categoryService;
    private DrinkService drinkService;

    public OrderController(UserService userService, TableService tableService, CategoryService categoryService, DrinkService drinkService) {
        this.userService = userService;
        this.tableService = tableService;
        this.categoryService = categoryService;
        this.drinkService = drinkService;
    }

    @GetMapping("/drinkSelection/{tableId}/{cashierId}")
    public String drinkView(@PathVariable("tableId") Integer tableId,
                         @PathVariable("cashierId") Integer cashierId,
                         Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getDrinks());
        return "Drink";
    }

    @GetMapping("/drinkSelection/{tableId}/{cashierId}/{categoryId}")
    public String drinkViewWithSelect(@PathVariable("tableId") Integer tableId,
                                    @PathVariable("cashierId") Integer cashierId,
                                    @PathVariable("categoryId") Integer categoryId,
                                    Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getAllDrinksByCategoryId(categoryId));
        return "Drink_Select";
    }
    
}
