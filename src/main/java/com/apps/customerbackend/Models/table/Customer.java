package com.apps.customerbackend.Models.table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import org.hibernate.validator.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Entity
@Table(name = "customers")
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Name can't be empty")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "Address can't be empty")
    @Column(name = "address")
    private String address;

    @NotBlank(message = "City can't be empty")
    @Column(name = "city")
    private String city;

    @NotBlank(message = "Province can't be empty")
    @Column(name = "province")
    private String province;

    @Column(name = "register_date")
    private LocalDateTime registerDate;

    @NotBlank(message = "Status can't be empty")
    @Column(name = "status")
    private String status;
}
