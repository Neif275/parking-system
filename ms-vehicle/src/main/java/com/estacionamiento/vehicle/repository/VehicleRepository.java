package com.estacionamiento.vehicle.repository;

import com.estacionamiento.vehicle.model.BrandModel;
import com.estacionamiento.vehicle.model.ModelModel;
import com.estacionamiento.vehicle.model.VehicleCategoryModel;
import com.estacionamiento.vehicle.model.VehicleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface VehicleRepository extends JpaRepository<VehicleModel, Long> {
    java.util.Optional<VehicleModel> findByPlate(String plate);
}
