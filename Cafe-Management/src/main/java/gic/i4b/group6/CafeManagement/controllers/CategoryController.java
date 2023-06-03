package gic.i4b.group6.CafeManagement.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import gic.i4b.group6.CafeManagement.services.CategoryService;

@Controller
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/admin/addCategory")
    public String categoryView() {
        return "New_category";
    }

    @PostMapping("/admin/addCategory")
    public String addCategory(@RequestParam("code") String code, 
                            @RequestParam("name") String name,
                            @RequestParam("file") MultipartFile picture) {
        categoryService.setCategory(code, name, picture);
        return "redirect:/admin/addCategory";
    }
    
}
