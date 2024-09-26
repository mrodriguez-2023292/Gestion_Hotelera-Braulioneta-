package com.braulioneta.gestionHotelera.DTO;

import java.time.LocalDateTime;
import com.braulioneta.gestionHotelera.utils.ReservationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
public class RoomReservationSaveDTO {

    // Fecha de inicio de la reserva, no puede estar vacía
    @NotNull(message = "La fecha de inicio no puede ir vacía")
    private LocalDateTime startDate;

    // Fecha de finalización de la reserva, no puede estar vacía
    @NotNull(message = "La fecha de finalización no puede ir vacía")
    private LocalDateTime endDate;

    // Estado de la reserva, representado por un valor de la enumeración ReservationStatus
    @Enumerated(EnumType.STRING)
    @NotNull(message = "El status no puede ir vacío")
    private ReservationStatus status;

    // Detalles adicionales sobre la reserva, no puede estar vacío
    @NotBlank(message = "Los detalles no pueden ir vacíos")
    private String details;

    // Identificador del usuario que realiza la reserva, no puede ser nulo
    @NotNull(message = "No existe el usuario")
    private Long userId;

    // Identificador de la habitación reservada, no puede ser nulo
    @NotNull(message = "La habitación no existe")
    private Long roomId;
}