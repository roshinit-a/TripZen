package com.tripzen.controller;

import com.tripzen.entity.TravelPackage;
import com.tripzen.service.TravelPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packages")
@CrossOrigin(origins = "*")
public class PackageController {

    @Autowired private TravelPackageService packageService;

    @GetMapping
    public List<TravelPackage> getAll() {
        return packageService.getAllPackages();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(packageService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/destination/{destinationId}")
    public List<TravelPackage> getByDestination(@PathVariable Long destinationId) {
        return packageService.getByDestination(destinationId);
    }

    @GetMapping("/search")
    public List<TravelPackage> search(@RequestParam String name) {
        return packageService.searchPackages(name);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestBody TravelPackage pkg) {
        return ResponseEntity.ok(packageService.addPackage(pkg));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TravelPackage pkg) {
        try {
            return ResponseEntity.ok(packageService.updatePackage(id, pkg));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        packageService.deletePackage(id);
        return ResponseEntity.ok("{\"message\":\"Package deactivated\"}");
    }
}
