package com.estacionamiento.vehicle.repository;

import com.estacionamiento.vehicle.model.VehicleCategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleCategoryRepository extends JpaRepository<VehicleCategoryModel, Integer> {}
