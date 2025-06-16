package com.prasanta.parking.controller;

import com.prasanta.parking.model.ParkingSlot;
import com.prasanta.parking.model.Vehicle;
import com.prasanta.parking.service.ParkingService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class HomeController {

    @GetMapping("/")
    public String getSlots() {
        return "App is running";
    }

}
