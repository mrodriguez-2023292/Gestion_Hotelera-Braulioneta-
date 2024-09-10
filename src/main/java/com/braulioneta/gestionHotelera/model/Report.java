package com.braulioneta.gestionHotelera.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String hotel_name;
    @NotBlank
    private Date report_date;
    @NotBlank
    private Long total_reservations;
    @NotBlank
    private Long total_rooms;
    @NotBlank
    private Long occupied_rooms;
    @NotBlank
    private Float occupancy_rate;
    @NotBlank
    private String most_requested_hotel;
 


}
