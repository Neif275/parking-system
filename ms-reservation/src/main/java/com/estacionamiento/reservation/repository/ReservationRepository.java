package com.estacionamiento.reservation.repository;

import com.estacionamiento.reservation.model.ReservationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationModel, Long> {
    List<ReservationModel> findByPlate(String plate);
    List<ReservationModel> findByOwnerUserId(Long ownerUserId);
    List<ReservationModel> findByStatus(String status);
    List<ReservationModel> findByParkingSpaceId(Long parkingSpaceId);
    List<ReservationModel> findByPlateAndStatus(String plate, String status);
}
