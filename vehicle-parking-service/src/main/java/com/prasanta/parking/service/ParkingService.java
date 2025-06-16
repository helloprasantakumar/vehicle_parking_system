package com.prasanta.parking.service;

import com.prasanta.parking.model.ParkingSlot;
import com.prasanta.parking.model.Vehicle;
import com.prasanta.parking.repository.ParkingSlotRepository;
import com.prasanta.parking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingService {

    @Autowired
    private ParkingSlotRepository slotRepo;

    @Autowired
    private VehicleRepository vehicleRepo;

    public List<ParkingSlot> getAllSlots() {
        return slotRepo.findAll();
    }

    public ParkingSlot addSlot(ParkingSlot slot) {
        return slotRepo.save(slot);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepo.findAll();
    }

    public Vehicle parkVehicle(Vehicle vehicle) {
        ParkingSlot slot = vehicle.getSlot();
        slot.setOccupied(true);
        slotRepo.save(slot);
        vehicle.setEntryTime(LocalDateTime.now());
        return vehicleRepo.save(vehicle);
    }

    public Vehicle unparkVehicle(Long id) {
        Vehicle vehicle = vehicleRepo.findById(id).orElseThrow();
        vehicle.setExitTime(LocalDateTime.now());
        ParkingSlot slot = vehicle.getSlot();
        slot.setOccupied(false);
        slotRepo.save(slot);
        return vehicleRepo.save(vehicle);
    }
}
