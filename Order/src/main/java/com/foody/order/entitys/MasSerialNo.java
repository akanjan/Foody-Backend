package com.foody.order.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "mas_serial_no", schema = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MasSerialNo {
    @Id
    private String srlType;
    private String srlDesc;
    private int srlNo;
    private String status;
    private LocalDate entryDate;
    private String entryBY;
}
