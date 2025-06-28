package com.prasanta.parking.controller;

import com.prasanta.parking.model.Vehicle;
import com.prasanta.parking.service.ParkingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking/vehicles")
public class VehicleController {

    private final ParkingService parkingService;

    public VehicleController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/all")
    public List<Vehicle> getAllVehicles() {
        return parkingService.getAllVehicles();
    }

    @PostMapping("/add")
    public Vehicle parkVehicle(@RequestBody Vehicle vehicle) {
        return parkingService.parkVehicle(vehicle);
    }

    @PutMapping("/{id}/exit")
    public Vehicle unparkVehicle(@PathVariable Long id) {
        return parkingService.unparkVehicle(id);
    }
}
