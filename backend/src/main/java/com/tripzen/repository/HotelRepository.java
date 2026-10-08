package com.tripzen.repository;

import com.tripzen.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByDestinationId(Long destinationId);
    List<Hotel> findByNameContainingIgnoreCase(String name);
    List<Hotel> findByRatingGreaterThanEqual(Double rating);
}
