package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.ReservationResponseDTO;
import com.braulioneta.gestionHotelera.DTO.ReservationSaveDTO;
import com.braulioneta.gestionHotelera.model.Reservation;


public interface IReservation {
    List<Reservation> listReservations();

    Reservation getReservation(Long id);

    //Método para listar reservaciones (SOLO DE UN USUARIO EN ESPECÍFICO)
    List<ReservationResponseDTO> myReservations(Long userId);

    //Método para mostrar solo 1 reservación por su Id
    Reservation findByIdReservation(Long id);

    //Método para guardar una reservación
     Reservation save(ReservationSaveDTO reservationDTO);

    //Metodo para actualizar una reservacion
    Reservation update(Long id, ReservationSaveDTO reservationSaveDTO);

     //Metodo para cancelar una reservacion
     void cancelReservation(Reservation reservation);
}
