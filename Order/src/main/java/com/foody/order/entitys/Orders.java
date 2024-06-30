package com.foody.order.entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders", schema = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Orders {

    @Id
    private String orderId; //ODRS
    private String customerId ;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private String deliveryAddress;

    /*@ElementCollection
    @CollectionTable(name = "order_item", joinColumns = @JoinColumn(name = "orderID"), schema = "orders")
    private List<MenuItem> menuItems;*/

    @JsonIgnore //Data Save to Database But Not show into the JSON
    private List<String> itemId = new ArrayList<>();

    @Transient //Data Not Save to database but show in to JSON
    private List<MenuItem> menuItems = new ArrayList<>();
}
