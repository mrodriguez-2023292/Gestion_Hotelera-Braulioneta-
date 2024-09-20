package com.braulioneta.gestionHotelera.model;

import java.sql.Timestamp;

import com.braulioneta.gestionHotelera.utils.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @FutureOrPresent 
    private Timestamp startDate;

    @NotNull
    @FutureOrPresent
    private Timestamp endDate;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Status status;

    @NotBlank
    private String details;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private Room room;

    @NotNull
    @ManyToOne
    private Event event;


}
