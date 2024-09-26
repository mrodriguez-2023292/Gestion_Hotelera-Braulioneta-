package com.braulioneta.gestionHotelera.DTO;

import com.braulioneta.gestionHotelera.utils.ReservationStatus;

import java.time.LocalDateTime;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
public class EventReservationSaveDTO {

    // Fecha y hora de inicio de la reserva del evento. Este campo no puede estar vacío.
    @NotNull(message = "La fecha de inicio no puede ir vacia")
    private LocalDateTime startDate;

    // Fecha y hora de finalización de la reserva del evento. Este campo no puede estar vacío.
    @NotNull(message = "La fecha de finalización no puede ir vacia")
    private LocalDateTime endDate;

    // Estado de la reserva, representado por un valor de la enumeración ReservationStatus. Este campo no puede estar vacío.
    @Enumerated(EnumType.STRING)
    @NotNull(message = "El status no puede ir vacio")
    private ReservationStatus status;

    // Detalles adicionales sobre la reserva. Este campo no puede estar vacío.
    @NotBlank(message = "Los detalles no pueden ir vacíos")
    private String details;

    // Identificador del usuario que realiza la reserva. Este campo no puede estar vacío.
    @NotNull(message = "No existe el usuario")
    private Long userId;

    // Identificador del evento asociado a la reserva. Este campo no puede estar vacío.
    @NotNull(message = "El evento no existe")
    private Long eventId;
}