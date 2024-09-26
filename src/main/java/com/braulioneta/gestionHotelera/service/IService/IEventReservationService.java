package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.EventReservationResponseDTO;
import com.braulioneta.gestionHotelera.DTO.EventReservationSaveDTO;
import com.braulioneta.gestionHotelera.model.EventReservation;

public interface IEventReservation {
    
    // Lista todas las reservaciones de eventos
    List<EventReservation> listReservations();

    // Obtiene una reservación de evento por su ID
    EventReservation getReservation(Long id);

    // Lista las reservaciones de un usuario específico
    List<EventReservationResponseDTO> myReservations(Long userId);

    // Encuentra una reservación por su ID
    EventReservation findByIdReservation(Long id);

    // Guarda una nueva reservación de evento a partir de un DTO
    EventReservation addEventReservation(EventReservationSaveDTO reservationDTO);

    // Actualiza una reservación existente con un DTO
    EventReservation updateEventReservation(Long id, EventReservationSaveDTO reservationSaveDTO);

    // Cancela una reservación de evento
    void deleteReservation(EventReservation reservation);
}