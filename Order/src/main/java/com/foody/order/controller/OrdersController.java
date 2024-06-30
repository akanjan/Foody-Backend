package com.foody.order.controller;

import com.foody.order.entitys.Orders;
import com.foody.order.exception.ApiResponse;
import com.foody.order.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/nic/foody/orders")
@CrossOrigin("*")
public class OrdersController {

    @Autowired
    private OrderService orderService;

    private Logger logger = LoggerFactory.getLogger(OrdersController.class);


    //Get login user
    @PostMapping("/login-user")
    public String loginUser(@RequestHeader("loggedInUser") String username){
        return username;
    }

    @PostMapping()
    public ResponseEntity<Orders> saveOrder(@RequestBody Orders orders){
        Orders saveOrder = this.orderService.saveOrder(orders);
        return new ResponseEntity<>(saveOrder, HttpStatus.CREATED);
    }

    int retryCount = 0;

    @GetMapping("/{orderID}")
    //@CircuitBreaker(name = "menuRestaurantBreaker", fallbackMethod = "menuRestaurantFallback")
    //@Retry(name = "menuRestaurantService", fallbackMethod = "menuRestaurantFallback")
    @RateLimiter(name = "menuRestaurantLimiter", fallbackMethod = "menuRestaurantFallback")
    public ResponseEntity<Orders> getOrder(@PathVariable String orderID){
        Optional<Orders> orders = this.orderService.getOrders(orderID);

        logger.info("Retry Count : {}", retryCount);
        retryCount++;

        return ResponseEntity.ok(orders.get());
    }

    //creating Order service  fall back  method for circuitbreaker
    public ResponseEntity<Orders> menuRestaurantFallback(String orderID, Exception ex){
        ex.printStackTrace();
        Orders orders = Orders.builder().orderId("OD-Demo123")
                                        .orderDate(LocalDate.now())
                                        .customerId("Anjan")
                                        .deliveryAddress("Demo Delivery Address")
                                        .build();
        return new ResponseEntity<>(orders, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user/{customerID}")
    public ResponseEntity<List<Orders>> getOrderByCustomerId(@PathVariable String customerID){
        List<Orders> ordersList = this.orderService.getOrdersByCustomerId(customerID);
        return ResponseEntity.ok(ordersList);
    }

    @GetMapping()
    public ResponseEntity<List<Orders>> getAllOrders(){
        return ResponseEntity.ok(this.orderService.getAllOrders());
    }

    @DeleteMapping("/{orderID}")
    public ResponseEntity<ApiResponse> deleteOrders(@PathVariable String orderID){
        this.orderService.deleteOrder(orderID);
        return new ResponseEntity<>(new ApiResponse(new Date(),"Deleted !!","Order Deleted Successfully"),
                HttpStatus.OK);
    }
}
