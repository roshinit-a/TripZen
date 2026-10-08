package com.tripzen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingRequest {
    private Long packageId;
    private LocalDate travelDate;
    private Integer persons;
    private String specialRequests;
}
