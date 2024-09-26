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
@Entity // Indica que esta clase es una entidad JPA (representa una tabla en la base de datos)
@AllArgsConstructor // Genera un constructor con todos los atributos de la clase
@NoArgsConstructor // Genera un constructor sin parámetros
public class RoomReservation {

    // Identificador único de la reserva de habitación. Se genera automáticamente.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Fecha de inicio de la reserva. Debe ser una fecha futura o presente.
    @NotNull
    @FutureOrPresent 
    private Timestamp startDate;

    // Fecha de fin de la reserva. Debe ser una fecha futura o presente.
    @NotNull
    @FutureOrPresent
    private Timestamp endDate;

    // Estado de la reserva. Utiliza la enumeración ReservationStatus, que puede ser:
    // RESERVED (reservada), CANCELED (cancelada), COMPLETED (completada).
    @Enumerated(EnumType.STRING)
    @NotNull
    private ReservationStatus status;

    // Detalles adicionales sobre la reserva.
    // Este campo no puede estar vacío.
    @NotBlank
    private String details;

    // Usuario que realizó la reserva. Relación ManyToOne con la entidad User.
    @NotNull
    @ManyToOne
    private User user;

    // Habitación reservada. Relación ManyToOne con la entidad Room.
    @NotNull
    @ManyToOne
    private Room roomId;
}