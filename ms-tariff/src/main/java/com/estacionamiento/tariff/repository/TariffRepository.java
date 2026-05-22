package com.estacionamiento.tariff.repository;

import com.estacionamiento.tariff.model.TariffModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TariffRepository extends JpaRepository<TariffModel, Long> {
    List<TariffModel> findByVehicleType(String vehicleType);
}
