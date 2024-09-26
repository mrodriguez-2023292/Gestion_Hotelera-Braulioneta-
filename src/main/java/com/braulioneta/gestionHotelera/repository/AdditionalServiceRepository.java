package com.braulioneta.gestionHotelera.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.braulioneta.gestionHotelera.model.AdditionalService;
import com.braulioneta.gestionHotelera.model.Event;

// Esta interfaz maneja las operaciones con la base de datos para la entidad AddtionalService
public interface AdditionalServiceRepository extends JpaRepository<AdditionalService, Long> {
    
    // Encuentra todos los servicios adicionales asociados a un evento específico
    List<AdditionalService> findByEvent(Event event);
}