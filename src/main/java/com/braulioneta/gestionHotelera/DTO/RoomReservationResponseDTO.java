package com.braulioneta.gestionHotelera.DTO;

import com.braulioneta.gestionHotelera.model.Room;
import com.braulioneta.gestionHotelera.utils.ReservationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera automáticamente getters, setters, equals, hashCode y toString
@NoArgsConstructor // Crea un constructor sin parámetros
@AllArgsConstructor // Crea un constructor con todos los atributos
public class RoomReservationResponseDTO {

    // Identificador único de la reserva de la habitación
    private Long id;

    // Estado de la reserva, representado por un valor de la enumeración ReservationStatus
    private ReservationStatus status;

    // Detalles adicionales sobre la reserva
    private String details;

    // Información del usuario que realizó la reserva, representado por el DTO UserClearDTO
    private UserClearDTO user;

    // Información de la habitación reservada, representada por la entidad Room
    private Room room;
}