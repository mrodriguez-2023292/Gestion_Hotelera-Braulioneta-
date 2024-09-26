package com.braulioneta.gestionHotelera.DTO;

import com.braulioneta.gestionHotelera.model.Event;
import com.braulioneta.gestionHotelera.utils.ReservationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@NoArgsConstructor // Crea un constructor sin parámetros
@AllArgsConstructor // Crea un constructor con todos los atributos
public class EventReservationResponseDTO {

    // Identificador único de la reserva del evento
    private Long id;

    // Estado de la reserva, representado por un valor de la enumeración ReservationStatus
    private ReservationStatus status;

    // Detalles adicionales sobre la reserva
    private String details;

    // Información del usuario que realizó la reserva, representada por un objeto UserClearDTO
    private UserClearDTO user;

    // Evento asociado a la reserva, representado por un objeto Event
    private Event event;
}