package com.prasanta.parking.service;

import com.prasanta.parking.model.ParkingSlot;
import com.prasanta.parking.model.Vehicle;
import com.prasanta.parking.repository.ParkingSlotRepository;
import com.prasanta.parking.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ParkingService {

    @Autowired
    private ParkingSlotRepository slotRepo;

    @Autowired
    private VehicleRepository vehicleRepo;

    @Autowired
    private JavaMailSender mailSender;

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

    private static final int PRICE_PER_HOUR = 10;

    private long calculateParkingFee(LocalDateTime entryTime, LocalDateTime exitTime) {
        long hours = java.time.Duration.between(entryTime, exitTime).toHours();
        return Math.max(hours, 1) * PRICE_PER_HOUR; // minimum 1 hour billing
    }


    private void sendExitEmail(Vehicle vehicle, long price) {
        if (vehicle.getUserEmail() == null || vehicle.getUserEmail().isBlank()) return;

        String to = vehicle.getUserEmail();
        String subject = "Parking Receipt";
        String body = "Dear " + vehicle.getUserName() + ",\n\n"
                + "Your vehicle (" + vehicle.getLicensePlate() + ") has been unparked.\n"
                + "Entry Time: " + vehicle.getEntryTime() + "\n"
                + "Exit Time: " + vehicle.getExitTime() + "\n"
                + "Total Charges: ₹" + price + "\n\n"
                + "Thank you for using our service.";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }


    public Vehicle unparkVehicle(Long id) {
        Vehicle vehicle = vehicleRepo.findById(id).orElseThrow();
        LocalDateTime now = LocalDateTime.now();
        vehicle.setExitTime(now);

        ParkingSlot slot = vehicle.getSlot();
        slot.setOccupied(false);
        slotRepo.save(slot);

        long price = calculateParkingFee(vehicle.getEntryTime(), now);

        sendExitEmail(vehicle, price); // <-- Optional: Send email with pricing

        return vehicleRepo.save(vehicle);
    }

}
