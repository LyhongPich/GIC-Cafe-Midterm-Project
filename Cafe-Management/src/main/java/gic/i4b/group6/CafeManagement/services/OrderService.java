package gic.i4b.group6.CafeManagement.services;

import java.util.List;

import gic.i4b.group6.CafeManagement.models.Orders;

public interface OrderService {
    void setOrder(Integer drinkId, Integer tableId, Integer sizeId, Integer addonId, Integer qtd);
    List<Orders> getAllOrder(Integer tableNum);
}
