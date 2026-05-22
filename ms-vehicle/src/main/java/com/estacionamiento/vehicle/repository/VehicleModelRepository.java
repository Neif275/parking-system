package com.estacionamiento.vehicle.repository;

import com.estacionamiento.vehicle.model.ModelModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleModelRepository extends JpaRepository<ModelModel, Integer> {}
