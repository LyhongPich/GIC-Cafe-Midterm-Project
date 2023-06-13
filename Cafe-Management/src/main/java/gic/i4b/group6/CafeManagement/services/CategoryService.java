package gic.i4b.group6.CafeManagement.services;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import gic.i4b.group6.CafeManagement.models.Categories;

public interface CategoryService {
    void setCategory(String code, String name, MultipartFile picture);
    Categories getCategoryById(Integer categoryId);
    List<Categories> getAllCategories();
}
