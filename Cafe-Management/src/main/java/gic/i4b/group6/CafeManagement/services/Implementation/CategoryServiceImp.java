package gic.i4b.group6.CafeManagement.services.Implementation;

import java.io.File;
import java.util.Base64;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gic.i4b.group6.CafeManagement.models.Categories;
import gic.i4b.group6.CafeManagement.repositories.CategoryRepository;
import gic.i4b.group6.CafeManagement.services.CategoryService;

@Service
public class CategoryServiceImp implements CategoryService {

    private CategoryRepository categoryRepository;
    

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public List<Categories> getAllCategories() {
        return categoryRepository.findAll();
    }


    @Override
    public void setCategory(String code, String name, MultipartFile picture) {
        List<Categories> cates = categoryRepository.findAll();

        int state = 0;

        for(Categories cate : cates) {
            if(code.equals(cate.getCategory_code()) && name.equals(cate.getCategory_name())) {
                state = 1;
                break;
            }
        }
        if(state == 0) {
            Categories categories = new Categories();
            categories.setCategory_code(code);
            categories.setCategory_name(name);

            String fileName = picture.getOriginalFilename();
            String cleanFile = new File(fileName).getName();
            if(cleanFile.contains("..")) {
                System.out.println("not a valid file");
            }
            try{
                categories.setPicture(Base64.getEncoder().encodeToString(picture.getBytes()));
            }
            catch(Exception e) {
                e.printStackTrace();
            }

            categoryRepository.save(categories);
        }
    }
    
}
