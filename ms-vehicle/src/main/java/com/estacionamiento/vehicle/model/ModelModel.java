package com.estacionamiento.vehicle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle_model")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ModelModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private BrandModel brand;


}
