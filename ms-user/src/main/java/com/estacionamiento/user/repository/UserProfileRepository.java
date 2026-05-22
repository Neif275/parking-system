package com.estacionamiento.user.repository;


import com.estacionamiento.user.model.UserProfileModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfileModel, Long> {
}
