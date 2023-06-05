package gic.i4b.group6.CafeManagement.controllers;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import gic.i4b.group6.CafeManagement.models.Addons;
import gic.i4b.group6.CafeManagement.models.Drinks;
import gic.i4b.group6.CafeManagement.models.Orders;
import gic.i4b.group6.CafeManagement.models.Sizes;
import gic.i4b.group6.CafeManagement.models.Tables;
import gic.i4b.group6.CafeManagement.services.AddonService;
import gic.i4b.group6.CafeManagement.services.CategoryService;
import gic.i4b.group6.CafeManagement.services.DrinkService;
import gic.i4b.group6.CafeManagement.services.OrderService;
import gic.i4b.group6.CafeManagement.services.SizeService;
import gic.i4b.group6.CafeManagement.services.TableService;
import gic.i4b.group6.CafeManagement.services.UserService;

@Controller
public class OrderController {
    private UserService userService;
    private TableService tableService;
    private CategoryService categoryService;
    private DrinkService drinkService;
    private OrderService orderService;
    private AddonService addonService;
    private SizeService sizeService;

    public OrderController(UserService userService, 
                        TableService tableService, 
                        CategoryService categoryService, 
                        DrinkService drinkService, 
                        OrderService orderService, 
                        AddonService addonService,
                        SizeService sizeService) {
        this.userService = userService;
        this.tableService = tableService;
        this.categoryService = categoryService;
        this.drinkService = drinkService;
        this.orderService = orderService;
        this.addonService = addonService;
        this.sizeService = sizeService;
    }

    @GetMapping("/drinkSelection/{tableId}/{cashierId}")
    public String drinkView(@PathVariable("tableId") Integer tableId,
                         @PathVariable("cashierId") Integer cashierId,
                         Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getDrinks());
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addons", addonService.getAllAddons());
        model.addAttribute("sizes", sizeService.getAllSize());
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
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addons", addonService.getAllAddons());
        model.addAttribute("sizes", sizeService.getAllSize());
        return "Drink_Select";
    }

    @GetMapping("drinkSelection/drink={drinkId}/table={tableId}/cashier={cashierId}")
    public String drinkSelectToOrder(@PathVariable("drinkId") Integer drinkId,
                                @PathVariable("tableId") Integer tableId,
                                @PathVariable("cashierId") Integer cashierId,
                                Model model) {
        model.addAttribute("table", tableService.getTableById(tableId));
        model.addAttribute("cashier", userService.getCashierById(cashierId));
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("drinks", drinkService.getDrinks());
        model.addAttribute("orders", orderService.getAllOrder(tableId));
        model.addAttribute("addons", addonService.getAllAddons());
        model.addAttribute("sizes", sizeService.getAllSize());             
        return "Drink_Order_Select";
    }

    @PostMapping("/addOrder")
    public String addOrder(@RequestParam("drinkId") Integer drinkId,
                        @RequestParam("tableId") Integer tableId,
                        @RequestParam("sizeSelected") Integer sizeId,
                        @RequestParam("addonSelect") Integer addonId,
                        @RequestParam("qtd") Integer qtd) {
        Orders orders = new Orders();
        Drinks drink = drinkService.getDrinkById(drinkId);
        Tables table = tableService.getTableById(tableId);
        Sizes size = sizeService.getSizeById(sizeId);
        Addons addon = addonService.getAddonById(addonId);

        orders.setAddons(addon);
        orders.setDrinks(drink);
        orders.setSizes(size);
        orders.setTables(table);
        orders.setQuantity(qtd);
        orders.setLocalDateTime(new Date());

        return "redirect:/drinkSelection/{tableId}/{cashierId}";
    }
    
}
