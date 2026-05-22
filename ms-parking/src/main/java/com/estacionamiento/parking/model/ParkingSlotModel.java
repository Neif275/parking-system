package com.estacionamiento.parking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parking_slot")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSlotModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "slot_number", unique = true)
    private String slotNumber;

    @ManyToOne
    @JoinColumn(name = "zone_id", nullable = false)
    private Zone zone;

    @ManyToOne
    @JoinColumn(name = "slot_type_id", nullable = false)
    private SlotTypeModel slotTypeModel;

    @Column(name = "is_available")
    private Boolean isAvailable = true;
}
