package com.foody.order.services.impl;

import com.foody.order.entitys.MasSerialNo;
import com.foody.order.entitys.MenuItem;
import com.foody.order.entitys.Orders;
import com.foody.order.entitys.Restaurant;
import com.foody.order.exception.ApiException;
import com.foody.order.external.RestaurantService;
import com.foody.order.repository.MasSrlRepo;
import com.foody.order.repository.OrdersRepo;
import com.foody.order.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrdersRepo ordersRepo;

    @Autowired
    private MasSrlRepo masSrlRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestaurantService restaurantService;

    private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    //Save order
    @Override
    public Orders saveOrder(Orders orders) {
        MasSerialNo masSerialNo = this.masSrlRepo.findBySrlTypeAndStatus("ODRS", "A");

        //Create Serial No
        //String slno = masSerialNo.getSrlNo().add(new BigDecimal(1)).toString();
        String slno = Integer.toString(masSerialNo.getSrlNo() + 1);
        String strSlNo = String.valueOf(slno);
        if (strSlNo.length() == 1) {
            strSlNo = "000" + strSlNo;
        } else if (strSlNo.length() == 2) {
            strSlNo = "00" + strSlNo;
        } else if (strSlNo.length() == 3) {
            strSlNo = "0" + strSlNo;
        }
        String finalSrlNo = "ODRS"+masSerialNo.getEntryDate().getYear()+strSlNo;

        //Set Serial No
        orders.setOrderId(finalSrlNo);

        //Save User
        Orders savedOrder = this.ordersRepo.save(orders);

        //Update Serial no
        this.masSrlRepo.updateSrlNo("ODRS", "A", Integer.parseInt(slno));

        return savedOrder;
    }

    //Get Single Order
    @Override
    public Optional<Orders> getOrders(String orderId) {
        Orders orders = this.ordersRepo.findById(orderId)
                .orElseThrow(() -> new ApiException("Order Id Not Found With Id : " + orderId));

        //***Fetch Items Details From Menu Service. Here we are use two approach for get data from other services
        // 1. RestTemplate (Get  Data From MenuItem Service)
        // 2. Fign Client   (Get  Data From Restaurant Service)

        ArrayList<MenuItem> menuList = new ArrayList<>();

        for(String item : orders.getItemId()){
            //Get MenuItem Detail
            MenuItem itemData = restTemplate.getForObject("http://MENU/nic/foody/menu/"+item, MenuItem.class);

            //Get Restaurant Derails From MenuItem
            /*Restaurant restaurantDtl = restTemplate.getForObject("http://RESTAURANT/nic/foody/rest/"+itemData
                    .getRestaurantId(), Restaurant.class); //if use restTemplet Then Use it else use Restaurent Service Fign Client */

            //Set Restaurant Data to MenuItem
            //itemData.setRestaurant(restaurantDtl); //use for restTemplate
            itemData.setRestaurant(restaurantService.getRestaurant(itemData.getRestaurantId()));  //Fign Client

            //Add MenuItem to a List
            menuList.add(itemData);

        }

        //List of MenuItem set into Order
        orders.setMenuItems(menuList);

        return Optional.ofNullable(orders);
    }


    //Get All Orders
    @Override
    public List<Orders> getAllOrders() {
        List<Orders> orders = this.ordersRepo.findAll().stream().collect(Collectors.toList());
        return orders;
    }

    //Get Order By Customer Id
    @Override
    public List<Orders> getOrdersByCustomerId(String customerId) {
        return this.ordersRepo.findByCustomerId(customerId);
    }

    //Delete Order
    @Override
    public void deleteOrder(String orderId) {
        Orders order = this.ordersRepo.findById(orderId)
                .orElseThrow(() -> new ApiException("Order Id Not Found With Id : " + orderId));
        this.ordersRepo.delete(order);
    }
}
