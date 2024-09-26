package com.braulioneta.gestionHotelera.model;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
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

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@Entity // Indica que esta clase es una entidad JPA (una tabla en la base de datos)
@AllArgsConstructor // Crea un constructor con todos los atributos
@NoArgsConstructor // Crea un constructor sin parámetros
public class Report {

    // Identificador único para cada reporte
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del hotel asociado al reporte, no puede estar vacío
    @NotBlank
    private String hotelName;

    // Fecha del reporte, debe ser en el presente o futuro
    @NotNull
    @FutureOrPresent
    private Timestamp reportDate;

    // Total de reservaciones en el hotel durante el periodo reportado, no puede ser nulo
    @NotNull
    private Long totalReservations;

    // Número total de habitaciones en el hotel, no puede ser nulo
    @NotNull
    private Long totalRooms;

    // Número de habitaciones ocupadas durante el periodo reportado, no puede ser nulo
    @NotNull
    private Long occupiedRooms;

    // Tasa de ocupación calculada, no puede ser nula
    @NotNull
    private Float occupancyRate;

    // Nombre del hotel más solicitado, no puede estar vacío
    @NotBlank
    private String mostRequestedHotel;
    
    // Hotel asociado a este reporte, debe ser un hotel válido y no nulo
    @NotNull
    @ManyToOne
    private Hotel hotelId;
}