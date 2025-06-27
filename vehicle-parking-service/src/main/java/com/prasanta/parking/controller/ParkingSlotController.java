package com.prasanta.parking.controller;

import com.prasanta.parking.model.ParkingSlot;
import com.prasanta.parking.service.ParkingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking/slots")
public class ParkingSlotController {

    private final ParkingService parkingService;

    public ParkingSlotController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping
    public List<ParkingSlot> getAllSlots() {
        return parkingService.getAllSlots();
    }

    @PostMapping
    public ParkingSlot addSlot(@RequestBody ParkingSlot slot) {
        return parkingService.addSlot(slot);
    }
}
