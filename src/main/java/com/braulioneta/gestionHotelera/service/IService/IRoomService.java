package com.braulioneta.gestionHotelera.service.IService;

import java.util.List;
import com.braulioneta.gestionHotelera.model.Room;

// Esta interfaz define los métodos para las operaciones relacionadas con las habitaciones
public interface IRoomService {

    // Devuelve una lista de todas las habitaciones
    List<Room> listRooms();

    // Obtiene una habitación por su ID
    Room getRoom(Long id);

    // Guarda una habitación en la base de datos
    Room saveRoom(Room room);

    // Elimina una habitación de la base de datos
    void eliminateRoom(Room room);
}
