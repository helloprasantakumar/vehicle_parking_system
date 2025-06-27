package com.prasanta.parking.controller;

import com.prasanta.parking.model.Admin;
import com.prasanta.parking.repository.AdminRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final AdminRepository adminRepo;

    public AdminController(AdminRepository adminRepo) {
        this.adminRepo = adminRepo;
    }

    @PostMapping("/register")
    public Admin register(@RequestBody Admin admin) {
        return adminRepo.save(admin);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody Admin admin) {
        return adminRepo.findByUsernameAndPassword(admin.getUsername(), admin.getPassword()) != null;
    }
}
