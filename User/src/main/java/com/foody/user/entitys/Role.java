package com.foody.user.entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles" , schema = "users")
@Builder
public class Role {
    @Id
    private int roleId;
    private String name;
}
