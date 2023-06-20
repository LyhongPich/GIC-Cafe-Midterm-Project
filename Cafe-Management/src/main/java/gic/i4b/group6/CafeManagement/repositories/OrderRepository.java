package gic.i4b.group6.CafeManagement.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gic.i4b.group6.CafeManagement.models.Drinks;
import gic.i4b.group6.CafeManagement.models.Orders;
import gic.i4b.group6.CafeManagement.models.Tables;

public interface OrderRepository extends JpaRepository<Orders, Integer> {
    List<Orders> findByTables(Tables tables);
    List<Orders> findByDrinks(Drinks drinks);
}
