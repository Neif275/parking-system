package com.estacionamiento.reservation.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String plate;
    @Column(name = "owner_user_id")
    private Long ownerUserId;
    @Column(name = "parking_space_id")
    private Long parkingSpaceId;
    @Column(name = "reservation_time")
    private LocalDateTime reservationTime;
    @Column(name = "expiration_time")
    private LocalDateTime expirationTime;
    private String status;
}
