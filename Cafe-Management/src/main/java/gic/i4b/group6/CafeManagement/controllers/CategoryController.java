package gic.i4b.group6.CafeManagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import gic.i4b.group6.CafeManagement.services.CategoryService;
import gic.i4b.group6.CafeManagement.services.DrinkService;

@Controller
public class CategoryController {
    private CategoryService categoryService;
    private DrinkService drinkService;

    public CategoryController(CategoryService categoryService, DrinkService drinkService) {
        this.categoryService = categoryService;
        this.drinkService = drinkService;
    }

    @GetMapping("/admin/addCategory")
    public String categoryView() {
        return "Admin/New_category";
    }

    @GetMapping("/admin/addCategoryFromDrink")
    public String categoryViewFromDrink() {
        return "Admin/New_category_from_new_drink";
    }

    @GetMapping("/admin/addCategoryFromEdit/drink={id}")
    public String categoryViewFromEdit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("drink", drinkService.getDrinkById(id));
        return "Admin/New_category_from_edit_drink";
    }

    @PostMapping("/admin/addCategory")
    public String addCategory(@RequestParam("code") String code, 
                            @RequestParam("name") String name,
                            @RequestParam("file") MultipartFile picture) {
        categoryService.setCategory(code, name, picture);
        return "redirect:/admin/addCategory";
    }

    @PostMapping("/admin/addCategoryFromDrink")
    public String addCategoryFromDrink(@RequestParam("code") String code, 
                            @RequestParam("name") String name,
                            @RequestParam("file") MultipartFile picture) {
        categoryService.setCategory(code, name, picture);
        return "redirect:/admin/addNewDrink";
    }

    @PostMapping("/admin/addCategoryFromEdit/drink={id}")
    public String addCategoryFromEdit(@RequestParam("code") String code,
                            @RequestParam("name") String name,
                            @RequestParam("file") MultipartFile picture) {
        categoryService.setCategory(code, name, picture);
        return "redirect:/admin/edit_drink/{id}";
    }
    
}
