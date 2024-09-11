package com.braulioneta.gestionHotelera.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.braulioneta.gestionHotelera.model.Room;

// Esta interfaz maneja las operaciones con la base de datos para la entidad Room
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Busca una habitación por su ID
    public Room findByid(Long id);
}
