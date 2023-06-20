package gic.i4b.group6.CafeManagement.services;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import gic.i4b.group6.CafeManagement.models.Categories;
import gic.i4b.group6.CafeManagement.models.Drinks;

public interface DrinkService {

    void setDrink(String dcode, String dname, String cName, BigDecimal price, String note, MultipartFile image);
    void editDrink(Integer id, String dcode, String dname, String cName, BigDecimal price, String note, MultipartFile image);
    void removeDrink(Integer id);
    Drinks getDrinkById(Integer id);
    List<Categories> getCategories();
    List<Drinks> getDrinks();
    List<Drinks> getAllDrinksByCategoryId(Integer id);
    List<Integer> getNumberOfDrink();
}
