package com.foody.restaurant.service;

import com.foody.restaurant.entitys.Restaurant;

import java.util.List;
import java.util.Optional;

public interface RestaurantService {

    //Save
    Restaurant saveRest(Restaurant restaurant);

    //Get
    Optional<Restaurant> getRest(String restaurantId);

    //GetAll
    List<Restaurant> getAllRest();

    //Update
    Optional<Restaurant> updateRest(Restaurant restaurant, String restaurantId);

    //Delete
    void deleteRest(String restaurantId);
}
