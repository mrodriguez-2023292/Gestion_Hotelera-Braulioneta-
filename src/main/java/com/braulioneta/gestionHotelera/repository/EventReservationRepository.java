package com.braulioneta.gestionHotelera.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.EventReservation;
import com.braulioneta.gestionHotelera.model.User;

// Esta interfaz maneja las operaciones con la base de datos para la entidad EventReservation
public interface EventReservationRepository extends JpaRepository<EventReservation, Long> {
    
    // Encuentra todas las reservas de eventos asociadas a un usuario específico
    List<EventReservation> findByUser(User user);
}