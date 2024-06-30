package com.foody.restaurant.controller;

import com.foody.restaurant.entitys.Restaurant;
import com.foody.restaurant.exception.ApiResponse;
import com.foody.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/nic/foody/rest")
@CrossOrigin("*")
public class RestaurantController {

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping()
    public ResponseEntity<Restaurant> saveRestaurant(@RequestBody Restaurant restaurant){
        Restaurant savedRestu = this.restaurantService.saveRest(restaurant);
        return new ResponseEntity<>(savedRestu, HttpStatus.CREATED);
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> getRestaurant(@PathVariable String restaurantId){
        Optional<Restaurant> restaurant = this.restaurantService.getRest(restaurantId);
        return ResponseEntity.ok(restaurant.get());
    }

    @GetMapping()
    public ResponseEntity<List<Restaurant>> getAllUsers(){
        return ResponseEntity.ok(this.restaurantService.getAllRest());
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<Restaurant> updateUser(@RequestBody Restaurant restaurant, @PathVariable String restaurantId){
        return ResponseEntity.ok(this.restaurantService.updateRest(restaurant,restaurantId).get());
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String restaurantId){
        this.restaurantService.deleteRest(restaurantId);
        return new ResponseEntity<>(new ApiResponse(new Date(),"Deleted !!","Restaurant Deleted Successfully"),
                HttpStatus.OK);
    }
}
