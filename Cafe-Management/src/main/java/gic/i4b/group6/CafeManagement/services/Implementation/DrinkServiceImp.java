package gic.i4b.group6.CafeManagement.services.Implementation;

import java.io.File;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gic.i4b.group6.CafeManagement.models.Categories;
import gic.i4b.group6.CafeManagement.models.Drinks;
import gic.i4b.group6.CafeManagement.repositories.CategoryRepository;
import gic.i4b.group6.CafeManagement.repositories.DrinkRepository;
import gic.i4b.group6.CafeManagement.services.DrinkService;

@Service
public class DrinkServiceImp implements DrinkService {

    private DrinkRepository drinkRepository;
    private CategoryRepository categoryRepository;

    public DrinkServiceImp(DrinkRepository drinkRepository, CategoryRepository categoryRepository) {
        this.drinkRepository = drinkRepository;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Categories> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void setDrink(String dcode, String dname, String cName, Float price, String note, MultipartFile image) {

        int state = 0;
        int cateId = 0;
        int stateCheckDrink = 0;

        List<Drinks> drinkCompare = drinkRepository.findAll();
        for(Drinks d : drinkCompare) {
            if(dcode.equals(d.getDrink_code()) && dname.equals(d.getDrink_name())) {
                stateCheckDrink = 1;
                break;
            }
        }
        if(stateCheckDrink == 0) {
            Drinks drink = new Drinks();
            drink.setDrink_code(dcode);
            drink.setDrink_name(dname);
            drink.setNote(note);
            drink.setPrices(price);

            String fileName = image.getOriginalFilename();
            String cleanFile = new File(fileName).getName();
            if(cleanFile.contains("..")) {
                System.out.println("not a valid file");
            }
            try {
                drink.setDrink_picture(Base64.getEncoder().encodeToString(image.getBytes()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<Categories> drinkCategory = getCategories();
            for(Categories cate : drinkCategory) {
                if(cName.equals(cate.getCategory_name())) {
                    state = 1;
                    cateId = cate.getId();
                    break;
                }
            }
            if(state == 1){
                Categories drinkCate = categoryRepository.findById(cateId).get();
                drink.setCategories(drinkCate);
            }
            drinkRepository.save(drink);
        }
    }

    @Override
    public List<Drinks> getDrinks() {
       return drinkRepository.findAll();
    }


    @Override
    public Drinks getDrinkById(Integer id) {
        return drinkRepository.findById(id).get();
    }


    @Override
    public List<Drinks> getAllDrinksByCategoryId(Integer id) {
        Categories category = categoryRepository.findById(id).get();
        return drinkRepository.findByCategories(category);
    }


    @Override
    public void editDrink(Integer id, String dcode, String dname, String cName, Float price, String note,
            MultipartFile image) {
                
        int state = 0;
        int cateId = 0;

        Drinks drink = getDrinkById(id);
        drink.setDrink_code(dcode);
        drink.setDrink_name(dname);
        drink.setNote(note);
        drink.setPrices(price);

        String fileName = image.getOriginalFilename();
        String cleanFile = new File(fileName).getName();
        if(cleanFile.contains("..")) {
            System.out.println("not a valid file");
        }
        try {
            drink.setDrink_picture(Base64.getEncoder().encodeToString(image.getBytes()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Categories> drinkCategory = getCategories();
        for(Categories cate : drinkCategory) {
            if(cName.equals(cate.getCategory_name())) {
                state = 1;
                cateId = cate.getId();
                break;
            }
        }
        if(state == 1){
            Categories drinkCate = categoryRepository.findById(cateId).get();
            drink.setCategories(drinkCate);
        }
        drinkRepository.save(drink);
    }


    @Override
    public void removeDrink(Integer id) {
        Drinks drink = drinkRepository.findById(id).orElse(null);
        if(drink != null && drink.getCategories() != null) {
            drink.getCategories().setDrinks(null);
        }
        drinkRepository.deleteById(id);
    }

}
