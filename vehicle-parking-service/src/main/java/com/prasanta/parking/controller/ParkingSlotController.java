package com.prasanta.parking.controller;

import com.prasanta.parking.model.ParkingSlot;
import com.prasanta.parking.service.ParkingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parking/slot-management")  // ✅ Unique base path
public class ParkingSlotController {

    private final ParkingService parkingService;

    public ParkingSlotController(ParkingService parkingService) {
        this.parkingService = parkingService;
    }

    @GetMapping("/all")  // ✅ Avoids conflict
    public List<ParkingSlot> getAllSlots() {
        return parkingService.getAllSlots();
    }

    @PostMapping("/add")  // ✅ Avoids conflict
    public ParkingSlot addSlot(@RequestBody ParkingSlot slot) {
        return parkingService.addSlot(slot);
    }
}
