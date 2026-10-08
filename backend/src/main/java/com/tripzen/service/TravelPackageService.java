package com.tripzen.service;

import com.tripzen.entity.TravelPackage;
import com.tripzen.repository.TravelPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TravelPackageService {

    @Autowired private TravelPackageRepository packageRepository;

    public List<TravelPackage> getAllPackages() {
        return packageRepository.findByIsActiveTrue();
    }

    public List<TravelPackage> getAllPackagesAdmin() {
        return packageRepository.findAll();
    }

    public TravelPackage getById(Long id) {
        return packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Package not found: " + id));
    }

    public List<TravelPackage> getByDestination(Long destinationId) {
        return packageRepository.findByDestinationId(destinationId);
    }

    public List<TravelPackage> searchPackages(String name) {
        return packageRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(name);
    }

    public TravelPackage addPackage(TravelPackage pkg) {
        return packageRepository.save(pkg);
    }

    public TravelPackage updatePackage(Long id, TravelPackage updated) {
        TravelPackage pkg = getById(id);
        pkg.setName(updated.getName());
        pkg.setDescription(updated.getDescription());
        pkg.setDurationDays(updated.getDurationDays());
        pkg.setPrice(updated.getPrice());
        pkg.setImageUrl(updated.getImageUrl());
        pkg.setInclusions(updated.getInclusions());
        pkg.setMaxPersons(updated.getMaxPersons());
        pkg.setIsActive(updated.getIsActive());
        if (updated.getDestination() != null) {
            pkg.setDestination(updated.getDestination());
        }
        return packageRepository.save(pkg);
    }

    public void deletePackage(Long id) {
        TravelPackage pkg = getById(id);
        pkg.setIsActive(false);
        packageRepository.save(pkg);
    }

    public long getTotalPackages() {
        return packageRepository.count();
    }
}
