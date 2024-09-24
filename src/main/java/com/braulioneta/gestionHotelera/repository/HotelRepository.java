package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.braulioneta.gestionHotelera.model.Hotel;

// Esta interfaz maneja las operaciones con la base de datos para la entidad Hotel
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    // Busca un hotel por su nombre
    public Hotel findByname(String name);
}