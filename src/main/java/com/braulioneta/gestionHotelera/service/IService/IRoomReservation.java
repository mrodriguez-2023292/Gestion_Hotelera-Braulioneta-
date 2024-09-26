package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.RoomReservationResponseDTO;
import com.braulioneta.gestionHotelera.DTO.RoomReservationSaveDTO;
import com.braulioneta.gestionHotelera.model.RoomReservation;

public interface IRoomReservation {

    // Lista todas las reservaciones de habitaciones
    List<RoomReservation> listReservations();

    // Obtiene una reservación de habitación por su ID
    RoomReservation getReservation(Long id);

    // Método para listar reservaciones (SOLO DE UN USUARIO EN ESPECÍFICO)
    List<RoomReservationResponseDTO> myReservations(Long userId);

    // Método para mostrar solo 1 reservación por su Id
    RoomReservation findByIdReservation(Long id);

    // Método para guardar una reservación
    RoomReservation addRoomReservation(RoomReservationSaveDTO reservationDTO);

    // Método para actualizar una reservación existente
    RoomReservation updateRoomReservation(Long id, RoomReservationSaveDTO reservationSaveDTO);

    // Método para eliminar una reservación
    void deleteReservation(RoomReservation reservation);
}