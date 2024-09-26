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
public class Event {

    // Identificador único para cada evento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del evento, no puede estar en blanco
    @NotBlank
    private String name;

    // Descripción del evento, no puede estar en blanco
    @NotBlank
    private String description;

    // Fecha y hora de inicio del evento, debe ser una fecha presente o futura
    @NotNull
    @FutureOrPresent
    private Timestamp startDate;

    // Fecha y hora de finalización del evento, debe ser una fecha presente o futura
    @NotNull
    @FutureOrPresent
    private Timestamp endDate;

    // Hotel donde se realizará el evento, no puede ser nulo
    @NotNull
    @ManyToOne
    private Hotel hotelId;
}