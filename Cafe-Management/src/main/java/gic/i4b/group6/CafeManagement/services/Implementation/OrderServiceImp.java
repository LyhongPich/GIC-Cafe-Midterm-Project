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
        int state = 0;
        List<Orders> orderList = orderRepository.findAll();
        for(Orders o : orderList) {
            boolean isDuplicate = false;

            if(o.getDrinks() != null && o.getDrinks().getId() == drinkId && 
               o.getSizes() != null && o.getSizes().getId() == sizeId && 
               o.getTables() != null && o.getTables().getId() == tableId &&
               o.getQuantity() == qtd) {

                if(addonId == null && o.getAddons() == null) {
                    isDuplicate = true;
                }
                else if(addonId != null && o.getAddons() != null && o.getAddons().getId() == addonId) {
                    isDuplicate = true;
                }
            }
            if(isDuplicate) {
                state = 1;
                break;
            }
        }

        if(state == 0) {
            Orders orders = new Orders();
            Drinks drink = drinkRepository.findById(drinkId).get();
            Tables table = tableRepository.findById(tableId).get();
            Sizes size = sizeRepository.findById(sizeId).get();
            float sum = 0;

            if(addonId != null) {
                Addons addon = addonRepository.findById(addonId).get();

                orders.setAddons(addon);
                sum+=addon.getAddon_price();
            }

            orders.setDrinks(drink);
            orders.setSizes(size);
            orders.setTables(table);
            orders.setQuantity(qtd);
            orders.setTotal_price((drink.getPrices()*size.getSize_price())+sum);
            orders.setOrder_date(new Date());
            
            orderRepository.save(orders);
        }
    }

    @Override
    public Orders getOrderById(Integer orderId) {
        return orderRepository.findById(orderId).get();
    }

    @Override
    public void editOrder(Integer orderId, Integer drinkId, Integer tableId, Integer sizeId, Integer addonId) {
        
        int state = 0;
        List<Orders> orderList = orderRepository.findAll();
        for(Orders o : orderList) {
            boolean isDuplicate = false;

            if(o.getDrinks() != null && o.getDrinks().getId() == drinkId && 
               o.getSizes() != null && o.getSizes().getId() == sizeId && 
               o.getTables() != null && o.getTables().getId() == tableId) {

                if(addonId == null && o.getAddons() == null) {
                    isDuplicate = true;
                }
                else if(addonId != null && o.getAddons() != null && o.getAddons().getId() == addonId) {
                    isDuplicate = true;
                }
            }
            if(isDuplicate) {
                state = 1;
                break;
            }
        }

        if(state == 0) {
            int sum = 0;
            float total = 0;
            Orders orders = getOrderById(orderId);
            Sizes size = sizeRepository.findById(sizeId).get();
            if(addonId == null) {
                sum+=0;
                orders.setAddons(null);
            }
            else{
                Addons addon = addonRepository.findById(addonId).get();
                sum+=orders.getAddons().getAddon_price();
                orders.setAddons(addon);
            }
            total = (size.getSize_price()*orders.getDrinks().getPrices())+sum;
            orders.setSizes(size);
            orders.setTotal_price(total);
            orderRepository.save(orders);
        }
    }

    @Override
    public void removeOrder(Integer orderId) {
        Orders order = orderRepository.findById(orderId).orElse(null);
        if(order.getAddons() == null) {
            if(order != null && order.getDrinks() != null &&
                order.getSizes() != null && order.getTables() != null) {

                order.getDrinks().setOrders(null);
                order.getSizes().setOrders(null);
                order.getTables().setOrders(null);
            }
        }
        else{
            if(order != null && order.getDrinks() != null &&
                order.getSizes() != null && order.getTables() != null) {

                order.getAddons().setOrders(null);
                order.getDrinks().setOrders(null);
                order.getSizes().setOrders(null);
                order.getTables().setOrders(null);
            }
        }
        orderRepository.deleteById(orderId);
    }

    @Override
    public void increaseQuantity(Integer orderId, Integer qtd) {
        int inc = qtd+1;
        int sum = 0;
        float totalPrice = 0;
        Orders order = getOrderById(orderId); 

        if(order.getAddons() == null) {
            sum+=0;
        }
        else {
            sum+=order.getAddons().getAddon_price();
        }
        totalPrice = (order.getSizes().getSize_price()*order.getDrinks().getPrices())+sum;
        order.setQuantity(inc);
        order.setTotal_price(totalPrice*inc);
        orderRepository.save(order);
    }

    @Override
    public void decreaseQuantity(Integer orderId, Integer qtd) {
        if(qtd > 1) {
            int dec = qtd-1;
            int sum = 0;
            float totalPrice = 0;
            Orders order = getOrderById(orderId); 

            if(order.getAddons() == null) {
                sum+=0;
            }
            else {
                sum+=order.getAddons().getAddon_price();
            }
            totalPrice = (order.getSizes().getSize_price()*order.getDrinks().getPrices())+sum;
            order.setQuantity(dec);
            order.setTotal_price(totalPrice*dec);
            orderRepository.save(order);
        }
    }

    @Override
    public Float checkoutView(Integer tableId) {
        Tables table = tableRepository.findById(tableId).get();
        float total = 0;

        List<Orders> orders = orderRepository.findByTables(table);
        for(Orders o : orders) {
            total += o.getTotal_price();
        }

        return total;
    }

    @Override
    public Float checkout(Float totalprice, Float cash) {
        float change = 0;
        if(cash > totalprice) {
            change = cash - totalprice;
        } 
        return change;
    }

    @Override
    public Orders getFirstOrderByTableId(Integer tableId) {
        Tables table = tableRepository.findById(tableId).get();
        List<Orders> orderList = orderRepository.findByTables(table);

        return orderList.get(0);
    }

    @Override
    public void removeAllOrder() {
        List<Orders> orderList = orderRepository.findAll();

        for(Orders o : orderList) {
            if(o != null) {
                o.setAddons(null);
                o.setDrinks(null);
                o.setSizes(null);
                o.setTables(null);
                orderRepository.delete(o);
            }
        }
    }

}
