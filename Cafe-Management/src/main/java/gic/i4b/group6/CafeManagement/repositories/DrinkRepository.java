package gic.i4b.group6.CafeManagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gic.i4b.group6.CafeManagement.models.Categories;
import gic.i4b.group6.CafeManagement.models.Drinks;

public interface DrinkRepository extends JpaRepository<Drinks, Integer> {
    List<Drinks> findByCategories(Categories categories);
}
