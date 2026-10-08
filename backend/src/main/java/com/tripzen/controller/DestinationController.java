package com.tripzen.controller;

import com.tripzen.entity.Destination;
import com.tripzen.service.DestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@CrossOrigin(origins = "*")
public class DestinationController {

    @Autowired private DestinationService destinationService;

    @GetMapping
    public List<Destination> getAll() {
        return destinationService.getAllDestinations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(destinationService.getById(id));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public List<Destination> search(@RequestParam String name) {
        return destinationService.searchByName(name);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> add(@RequestBody Destination destination) {
        return ResponseEntity.ok(destinationService.addDestination(destination));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@PathVariable Long id,
                                     @RequestBody Destination destination) {
        try {
            return ResponseEntity.ok(destinationService.updateDestination(id, destination));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        destinationService.deleteDestination(id);
        return ResponseEntity.ok("{\"message\":\"Destination deleted\"}");
    }
}
