package com.foody.order.services;

import com.foody.order.entitys.Orders;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    //Placed Order
    Orders saveOrder(Orders orders);

    //Gat Order
    Optional<Orders> getOrders(String orderID);

    //Gat All Orders
    List<Orders> getAllOrders();

    //Gat All Order By Customer Id
    //Optional<Orders> getOrdersByCustomerId(String customerID);
    List<Orders> getOrdersByCustomerId(String customerId);

    //Update Order
    //Optional<Orders> updateOrder(Orders orders, String orderID);

    //Delete Order
    void deleteOrder(String orderID);

}
