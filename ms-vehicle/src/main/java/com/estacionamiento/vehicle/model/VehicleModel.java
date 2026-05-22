package com.estacionamiento.vehicle.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicleModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String plate;
    private String color;
    private String year;

    @ManyToOne
    @JoinColumn(name = "model_id", nullable = false)
    private ModelModel model;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private VehicleCategoryModel category;

    @Column(name = "owner_user_id")
    private Long ownerUserId;
}
