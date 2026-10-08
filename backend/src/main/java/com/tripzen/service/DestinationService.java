package com.tripzen.service;

import com.tripzen.entity.Destination;
import com.tripzen.repository.DestinationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DestinationService {

    @Autowired private DestinationRepository destinationRepository;

    public List<Destination> getAllDestinations() {
        return destinationRepository.findAll();
    }

    public Destination getById(Long id) {
        return destinationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Destination not found: " + id));
    }

    public List<Destination> searchByName(String name) {
        return destinationRepository.findByNameContainingIgnoreCase(name);
    }

    public Destination addDestination(Destination destination) {
        return destinationRepository.save(destination);
    }

    public Destination updateDestination(Long id, Destination updated) {
        Destination dest = getById(id);
        dest.setName(updated.getName());
        dest.setCountry(updated.getCountry());
        dest.setState(updated.getState());
        dest.setDescription(updated.getDescription());
        dest.setImageUrl(updated.getImageUrl());
        dest.setBestTimeToVisit(updated.getBestTimeToVisit());
        return destinationRepository.save(dest);
    }

    public void deleteDestination(Long id) {
        destinationRepository.deleteById(id);
    }
}
