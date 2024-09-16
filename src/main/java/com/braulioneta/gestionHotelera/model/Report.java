package com.braulioneta.gestionHotelera.model;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


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
    @NotNull
    @FutureOrPresent
    private Date report_date;
    @NotNull
    private Long total_reservations;
    @NotNull
    private Long total_rooms;
    @NotNull
    private Long occupied_rooms;
    @NotNull
    private Float occupancy_rate;
    @NotBlank
    private String most_requested_hotel;
 
   


}
