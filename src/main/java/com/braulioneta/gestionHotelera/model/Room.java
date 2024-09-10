package com.braulioneta.gestionHotelera.model;

import com.braulioneta.gestionHotelera.utils.Status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String type;

    @NotBlank
    private Integer capacity;

    @NotBlank
    private Long price;

    @NotBlank
    @Enumerated(EnumType.STRING)
    private Status status = Status.AVAILABLE;

    @NotBlank
    @ManyToOne
    private Hotel hotel;
}
