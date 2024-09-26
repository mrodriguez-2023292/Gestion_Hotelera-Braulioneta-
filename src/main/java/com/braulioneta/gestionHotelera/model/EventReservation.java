package com.braulioneta.gestionHotelera.model;

import java.sql.Timestamp;

import com.braulioneta.gestionHotelera.utils.ReservationStatus;

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

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@Entity // Indica que esta clase es una entidad JPA (una tabla en la base de datos)
@AllArgsConstructor // Crea un constructor con todos los atributos
@NoArgsConstructor // Crea un constructor sin parámetros
public class EventReservation {

    // Identificador único para cada reserva de evento
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha y hora de inicio de la reserva, debe ser una fecha presente o futura
    @NotNull
    @FutureOrPresent
    private Timestamp startDate;

    // Fecha y hora de finalización de la reserva, debe ser una fecha presente o futura
    @NotNull
    @FutureOrPresent
    private Timestamp endDate;

    // Estado de la reserva (ej. CONFIRMED, CANCELLED), basado en un enum llamado ReservationStatus
    @Enumerated(EnumType.STRING)
    @NotNull
    private ReservationStatus status;

    // Detalles adicionales sobre la reserva, no puede estar en blanco
    @NotBlank
    private String details;

    // Usuario que realizó la reserva, no puede ser nulo
    @NotNull
    @ManyToOne
    private User user;

    // Evento relacionado con la reserva, no puede ser nulo
    @NotNull
    @ManyToOne
    private Event eventId;
}