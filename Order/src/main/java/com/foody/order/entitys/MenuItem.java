package com.foody.order.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class MenuItem {
    private String itemId;
    private String restaurantId;
    private String itemName;
    private String description;
    private BigDecimal price;

    private Restaurant restaurant;
}
