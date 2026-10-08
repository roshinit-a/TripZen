package com.tripzen.service;

import com.tripzen.entity.Destination;
import com.tripzen.entity.Hotel;
import com.tripzen.repository.DestinationRepository;
import com.tripzen.repository.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {

    @Autowired private HotelRepository hotelRepository;
    @Autowired private DestinationRepository destinationRepository;

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public Hotel getById(Long id) {
        return hotelRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Hotel not found: " + id));
    }

    public List<Hotel> getByDestination(Long destinationId) {
        return hotelRepository.findByDestinationId(destinationId);
    }

    public Hotel addHotel(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    public Hotel updateHotel(Long id, Hotel updated) {
        Hotel hotel = getById(id);
        hotel.setName(updated.getName());
        hotel.setLocation(updated.getLocation());
        hotel.setPricePerNight(updated.getPricePerNight());
        hotel.setRating(updated.getRating());
        hotel.setAvailableRooms(updated.getAvailableRooms());
        hotel.setDescription(updated.getDescription());
        hotel.setImageUrl(updated.getImageUrl());
        if (updated.getDestination() != null) {
            hotel.setDestination(updated.getDestination());
        }
        return hotelRepository.save(hotel);
    }

    public void deleteHotel(Long id) {
        hotelRepository.deleteById(id);
    }
}
