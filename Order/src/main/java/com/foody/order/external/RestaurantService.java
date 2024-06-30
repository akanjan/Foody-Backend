package com.foody.order.external;

import com.foody.order.entitys.Restaurant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RESTAURANT")
public interface RestaurantService {

    @GetMapping("/nic/foody/rest/{restaurantId}")
    Restaurant getRestaurant(@PathVariable String restaurantId);
}
