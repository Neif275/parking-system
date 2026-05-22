package com.estacionamiento.entryexit.repository;

import com.estacionamiento.entryexit.model.EntryExitModel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EntryExitRepository extends JpaRepository<EntryExitModel, Long> {
    List<EntryExitModel> findByPlate(String plate);
    List<EntryExitModel> findByStatus(String status);
    Optional<EntryExitModel> findByPlateAndStatus(String plate, String status);
    List<EntryExitModel> findByParkingSpaceId(Long parkingSpaceId);
}
