package com.estacionamiento.vehicle.repository;

import com.estacionamiento.vehicle.model.BrandModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<BrandModel, Integer> {}
