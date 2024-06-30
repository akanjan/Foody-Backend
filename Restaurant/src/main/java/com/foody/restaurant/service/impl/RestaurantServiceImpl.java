package com.foody.restaurant.service.impl;

import com.foody.restaurant.entitys.MasSerialNo;
import com.foody.restaurant.entitys.Restaurant;
import com.foody.restaurant.exception.ApiException;
import com.foody.restaurant.repository.MasSrlRepo;
import com.foody.restaurant.repository.RestaurantRepo;
import com.foody.restaurant.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private MasSrlRepo masSrlRepo;

    @Autowired
    private RestaurantRepo restaurantRepo;

    @Override
    public Restaurant saveRest(Restaurant restaurant) {
        MasSerialNo masSerialNo = this.masSrlRepo.findBySrlTypeAndStatus("REST", "A");

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
        String finalSrlNo = "REST"+masSerialNo.getEntryDate().getYear()+strSlNo;

        //Set Serial No
        restaurant.setRestaurantId(finalSrlNo);

        //Save User
        Restaurant savedRest = this.restaurantRepo.save(restaurant);

        //Update Serial no
        this.masSrlRepo.updateSrlNo("REST", "A", Integer.parseInt(slno));

        return savedRest;
    }

    @Override
    public Optional<Restaurant> getRest(String restaurantId) {
        Restaurant restaurant = this.restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ApiException("Restaurant Not Found With Id : " + restaurantId));
        return Optional.ofNullable(restaurant);
    }

    @Override
    public List<Restaurant> getAllRest() {
        List<Restaurant> restaurants = this.restaurantRepo.findAll().stream().collect(Collectors.toList());
        return restaurants;
    }

    @Override
    public Optional<Restaurant> updateRest(Restaurant restaurant, String restaurantId) {
        Restaurant restDtl = this.restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ApiException("Restaurant Not Found With Id : " + restaurantId));
        restDtl.setResName(restaurant.getResName());
        restDtl.setResAddress(restaurant.getResAddress());
        restDtl.setCuisineType(restaurant.getCuisineType());
        restDtl.setContactNumber(restaurant.getContactNumber());

        return Optional.ofNullable(this.restaurantRepo.save(restDtl));
    }

    @Override
    public void deleteRest(String restaurantId) {
        Restaurant restaurant = this.restaurantRepo.findById(restaurantId)
                .orElseThrow(() -> new ApiException("Restaurant Not Found With Id : " + restaurantId));
        this.restaurantRepo.delete(restaurant);
    }
}
