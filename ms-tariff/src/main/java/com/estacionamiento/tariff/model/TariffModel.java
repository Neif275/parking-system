package com.estacionamiento.tariff.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Entity
@Table(name = "tariff")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TariffModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(name = "price_per_minute")
    private BigDecimal pricePerMinute;
    @Column(name = "vehicle_type")
    private String vehicleType;
}
