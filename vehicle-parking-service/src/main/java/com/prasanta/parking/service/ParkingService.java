package com.prasanta.parking.service;

import com.prasanta.parking.model.ParkingSlot;
import com.prasanta.parking.model.Vehicle;
import com.prasanta.parking.repository.ParkingSlotRepository;
import com.prasanta.parking.repository.VehicleRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm A");
        String formattedEntryTime = vehicle.getEntryTime().format(formatter);
        String formattedExitTime = vehicle.getExitTime().format(formatter);

        String to = vehicle.getUserEmail();
        String subject = "Parking Receipt";

        String htmlBody = """
                    <html>
                        <body style="font-family: Arial, sans-serif; line-height: 1.6;">
                            <h2 style="color: #2E86C1;">Parking Receipt</h2>
                            <p>Dear <strong>%s</strong>,</p>
                            <p>Your vehicle <strong>%s</strong> has been successfully <span style="color:green;">UNPARKED</span>.</p>
                            <table style="border-collapse: collapse; width: 100%%;">
                                <tr>
                                    <td style="padding: 8px; border: 1px solid #ddd;"><strong>Entry Time:</strong></td>
                                    <td style="padding: 8px; border: 1px solid #ddd;">%s</td>
                                </tr>
                                <tr>
                                    <td style="padding: 8px; border: 1px solid #ddd;"><strong>Exit Time:</strong></td>
                                    <td style="padding: 8px; border: 1px solid #ddd;">%s</td>
                                </tr>
                                <tr>
                                    <td style="padding: 8px; border: 1px solid #ddd;"><strong>Total Charges:</strong></td>
                                    <td style="padding: 8px; border: 1px solid #ddd;">₹%d</td>
                                </tr>
                            </table>
                            <p style="margin-top: 20px;">Thank you for using our parking service!</p>
                        </body>
                    </html>
                """.formatted(vehicle.getUserName(), vehicle.getLicensePlate(), formattedEntryTime, formattedExitTime, price);

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // true = enable HTML

            mailSender.send(mimeMessage);
            System.out.println("HTML Email sent to " + to);

        } catch (MessagingException e) {
            e.printStackTrace();
            System.err.println("Failed to send HTML email to " + to);
        }
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
