package com.foody.order.repository;

import com.foody.order.entitys.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, String> {

    //Optional<Orders> findByCustomerID(String customerID);
    List<Orders> findByCustomerId(String customerID);
}
