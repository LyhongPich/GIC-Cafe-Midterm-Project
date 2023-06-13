package gic.i4b.group6.CafeManagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import gic.i4b.group6.CafeManagement.services.DrinkService;

@Controller
public class DrinkController {
    
    private DrinkService drinkService;

    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @GetMapping("/admin/addNewDrink")
    public String addDrinkView(Model model) {
        model.addAttribute("categories", drinkService.getCategories());
        return "New_drink";
    }

    @PostMapping("/admin/addNewDrink")
    public String addDrink(@RequestParam("drinkCode") String dcode, 
                        @RequestParam("drinkName") String dname,
                        @RequestParam("price") Float price, 
                        @RequestParam("note") String note,
                        @RequestParam("cateName") String cName,
                        @RequestParam("file") MultipartFile image) {
        drinkService.setDrink(dcode, dname, cName, price, note, image);
        return "redirect:/admin/addNewDrink";
    }

    @GetMapping("/admin/edit_drink/{id}")
    public String editDrink(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("drink", drinkService.getDrinkById(id));
        model.addAttribute("categories", drinkService.getCategories());
        return "Edit_drink";
    }

    @PostMapping("/admin/updateDrink/{id}")
    public String updatedDrink(@PathVariable("id") Integer id,
                                @RequestParam("drinkCode") String dcode, 
                                @RequestParam("drinkName") String dname,
                                @RequestParam("price") Float price, 
                                @RequestParam("note") String note,
                                @RequestParam("cateName") String cName,
                                @RequestParam("file") MultipartFile image){
        drinkService.editDrink(id, dcode, dname, cName, price, note, image);
        return "redirect:/admin";
    }

    @GetMapping("/admin/confirm_remove_drink/{id}")
    public String confirmRemoveDrink(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("drink", drinkService.getDrinkById(id));
        return "Remove_Drink_Confirm";
    }

    @PostMapping("/admin/remove_drink/{id}")
    public String drinkRemoved(@PathVariable("id") Integer id) {
        drinkService.removeDrink(id);
        return "redirect:/admin";
    }
}
