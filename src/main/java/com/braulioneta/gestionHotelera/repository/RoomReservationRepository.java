package com.braulioneta.gestionHotelera.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.RoomReservation;
import com.braulioneta.gestionHotelera.model.User;

// Esta interfaz maneja las operaciones con la base de datos para la entidad RoomReservation
public interface RoomReservationRepository extends JpaRepository<RoomReservation, Long> {
    
    // Encuentra todas las reservas de habitaciones asociadas a un usuario específico
    List<RoomReservation> findByUser(User user);
}