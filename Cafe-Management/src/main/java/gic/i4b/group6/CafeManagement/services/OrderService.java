package gic.i4b.group6.CafeManagement.services;

import java.util.List;

import gic.i4b.group6.CafeManagement.models.Orders;

public interface OrderService {
    void setOrder(Integer drinkId, Integer tableId, Integer sizeId, Integer addonId, Integer qtd);
    void editOrder(Integer orderId, Integer drinkId, Integer tableId, Integer sizeId, Integer addonId);
    void removeOrder(Integer orderId);
    void removeAllOrder();
    void increaseQuantity(Integer orderId, Integer qtd);
    void decreaseQuantity(Integer orderId, Integer qtd);
    Orders getOrderById(Integer orderId);
    Orders getFirstOrderByTableId(Integer tableId);
    List<Orders> getAllOrder(Integer tableNum);
    Float checkoutView(Integer tableId);
    Float checkout(Float totalprice, Float cash);
}
