package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;

import com.braulioneta.gestionHotelera.DTO.RoomSaveDTO;
import com.braulioneta.gestionHotelera.model.Room;

public interface IRoomService {

    // Devuelve una lista de todas las habitaciones
    List<Room> listRooms();

    // Obtiene una habitación por su ID
    Room getRoom(Long id);

    // Guarda una habitación en la base de datos
    Room addRoom(RoomSaveDTO roomDto);

    // Actualiza una habitación existente en la base de datos
    Room updateRoom(Long id, RoomSaveDTO roomDto);

    // Elimina una habitación de la base de datos
    void deleteRoom(Room room);
}