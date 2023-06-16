package gic.i4b.group6.CafeManagement.services.Implementation;

import java.math.BigDecimal;
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
            BigDecimal sum = BigDecimal.ZERO;

            if(addonId != null) {
                Addons addon = addonRepository.findById(addonId).get();

                orders.setAddons(addon);
                sum = sum.add(addon.getAddon_price());
            }

            orders.setDrinks(drink);
            orders.setSizes(size);
            orders.setTables(table);
            orders.setQuantity(qtd);
            orders.setTotal_price((drink.getPrices().multiply(size.getSize_price())).add(sum));
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
            BigDecimal sumBig = new BigDecimal(sum);
            BigDecimal total = BigDecimal.ZERO;
            Orders orders = getOrderById(orderId);
            Sizes size = sizeRepository.findById(sizeId).get();
            if(addonId == null) {
                sumBig = sumBig.add(BigDecimal.ZERO);
                orders.setAddons(null);
            }
            else{
                Addons addon = addonRepository.findById(addonId).get();
                sumBig = sumBig.add(orders.getAddons().getAddon_price());
                orders.setAddons(addon);
            }
            total = (size.getSize_price().multiply(orders.getDrinks().getPrices())).add(sumBig);
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

        BigDecimal increase = new BigDecimal(inc);
        BigDecimal summation = new BigDecimal(sum);
        BigDecimal totalPrice = BigDecimal.ZERO;
        Orders order = getOrderById(orderId); 

        if(order.getAddons() == null) {
            summation = summation.add(BigDecimal.ZERO);
        }
        else {
            summation = summation.add(order.getAddons().getAddon_price());
        }
        totalPrice = (order.getSizes().getSize_price().multiply(order.getDrinks().getPrices())).add(summation);
        order.setQuantity(inc);
        order.setTotal_price(totalPrice.multiply(increase));
        orderRepository.save(order);
    }

    @Override
    public void decreaseQuantity(Integer orderId, Integer qtd) {
        if(qtd > 1) {
            int dec = qtd-1;
            int sum = 0;

            BigDecimal decrease = new BigDecimal(dec);
            BigDecimal summation = new BigDecimal(sum);
            BigDecimal totalPrice = BigDecimal.ZERO;
            Orders order = getOrderById(orderId); 

            if(order.getAddons() == null) {
                summation = summation.add(BigDecimal.ZERO);
            }
            else {
                summation = summation.add(order.getAddons().getAddon_price());
            }
            totalPrice = (order.getSizes().getSize_price().multiply(order.getDrinks().getPrices())).add(summation);
            order.setQuantity(dec);
            order.setTotal_price(totalPrice.multiply(decrease));
            orderRepository.save(order);
        }
    }

    @Override
    public BigDecimal checkoutView(Integer tableId) {
        Tables table = tableRepository.findById(tableId).get();
        BigDecimal total = BigDecimal.ZERO;

        List<Orders> orders = orderRepository.findByTables(table);
        for(Orders o : orders) {
            total = total.add(o.getTotal_price());
        }

        return total;
    }

    @Override
    public BigDecimal checkout(BigDecimal totalprice, BigDecimal cash) {
        BigDecimal change = cash.subtract(totalprice);
        
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

    @Override
    public void setLastOrder(Integer drinkId) {
        Drinks drink = drinkRepository.findById(drinkId).get();

        List<Orders> orderList = orderRepository.findByDrinks(drink);

        if(orderList.size() == 1) {
            drink.setLast_order_date(orderList.get(0).getOrder_date());
        }
        else{
            Orders lastOrder = orderList.get(0);

            for(Orders o : orderList) {
                if(o.getOrder_date().compareTo(lastOrder.getOrder_date()) > 0) {
                    lastOrder = o;
                }
            }

            drink.setLast_order_date(lastOrder.getOrder_date());
        }
        drinkRepository.save(drink);
    }

    @Override
    public void removeLastOrder(Integer orderId) {
        Orders order = orderRepository.findById(orderId).get();

        Drinks drink = drinkRepository.findById(order.getDrinks().getId()).get();

        List<Orders> orderList = orderRepository.findByDrinks(drink);

        if(orderList.size() == 1) {
            drink.setLast_order_date(null);
        }
        else{
            Orders lastOrder = orderList.get(0);

            for(Orders o : orderList) {
                if(o.getOrder_date().compareTo(lastOrder.getOrder_date()) > 0) {
                    lastOrder = o;
                }
            }
            Orders secondLastOrder = null;

            for(Orders o : orderList) {
                if(o.getOrder_date().compareTo(drink.getLast_order_date()) > 0
                    && (secondLastOrder == null || o.getOrder_date().compareTo(secondLastOrder.getOrder_date()) < 0)) {
                        secondLastOrder = o;
                }
            }
            if(secondLastOrder != null) {
                drink.setLast_order_date(secondLastOrder.getOrder_date());
            }
        }
        drinkRepository.save(drink);
    }

}
