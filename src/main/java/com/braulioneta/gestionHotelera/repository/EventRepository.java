package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.Event;

// Esta interfaz maneja las operaciones con la base de datos para la entidad Event
public interface EventRepository extends JpaRepository<Event, Long> {
    
    // Repositorio para la entidad Event, proporciona operaciones CRUD básicas
}
