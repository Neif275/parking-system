package com.estacionamiento.entryexit.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "entry_exit")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EntryExitModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String plate;
    @Column(name = "entry_time")
    private LocalDateTime entryTime;
    @Column(name = "exit_time")
    private LocalDateTime exitTime;
    @Column(name = "parking_space_id")
    private Long parkingSpaceId;
    @Column(name = "tariff_id")
    private Long tariffId;
    private String status;
}
