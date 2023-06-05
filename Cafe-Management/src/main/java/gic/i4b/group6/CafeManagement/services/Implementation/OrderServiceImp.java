package gic.i4b.group6.CafeManagement.services.Implementation;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import gic.i4b.group6.CafeManagement.models.Addons;
import gic.i4b.group6.CafeManagement.models.Drinks;
import gic.i4b.group6.CafeManagement.models.Orders;
import gic.i4b.group6.CafeManagement.models.Sizes;
import gic.i4b.group6.CafeManagement.models.Tables;
import gic.i4b.group6.CafeManagement.repositories.AddonRepository;
import gic.i4b.group6.CafeManagement.repositories.DrinkRepository;
import gic.i4b.group6.CafeManagement.repositories.OrderRepository;
import gic.i4b.group6.CafeManagement.repositories.SizeRepository;
import gic.i4b.group6.CafeManagement.repositories.TableRepository;
import gic.i4b.group6.CafeManagement.services.OrderService;

@Service
public class OrderServiceImp implements OrderService {

    private OrderRepository orderRepository;
    private TableRepository tableRepository;
    private DrinkRepository drinkRepository;
    private AddonRepository addonRepository;
    private SizeRepository sizeRepository;

    public OrderServiceImp(OrderRepository orderRepository, TableRepository tableRepository, DrinkRepository drinkRepository, AddonRepository addonRepository, SizeRepository sizeRepository) {
        this.orderRepository = orderRepository;
        this.tableRepository = tableRepository;
        this.drinkRepository = drinkRepository;
        this.addonRepository = addonRepository;
        this.sizeRepository = sizeRepository;
    }

    @Override
    public List<Orders> getAllOrder(Integer tableNum) {
        Tables table = tableRepository.findById(tableNum).get();
        return orderRepository.findByTables(table);
    }

    @Override
    public void setOrder(Integer drinkId, Integer tableId, Integer sizeId, Integer addonId, Integer qtd) {
        Orders orders = new Orders();
        Drinks drink = drinkRepository.findById(drinkId).get();
        Tables table = tableRepository.findById(tableId).get();
        Sizes size = sizeRepository.findById(sizeId).get();
        Addons addon = addonRepository.findById(addonId).get();

        orders.setAddons(addon);
        orders.setDrinks(drink);
        orders.setSizes(size);
        orders.setTables(table);
        orders.setQuantity(qtd);
        orders.setLocalDateTime(new Date());
        orderRepository.save(orders);
    }

}
