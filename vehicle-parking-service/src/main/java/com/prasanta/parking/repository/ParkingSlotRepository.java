package com.prasanta.parking.repository;

import com.prasanta.parking.model.ParkingSlot;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParkingSlotRepository extends JpaRepository<ParkingSlot, Long> {
}
