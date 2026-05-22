package com.estacionamiento.parking.repository;

import com.estacionamiento.parking.model.SlotTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotTypeRepository extends JpaRepository<SlotTypeModel, Integer> {
}
