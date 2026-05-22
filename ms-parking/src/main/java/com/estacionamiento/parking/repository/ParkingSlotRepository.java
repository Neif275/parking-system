package com.estacionamiento.parking.repository;

import com.estacionamiento.parking.model.ParkingSlotModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingSlotRepository extends JpaRepository<ParkingSlotModel, Long> {
    List<ParkingSlotModel> findByIsAvailableTrue();
}
