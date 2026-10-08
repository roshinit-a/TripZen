package com.tripzen.repository;

import com.tripzen.entity.TravelPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TravelPackageRepository extends JpaRepository<TravelPackage, Long> {
    List<TravelPackage> findByDestinationId(Long destinationId);
    List<TravelPackage> findByIsActiveTrue();
    List<TravelPackage> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);

    @Query("SELECT tp FROM TravelPackage tp WHERE tp.isActive = true AND tp.price <= :maxPrice")
    List<TravelPackage> findByMaxPrice(Double maxPrice);
}
