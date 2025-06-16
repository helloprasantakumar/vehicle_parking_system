package com.prasanta.parking.controller;

import com.prasanta.parking.model.ParkingSlot;
import com.prasanta.parking.model.Vehicle;
import com.prasanta.parking.service.ParkingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking")
@CrossOrigin(origins = "*")
public class ParkingController {

    private final ParkingService service;

    public ParkingController(ParkingService service) {
        this.service = service;
    }

    @GetMapping("/slots")
    public List<ParkingSlot> getSlots() {
        return service.getAllSlots();
    }

    @PostMapping("/slot")
    public ParkingSlot addSlot(@RequestBody ParkingSlot slot) {
        return service.addSlot(slot);
    }

    @GetMapping("/vehicles")
    public List<Vehicle> getVehicles() {
        return service.getAllVehicles();
    }

    @PostMapping("/vehicle")
    public Vehicle park(@RequestBody Vehicle vehicle) {
        return service.parkVehicle(vehicle);
    }

    @PutMapping("/vehicle/{id}/exit")
    public Vehicle unpark(@PathVariable Long id) {
        return service.unparkVehicle(id);
    }
}
