package gic.i4b.group6.CafeManagement.services;

import java.math.BigDecimal;
import java.util.List;

import gic.i4b.group6.CafeManagement.models.Orders;

public interface OrderService {
    void setOrder(Integer drinkId, Integer tableId, Integer sizeId, Integer addonId, Integer qtd);
    void editOrder(Integer orderId, Integer drinkId, Integer tableId, Integer sizeId, Integer addonId);
    void removeOrder(Integer orderId);
    void removeAllOrder();
    void increaseQuantity(Integer orderId, Integer qtd);
    void decreaseQuantity(Integer orderId, Integer qtd);
    void setLastOrder(Integer drinkId);
    void removeLastOrder(Integer orderId); 
    Orders getOrderById(Integer orderId);
    Orders getFirstOrderByTableId(Integer tableId);
    List<Orders> getAllOrder(Integer tableNum);
    BigDecimal checkoutView(Integer tableId);
    BigDecimal checkout(BigDecimal totalprice, BigDecimal cash);
}
